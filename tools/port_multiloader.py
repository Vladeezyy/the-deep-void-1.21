#!/usr/bin/env python3
"""NeoForge-only MCreator code → loader-neutral common code (idempotent; run from the repository root).

    python3 -I tools/port_multiloader.py          # rewrite common/src/main/java, regenerate the event indexes

- NeoForge event / bus / dist / FMLPaths imports → the same names in net.mcreator.midnightlurker.port.bus
- DeferredRegister / DeferredHolder → port.platform.Registrar / RegistryEntry (mob effects: .holder() where a Holder is used)
- x.getData(TheDeepVoidModVariables.PLAYER_VARIABLES) → TheDeepVoidModVariables.of(x)
- x.getPersistentData() → PortUtil.data(x)
- NeoForge's EntityType.Builder methods → vanilla ones
- port/bus/EventIndex.java and ClientEventIndex.java: every @SubscribeEvent method whose event is in port.bus
"""
import re
import sys
from pathlib import Path

ROOT = Path("common/src/main/java/net/sombrius/thedeepvoid")
PKG = "net.sombrius.thedeepvoid"
BUS = ROOT / "port" / "bus"
BUS_TYPES = {p.stem for p in BUS.glob("*.java")} - {"package-info", "ModBus", "EventIndex", "ClientEventIndex"}
# NeoForge API the mod uses outside events, rewritten for vanilla (port/neo: same names and methods)
NEO = ROOT / "port" / "neo"
NEO_TYPES = {p.stem for p in NEO.glob("*.java")} - {"package-info", "ItemHandlers", "PortCaps"}

IMPORTS = {
    "net.neoforged.fml.common.Mod": None,
    "net.neoforged.bus.api.IEventBus": None,
    "net.neoforged.neoforge.registries.DeferredRegister": f"{PKG}.port.platform.Registrar",
    "net.neoforged.neoforge.registries.DeferredHolder": f"{PKG}.port.platform.RegistryEntry",
}


def rewrite_imports(src):
    def repl(m):
        name = m.group(1)
        if name in IMPORTS:
            return "" if IMPORTS[name] is None else f"import {IMPORTS[name]};\n"
        simple = name.rsplit(".", 1)[1]
        if name.startswith("net.neoforged.") and simple in BUS_TYPES:
            return f"import {PKG}.port.bus.{simple};\n"
        if name.startswith("net.neoforged.") and simple in NEO_TYPES:
            return f"import {PKG}.port.neo.{simple};\n"
        if name == "net.neoforged.neoforge.common.MutableDataComponentHolder":
            return "import net.minecraft.world.item.ItemStack;\n"
        return m.group(0)
    return re.sub(r"import (net\.neoforged\.[\w.]+);\n", repl, src)


def receiver_start(s, end):
    """Start index of the primary expression that ends at s[end] (exclusive), e.g. a.b(c).d or (cast) x"""
    i = end
    while True:
        if i > 0 and s[i - 1] == ")":
            depth = 0
            j = i - 1
            while j >= 0:
                if s[j] == ")":
                    depth += 1
                elif s[j] == "(":
                    depth -= 1
                    if depth == 0:
                        break
                j -= 1
            i = j
            k = i
            while k > 0 and (s[k - 1].isalnum() or s[k - 1] in "_$"):
                k -= 1
            i = k
        else:
            k = i
            while k > 0 and (s[k - 1].isalnum() or s[k - 1] in "_$"):
                k -= 1
            if k == i:
                return i
            i = k
        if i > 0 and s[i - 1] == ".":
            i -= 1
            continue
        return i


def wrap_calls(src, suffix, make):
    """x<suffix> → make(x) for every occurrence"""
    out = src
    while True:
        pos = out.find(suffix)
        if pos < 0:
            return out
        start = receiver_start(out, pos)
        recv = out[start:pos]
        if not recv:
            sys.exit(f"no receiver before {suffix!r}: {out[max(0, pos - 80):pos + 40]!r}")
        out = out[:start] + make(recv) + out[pos + len(suffix):]


