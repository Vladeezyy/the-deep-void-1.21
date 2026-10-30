#!/usr/bin/env python3
"""GeckoLib 4 -> 5, The Deep Void's own rules after tools/port_geckolib5.py (idempotent; repo root):
- the controllers' leftovers of GeckoLib 4 in entities and items: getController(), the STOPPED state,
  forceAnimationReset(), getPartialTick() (port.GeoAnims)
- GeoItem / GeoArmor items: the same AnimationTest as the entities"""
import glob
import re

ROOT = 'common/src/main/java/net/sombrius/thedeepvoid/'
PKG = 'net.sombrius.thedeepvoid'


def port(s, cls):
    s = re.sub(r'(\w+)\.getController\(\)\.getAnimationState\(\) == AnimationController\.State\.STOPPED', rf'{PKG}.port.GeoAnims.stopped(\1.controller())', s)
    s = re.sub(r'(\w+)\.getController\(\)\.getAnimationState\(\) != AnimationController\.State\.STOPPED', rf'!{PKG}.port.GeoAnims.stopped(\1.controller())', s)
    s = re.sub(r'\bevent\.getController\(\)', 'event.controller()', s)
    s = s.replace('.forceAnimationReset()', '.reset()')
    s = re.sub(r'\bevent\.getPartialTick\(\)', f'{PKG}.port.GeoAnims.partialTick(event)', s)
    if cls and '(AnimationState event)' in s:
        s = s.replace('(AnimationState event)', f'(AnimationTest<{cls}> event)')
        s = s.replace('import software.bernie.geckolib.animation.AnimationState;', 'import software.bernie.geckolib.animation.state.AnimationTest;')
        s = re.sub(r'new AnimationController<>\(this, ', 'new AnimationController<>(', s)
    # GeckoLib 5: models and animations live in geckolib/models and geckolib/animations, named without folder and suffix
    s = re.sub(r'Identifier\.fromNamespaceAndPath\("the_deep_void", "animations/([\w.-]+?)\.animation\.json"\)', r'Identifier.fromNamespaceAndPath("the_deep_void", "\1")', s)
    s = re.sub(r'Identifier\.fromNamespaceAndPath\("the_deep_void", "geo/([\w.-]+?)\.geo\.json"\)', r'Identifier.fromNamespaceAndPath("the_deep_void", "\1")', s)
    # GeckoLib 5.4 crashes on an animation the file does not have (GeckoLib 4 skipped it): port.GeoAnims.Controller
    s = re.sub(r'\bnew AnimationController<>\("', f'new {PKG}.port.GeoAnims.Controller<>("', s)
    # the armor stand check returned CONTINUE either way; GeckoLib 5 render states have no entity
    s = re.sub(r'\n\t+Entity entity = \(Entity\) event\.getData\(DataTickets\.ENTITY\);\n\t+if \(entity instanceof ArmorStand\) \{\n\t+return PlayState\.CONTINUE;\n\t+\}', '', s)
    s = s.replace('software.bernie.geckolib.cache.object.GeoBone', 'software.bernie.geckolib.cache.model.GeoBone')
    s = re.sub(r'\bbone\.getPivot([XYZ])\(\)', lambda m: 'bone.pivot' + m.group(1) + '()', s)
    s = s.replace('import software.bernie.geckolib.animation.PlayState;', 'import software.bernie.geckolib.animation.object.PlayState;')
    s = s.replace('import software.bernie.geckolib.animation.AnimatableManager;', 'import software.bernie.geckolib.animatable.manager.AnimatableManager;')
    return s


ARMS_RENDERER = """package {pkg}.item.renderer;

import {pkg}.port.GeoArmsItemRenderer;
import {pkg}.item.model.{name}Model;
import {pkg}.item.{name};

public class {name}Renderer extends GeoArmsItemRenderer<{name}> {{
	public {name}Renderer() {{
		super(new {name}Model());
	}}
}}
"""

GEO_RENDERER = """	@Override
	public boolean isPerspectiveAware() {
		return true;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private %(r)s renderer;

			@Override
			public GeoItemRenderer<?> getGeoItemRenderer() {
				if (this.renderer == null)
					this.renderer = new %(r)s();
				return this.renderer;
			}
		});
	}

"""


def arms_renderer(path):
    """MCreator's first person weapon renderers (GeckoLib 4 renderRecursively with the player's arms): one shared
    GeckoLib 5 renderer, port.GeoArmsItemRenderer"""
    s = open(path, encoding='utf-8').read()
    m = re.search(r'public class (\w+)Renderer extends GeoItemRenderer<\1>', s)
    if not m or '"RightArm"' not in s:
        return
    open(path, 'w', encoding='utf-8', newline='\n').write(ARMS_RENDERER.format(pkg=PKG, name=m.group(1)))


ARMOR_RENDERER = """package {pkg}.client.renderer;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import {pkg}.item.{item};
import {pkg}.item.model.{model};
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class {name}<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<{item}, R> {{
	public {name}() {{
		super(new {model}());
	}}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {{
		return RenderTypes.entityTranslucent(texture);
	}}
}}
"""

GEO_ARMOR = """	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private %(r)s<?> renderer;

			@Override
			public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack itemStack, EquipmentSlot equipmentSlot) {
				if (this.renderer == null)
					this.renderer = new %(r)s<>();
				return this.renderer;
			}
		});
	}
"""


def armor_renderer(path):
    """GeckoLib 5 armor: the armor bones have GeckoLib's default names (armorHead...), the renderer works on render states"""
    s = open(path, encoding='utf-8').read()
    m = re.search(r'public class (\w+) extends GeoArmorRenderer<(\w+)> \{', s)
    if not m:
        return
    model = re.search(r'super\(new (\w+)\(\)\);', s).group(1)
    open(path, 'w', encoding='utf-8', newline='\n').write(ARMOR_RENDERER.format(pkg=PKG, name=m.group(1), item=m.group(2), model=model))