def ensure_import(src, fqcn):
    if f"import {fqcn};" in src or not re.search(r"\b" + fqcn.rsplit(".", 1)[1] + r"\b", src):
        return src
    return re.sub(r"^(package [\w.]+;\n)", r"\1\nimport " + fqcn + ";\n", src, count=1, flags=re.M)


def effect_visibility(src):
    """MobEffect.initializeClient(IClientMobEffectExtensions) → the methods of port.EffectVisibility on the effect"""
    m = re.search(r"\n\t@Override\n\tpublic void initializeClient\([^)]*\) \{\n\t\tconsumer\.accept\(new IClientMobEffectExtensions\(\) \{\n(.*?)\n\t\t\}\);\n\t\}\n", src, re.S)
    if not m:
        return src
    methods = re.split(r"\n(?=\t\t\t@Override)", m.group(1))
    kept = ["\n".join(line[2:] for line in part.split("\n")) for part in methods if "isVisibleIn" in part]
    src = src[:m.start()] + "\n" + "\n\n".join(kept) + "\n" + src[m.end():]
    src = re.sub(r"(public class \w+ extends MobEffect)( implements [\w., ]+)?( \{)",
                 lambda m: m.group(1) + (m.group(2) + ", " if m.group(2) else " implements ") + "net.sombrius.thedeepvoid.port.EffectVisibility" + m.group(3), src)
    return re.sub(r"import net\.neoforged\.neoforge\.client\.extensions\.common\.IClientMobEffectExtensions;\n", "", src)


NEO_ONLY_OVERRIDES = ["canConnectRedstone", "getBeaconColorMultiplier", "canSustainPlant", "onEntitySwing", "getBlockPathType", "getArmorTexture",
                      "getCraftingRemainingItem", "getFlammability", "getFireSpreadSpeed", "getEnchantPowerBonus", "canDrownInFluidType",
                      # DimensionSpecialEffects (the Fabric module calls them from LevelRenderer / LightTexture mixins)
                      "renderClouds", "renderSky", "renderSnowAndRain", "tickRain", "adjustLightmapColors"]


def drop_args(src, call):
    """x.call(args) → x.call() (NeoForge's level-aware overloads of vanilla getters)"""
    pos = 0
    while (i := src.find(call, pos)) >= 0:
        start = i + len(call) - 1
        end = close_paren(src, start)
        src = src[:start + 1] + src[end:]
        pos = start + 1
    return src


def close_paren(s, i):
    depth = 0
    while True:
        if s[i] == "(":
            depth += 1
        elif s[i] == ")":
            depth -= 1
            if depth == 0:
                return i
        i += 1


def implement_owner(src, method, iface):
    """Adds an interface to each class that declares {method} (a nested class or the top-level one)"""
    pos = 0
    while (i := src.find(method, pos)) >= 0:
        pos = i + len(method)
        decls = [m for m in re.finditer(r"^([ \t]*public (?:static )?(?:abstract )?class \w+(?:<[^>{]*>)?(?: extends [\w.<>, ?]+?)?)( implements ([^{]+?))?(\s*\{)", src[:i], re.M)]
        # the innermost class whose braces still enclose the method
        for m in reversed(decls):
            depth, j = 0, m.end() - 1
            while j < i:
                depth += {"{": 1, "}": -1}.get(src[j], 0)
                j += 1
            if depth > 0:
                if iface in (m.group(3) or ""):
                    break
                new = m.group(1) + (" implements " + m.group(3).strip() + ", " if m.group(2) else " implements ") + iface + m.group(4)
                src = src[:m.start()] + new + src[m.end():]
                pos += len(new) - (m.end() - m.start())
                break
    return src


def implement(src, iface):
    """Adds an interface to the file's top-level class"""
    if iface in src:
        return src
    m = re.search(r"^(public (?:abstract )?class \w+(?:<[^>{]*>)?(?: extends [\w.<>, ?]+?)?)( implements ([^{]+?))?(\s*\{)", src, re.M)
    if not m:
        return src
    if m.group(2):
        return src[:m.start()] + m.group(1) + " implements " + m.group(3).strip() + ", " + iface + m.group(4) + src[m.end():]
    return src[:m.start()] + m.group(1) + " implements " + iface + m.group(4) + src[m.end():]


def rewrite_code(path, src):
    src = rewrite_imports(src)
    # unused MCreator imports (not on Fabric's classpath)
    src = re.sub(r"import org\.checkerframework\.[\w.]+;\n", "", src)
    src = re.sub(r"\bDeferredRegister<", "Registrar<", src)
    src = re.sub(r"\bDeferredRegister\.create\(", "Registrar.create(", src)
    src = re.sub(r"\bDeferredHolder<", "RegistryEntry<", src)
    for tags, reg in (("BlockTags", "BLOCK"), ("ItemTags", "ITEM"), ("EntityTypeTags", "ENTITY_TYPE"), ("BiomeTags", "BIOME")):
        src = src.replace(f"{tags}.create(", f"net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.{reg}, ")
    src = src.replace(".setShouldReceiveVelocityUpdates(true)", "")
    src = src.replace("new SimpleParticleType(true))", "new SimpleParticleType(true) {})").replace("new SimpleParticleType(false))", "new SimpleParticleType(false) {})")
    src = src.replace("import net.neoforged.neoforge.common.DeferredSpawnEggItem;", "import net.minecraft.world.item.SpawnEggItem;")
    src = re.sub(r"new SavedData\.Factory<>\((\w+)::new, (\w+)::load\)", r"new SavedData.Factory<>(\1::new, \2::load, null)", src)
    # NeoForge-only overrides that NeoForge still calls without @Override
    src = re.sub(r"@Override\n(\s*)public boolean (shouldDisplayFluidOverlay|canHarvestBlock)\(", r"\1public boolean \2(", src)
    src = src.replace("public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader world, BlockPos pos, Player player) {",
                      "public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {")
    # NeoForge's onDestroyedByPlayer stays (NeoForge calls it; the Fabric module calls it through PortBlockHooks.DestroyedByPlayer)
    if "public boolean onDestroyedByPlayer(" in src:
        src = re.sub(r"\n[ \t]*@Override[ \t]*(\n[ \t]*public boolean onDestroyedByPlayer\()", r"\1", src)
        src = re.sub(r"super\.onDestroyedByPlayer\((\w+), (\w+), (\w+), (\w+), (\w+), (\w+)\)", r"net.sombrius.thedeepvoid.port.PortUtil.destroyedByPlayer(\2, \3, \6)", src)
        src = implement(src, "net.sombrius.thedeepvoid.port.PortBlockHooks.DestroyedByPlayer")
    # other NeoForge-only overrides: NeoForge still calls them, the Fabric module maps the ones that matter
    src = re.sub(r"\n[ \t]*@Override[ \t]*(\n[ \t]*public [\w<>\[\]?, .]+? (?:" + "|".join(NEO_ONLY_OVERRIDES) + r")\()", r"\1", src)
    src = re.sub(r"new DeferredSpawnEggItem\((TheDeepVoidModEntities\.\w+),", r"new SpawnEggItem(\1.get(),", src)
    src = src.replace("NeoForgeMod.POISON_DAMAGE", "net.sombrius.thedeepvoid.port.PortUtil.POISON_DAMAGE")
    src = re.sub(r"import net\.neoforged\.neoforge\.common\.NeoForgeMod;\n", "", src)
    src = re.sub(r'DeferredHolder\.create\(Registries\.SOUND_EVENT, (ResourceLocation\.parse\("[^"]+"\))\)', r"net.minecraft.core.Holder.direct(net.minecraft.sounds.SoundEvent.createVariableRangeEvent(\1))", src)
    src = re.sub(r"instanceof ILevelExtension (\w+)", r"instanceof net.minecraft.world.level.LevelAccessor \1", src)
    src = src.replace(".getUnsidedLevel()", ".getLevel()")
    src = re.sub(r"\.getFoodProperties\((\w+)\)", ".get(net.minecraft.core.component.DataComponents.FOOD)", src)
    src = drop_args(src, ".getLightEmission(")
    src = src.replace("CreativeModeTab.builder()", f"{PKG}.port.platform.Platform.INSTANCE.creativeTabBuilder()")
    for imp in ("net.neoforged.fml.ModLoadingContext", "net.neoforged.neoforge.common.NeoForge"):
        if imp.rsplit(".", 1)[1] + "." not in src.replace(f"import {imp};", ""):
            src = src.replace(f"import {imp};\n", "")
    src = re.sub(r"(?:net\.neoforged\.neoforge\.event\.)?EventHooks\.onAnimalTame\(", f"{PKG}.port.platform.Platform.INSTANCE.onAnimalTame(", src)
    src = re.sub(r"\bCommonHooks\.(canCropGrow|fireCropGrowPost)\(", rf"{PKG}.port.platform.Platform.INSTANCE.\1(", src)
    src = re.sub(r"\bServerLifecycleHooks\.getCurrentServer\(\)", f"{PKG}.port.platform.Platform.INSTANCE.currentServer()", src)
    src = re.sub(r"super\.onEntitySwing\(\w+, \w+\)", "false", src)
    src = re.sub(r"super\.canHarvestBlock\((\w+), \w+, \w+, (\w+)\)", r"\2.hasCorrectToolForDrops(\1)", src)
    src = src.replace("IMenuTypeExtension.create(", f"{PKG}.port.platform.Platform.INSTANCE.menuType(")
    src = wrap_calls(src, ".openMenu(new MenuProvider()", lambda r: f"{PKG}.port.platform.Platform.INSTANCE.openMenu({r}, new MenuProvider()")
    src = re.sub(r"\bArmPoses\.(\w+)\.getValue\(\)", rf'{PKG}.port.PortArmPoses.pose("\1")', src)
    src = re.sub(r"(?<!ClampedItemPropertyFunction\) )ItemProperties\.getProperty\(", "(net.minecraft.client.renderer.item.ClampedItemPropertyFunction) ItemProperties.getProperty(", src)
    src = src.replace("ItemBlockRenderTypes.setRenderLayer(", f"{PKG}.port.PortClient.setRenderLayer(")
    for imp in ("net.neoforged.neoforge.common.extensions.ILevelExtension", "net.neoforged.neoforge.common.extensions.IMenuTypeExtension", "net.neoforged.neoforge.event.EventHooks",
                "net.neoforged.neoforge.common.CommonHooks", "net.neoforged.neoforge.server.ServerLifecycleHooks", "net.neoforged.neoforge.registries.DeferredHolder",
                "net.neoforged.neoforge.client.IArmPoseTransformer", "net.neoforged.fml.common.asm.enumextension.EnumProxy", "net.sombrius.thedeepvoid.port.ArmPoses"):
        src = src.replace(f"import {imp};\n", "")
    # the config: port.neo.ModConfigSpec reads the same file on both loaders
    src = re.sub(r"ModList\.get\(\)\.getModContainerById\(TheDeepVoidMod\.MODID\)\.ifPresent\(container -> container\.registerConfig\(ModConfig\.Type\.\w+, (\w+)\.SPEC, (\"[^\"]+\")\)\);",
                 rf"\1.SPEC.load({PKG}.port.platform.Platform.INSTANCE.configFile(\2));", src)
    src = src.replace("import net.neoforged.fml.ModList;\n", "")
    # the custom sky reads NeoForge's dimension effects registry: the mod's own effects are in PortClient
    src = re.sub(r'Field field = DimensionSpecialEffectsManager\.class\.getDeclaredField\("EFFECTS"\);\s*field\.setAccessible\(true\);\s*'
                 r'for \(DimensionSpecialEffects dimensionSpecialEffects : \(\(com\.google\.common\.collect\.ImmutableMap<ResourceLocation, DimensionSpecialEffects>\) field\.get\(null\)\)\.values\(\)\)',
                 f"for (DimensionSpecialEffects dimensionSpecialEffects : {PKG}.port.PortClient.dimensionEffects())", src)
    src = src.replace("import net.neoforged.neoforge.client.DimensionSpecialEffectsManager;\n", "")
    src = src.replace("net.neoforged.neoforge.event.level.LevelEvent", f"{PKG}.port.bus.LevelEvent")
    # items and effects with client extensions: the loaders find them through these interfaces
    src = implement_owner(src, "public IClientItemExtensions clientExtensions()", f"{PKG}.port.neo.HasClientExtensions.Item")
    src = implement_owner(src, "public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity)", f"{PKG}.port.PortItemHooks.EntitySwing")
    src = implement_owner(src, "public IClientMobEffectExtensions clientExtensions()", f"{PKG}.port.neo.HasClientExtensions.Effect")
    # client-only members: NeoForge strips @OnlyIn on a dedicated server, Fabric Loader strips @Environment
    src = re.sub(r"(?<![.\w])@OnlyIn\(Dist\.CLIENT\)", "@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)", src)
    src = re.sub(r"(?<![.\w])@OnlyIn\(value = Dist\.CLIENT, _interface = ([\w.]+)\.class\)",
                 r"@net.neoforged.api.distmarker.OnlyIn(value = net.neoforged.api.distmarker.Dist.CLIENT, _interface = \1.class) @net.fabricmc.api.EnvironmentInterface(value = net.fabricmc.api.EnvType.CLIENT, itf = \1.class)", src)
    # NeoForge fluid types live in the neoforge module
    src = re.sub(r"\(\) -> TheDeepVoidModFluidTypes\.(\w+)_TYPE\.get\(\)", lambda m: f'() -> net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.fluidType("{m.group(1).lower()}")', src)
    src = re.sub(r"import net\.sombrius\.thedeepvoid\.init\.TheDeepVoidModFluidTypes;\n", "", src)
    src = src.replace("SwordItem.createAttributes(", "net.sombrius.thedeepvoid.port.PortUtil.swordAttributes(")
    for fqcn in ("net.minecraft.world.level.LevelAccessor", "net.minecraft.world.item.SpawnEggItem"):
        src = ensure_import(src, fqcn)
    src = src.replace(".setTrackingRange(", ".clientTrackingRange(").replace(".setUpdateInterval(", ".updateInterval(")
    src = wrap_calls(src, ".getData(TheDeepVoidModVariables.PLAYER_VARIABLES)", lambda r: f"TheDeepVoidModVariables.of({r})")
    # item handler capabilities → port.neo.PortCaps (the same answers on both loaders)
    for suffix, call in ((".getCapability(Capabilities.ItemHandler.BLOCK, ", "block({}, "), (".getCapability(Capabilities.ItemHandler.ENTITY, ", "entity({}, "),
                         (".getCapability(Capabilities.ItemHandler.ENTITY)", "entity({})"), (".getCapability(Capabilities.ItemHandler.ITEM)", "item({})")):
        src = wrap_calls(src, suffix, lambda r, call=call: f"{PKG}.port.neo.PortCaps." + call.format(r))
    src = re.sub(r"\bMutableDataComponentHolder\b", "ItemStack", src)
    src = wrap_calls(src, ".getPersistentData()", lambda r: f"{PKG}.port.PortUtil.data({r})")
    src = src.replace("FakePlayerFactory.getMinecraft(", f"{PKG}.port.platform.Platform.INSTANCE.fakePlayer(")
    src = re.sub(r"import net\.neoforged\.neoforge\.common\.util\.FakePlayerFactory;\n", "", src)
    if path.name != "TheDeepVoidModPotions.java":
        src = re.sub(r"\bTheDeepVoidModPotions\.(?!REGISTRY\b)([A-Z0-9_]+)\b(?!\.(holder|get|value|getId|getKey)\b)", r"TheDeepVoidModPotions.\1.holder()", src)
    if path.name != "TheDeepVoidModMobEffects.java":
        src = re.sub(r"\bTheDeepVoidModMobEffects\.(?!REGISTRY\b)([A-Z0-9_]+)\b(?!\.(holder|get|value|getId|getKey)\b)",
                     r"TheDeepVoidModMobEffects.\1.holder()", src)
    return src