def geo_armor(s):
    """GeckoLib 5 draws its armor itself (a mixin in the vanilla armor layer, both loaders) from the item's render provider"""
    m = re.search(r'\tpublic IClientItemExtensions clientExtensions\(\) \{\n\t\treturn new IClientItemExtensions\(\) \{\n\t\t\tprivate GeoArmorRenderer<\?> renderer;\n\n'
                  r'\t\t\t@Override\n\t\t\tpublic HumanoidModel<\?> getHumanoidArmorModel\([^)]*\) \{\n\t\t\t\tif \(this\.renderer == null\)\n\t\t\t\t\tthis\.renderer = new (\w+)\(\);\n'
                  r'\t\t\t\tthis\.renderer\.prepForRender\([^)]*\);\n\t\t\t\treturn this\.renderer;\n\t\t\t\}\n\t\t\};\n\t\}\n', s)
    if not m:
        return s
    s = s[:m.start()] + GEO_ARMOR % {'r': m.group(1)} + s[m.end():]
    s = s.replace(', net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Item {', ' {')
    s = s.replace('import software.bernie.geckolib.animatable.GeoItem;', 'import software.bernie.geckolib.animatable.GeoItem;\nimport software.bernie.geckolib.animatable.client.GeoRenderProvider;')
    if 'import java.util.function.Consumer;' not in s:
        s = s.replace('import software.bernie.geckolib.animatable.GeoItem;', 'import software.bernie.geckolib.animatable.GeoItem;\nimport java.util.function.Consumer;')
    s = re.sub(r'new AnimationController\(this, ("\w+", \d+, this::\w+)\)', r'new AnimationController<>(\1)', s)
    return s


def geo_item(s):
    """GeckoLib 5: an item's renderer comes from its render provider (the geckolib:geckolib special item model), not
    from NeoForge's BEWLR; the animations see the render perspective (perspective-aware: one instance per perspective)"""
    m = re.search(r'\n\t\t\tprivate final BlockEntityWithoutLevelRenderer renderer = new (\w+)\(\);\n\n\t\t\t@Override\n\t\t\tpublic BlockEntityWithoutLevelRenderer getCustomRenderer\(\) \{\n\t\t\t\treturn renderer;\n\t\t\t\}\n', s)
    if not m:
        return s
    s = s[:m.start()] + s[m.end():]
    s = s.replace('\tpublic IClientItemExtensions clientExtensions() {', GEO_RENDERER % {'r': m.group(1)} + '\tpublic IClientItemExtensions clientExtensions() {', 1)
    s = s.replace('import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;\n', '')
    s = s.replace('import software.bernie.geckolib.animatable.GeoItem;', 'import software.bernie.geckolib.animatable.GeoItem;\nimport software.bernie.geckolib.animatable.client.GeoRenderProvider;\nimport software.bernie.geckolib.renderer.GeoItemRenderer;')
    if 'import java.util.function.Consumer;' not in s:
        s = s.replace('import software.bernie.geckolib.animatable.GeoItem;', 'import software.bernie.geckolib.animatable.GeoItem;\nimport java.util.function.Consumer;')
    # the renderer told the item its perspective (a static field); GeckoLib 5 has it in the animation test
    s = s.replace('this.transformType != null ? this.transformType.firstPerson() : false', f'{PKG}.port.GeoAnims.firstPerson(event)')
    s = re.sub(r'new AnimationController\(this, ("\w+", \d+, this::\w+)\)', r'new AnimationController<>(\1)', s)
    return s


def item_model(path):
    """GeckoLib 5: the model and texture of an item are chosen from the render state (the mod's are constant)"""
    s = open(path, encoding='utf-8').read()
    m = re.search(r'extends GeoModel<(\w+)>', s)
    if not m or 'GeoRenderState' in s:
        return
    s = re.sub(r'public Identifier get(Model|Texture)Resource\(' + m.group(1) + r' \w+\)', r'public Identifier get\1Resource(GeoRenderState renderState)', s)
    s = s.replace('import software.bernie.geckolib.model.GeoModel;', 'import software.bernie.geckolib.model.GeoModel;\nimport software.bernie.geckolib.renderer.base.GeoRenderState;')
    open(path, 'w', encoding='utf-8', newline='\n').write(s)


def main():
    for path in glob.glob(ROOT + 'item/model/*.java'):
        item_model(path)
    for path in glob.glob(ROOT + 'item/renderer/*.java'):
        arms_renderer(path)
    for path in glob.glob(ROOT + 'client/renderer/*.java'):
        armor_renderer(path)
    changed = 0
    for path in glob.glob(ROOT + '**/*.java', recursive=True):
        s = open(path, encoding='utf-8').read()
        if 'software.bernie.geckolib' not in s:
            continue
        m = re.search(r'^public (?:abstract )?class (\w+)[^{]*implements [^{]*\b(?:GeoItem|GeoEntity)\b', s, re.M)
        o = port(s, m.group(1) if m else None)
        if m and 'implements GeoItem' in s:
            o = geo_armor(geo_item(o))
            o = re.sub(r'\bAnimationController (\w+) = new AnimationController<>\(', rf'AnimationController<{m.group(1)}> \1 = new AnimationController<>(', o)
            o = re.sub(r'\bnew AnimationController<>\("', f'new {PKG}.port.GeoAnims.Controller<>("', o)
        if o != s:
            open(path, 'w', encoding='utf-8', newline='\n').write(o)
            changed += 1
    print('changed', changed)


if __name__ == '__main__':
    main()