SUB = re.compile(r"@SubscribeEvent(?:\(priority\s*=\s*EventPriority\.(\w+)\))?\s*\n(?:[ \t]*(?:@[\w.]+(?:\([^()]*(?:\([^()]*\)[^()]*)*\))?[ \t]*)+\n)*\s*public static void (\w+)\(\s*([\w.]+) \w+\)")


def index_entries(path, src):
    cls = re.search(r"^public (?:final |abstract )*class (\w+)", src, re.M)
    if not cls:
        return [], []
    pkg = re.search(r"^package ([\w.]+);", src, re.M).group(1)
    annotation = re.search(r"@EventBusSubscriber(\([^)]*\))?\s*\npublic", src)
    client = bool(annotation and annotation.group(1) and "CLIENT" in annotation.group(1))
    common, clientside, skipped = [], [], []
    # nested static classes with their own @EventBusSubscriber (MCreator's EventBusVariableHandlers)
    nested = []
    for m in re.finditer(r"@EventBusSubscriber([^\n]*)\n\s*public static class (\w+)[^{]*\{", src):
        depth, i = 1, m.end()
        while depth:
            depth += {"{": 1, "}": -1}.get(src[i], 0)
            i += 1
        nested.append((m.start(), i, m.group(2), "CLIENT" in m.group(1)))
    for m in SUB.finditer(src):
        event = m.group(3)
        if event.split(".")[0] not in BUS_TYPES:
            skipped.append(f"{path.name}: {event}")
            continue
        owner = cls.group(1)
        inside = [(n, c) for start, end, n, c in nested if start < m.start() < end]
        nested_client = False
        if inside:
            owner = f"{owner}.{inside[-1][0]}"
            nested_client = inside[-1][1]
        entry = (event, m.group(1) or "NORMAL", f"{pkg}.{owner}::{m.group(2)}")
        # a client-only method (stripped on a dedicated server) is a client listener even in a common class
        only_client = "OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT)" in m.group(0)
        (clientside if client or only_client or nested_client else common).append(entry)
    return common + [("__client__",) + e for e in clientside], skipped


def write_index(name, entries, doc):
    lines = [f"package {PKG}.port.bus;", "", f"/** {doc} (generated by tools/port_multiloader.py) */",
             f"public final class {name} {{", f"\tprivate {name}() {{", "\t}", "",
             "\tpublic static void register() {"]
    for event, prio, handler in sorted(entries, key=lambda e: (e[0], e[2])):
        lines.append(f"\t\tModBus.add({event}.class, EventPriority.{prio}, {handler}, {handler.split('::')[0]}.class);")
    lines += ["\t}", "}", ""]
    (BUS / f"{name}.java").write_text("\n".join(lines))


def main():
    common, client, skipped, changed = [], [], [], 0
    for path in sorted(ROOT.rglob("*.java")):
        if BUS in path.parents:
            continue
        src = path.read_text()
        new = rewrite_code(path, src)
        if new != src:
            path.write_text(new)
            changed += 1
        entries, skip = index_entries(path, new)
        skipped += skip
        for e in entries:
            if e[0] == "__client__":
                client.append(e[1:])
            else:
                common.append(e)
    write_index("EventIndex", common, "The common @SubscribeEvent methods")
    write_index("ClientEventIndex", client, "The client-only @SubscribeEvent methods (classes with Dist.CLIENT)")
    print(f"{changed} files rewritten; index: {len(common)} common, {len(client)} client listeners")
    for s in skipped:
        print("not indexed (loader event):", s)


if __name__ == "__main__":
    main()
