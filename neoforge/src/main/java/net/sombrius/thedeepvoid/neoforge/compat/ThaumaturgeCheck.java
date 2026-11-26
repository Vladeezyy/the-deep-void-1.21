package net.sombrius.thedeepvoid.neoforge.compat;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.RegistryManager;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Thaumaturge compat self-test, inert unless the {@code deepvoid.thaumtest} system property is set
 * ({@code tools/thaumtest.sh}). Without Thaumaturge: the compat pack is not there. With it ({@code -Pwith_thaumaturge}):
 * the pack is on, the mod's aspects are registered, the Void Matter and the Lurker have data map entries and
 * Thaumaturge's aspect index gives the Void Matter the Abyssus aspect. Every item of the mod in the creative tabs
 * (spawn eggs aside) has aspects (a data map entry or a recipe Thaumaturge derives them from) and every mob with a spawn
 * egg has entity aspects; lists the ones without, and writes every item's and mob's aspects to
 * {@code thaumtest-aspects.txt} in the game folder. Thaumaturge is read by reflection: no compile dependency on it.
 * Logs {@code [thaumtest] PASS} or {@code [thaumtest] FAIL} and stops the server.
 */
public final class ThaumaturgeCheck {
	public static final String MODE = System.getProperty("deepvoid.thaumtest");
	/** The mod's own aspects (tools/gen_thaumaturge.py) */
	public static final String[] ASPECTS = {"abyssus", "caro", "os", "oculus"};
	// Thaumaturge builds its aspect index on the first server tick: check a little later
	private static final int TICK = 40;
	private static int ticks;

	private ThaumaturgeCheck() {
	}

	public static void tick(ServerTickEvent.Post event) {
		if (++ticks != TICK)
			return;
		MinecraftServer server = event.getServer();
		boolean pass;
		try {
			pass = run(server);
		} catch (Exception e) {
			TheDeepVoidMod.LOGGER.error("[thaumtest] check failed", e);
			pass = false;
		}
		TheDeepVoidMod.LOGGER.info("[thaumtest] {}", pass ? "PASS" : "FAIL");
		server.halt(false);
	}

	private static boolean run(MinecraftServer server) throws Exception {
		boolean available = server.getPackRepository().getAvailableIds().contains(ThaumaturgeCompat.PACK_ID);
		if (!ModList.get().isLoaded(ThaumaturgeCompat.MOD_ID)) {
			TheDeepVoidMod.LOGGER.info("[thaumtest] Thaumaturge not loaded, compat pack available: {}", available);
			return !available;
		}
		boolean selected = server.getPackRepository().getSelectedIds().contains(ThaumaturgeCompat.PACK_ID);
		ResourceKey<Registry<Object>> aspectKey = ResourceKey.createRegistryKey(rl("thaumaturge", "aspect"));
		Registry<Object> aspects = server.registryAccess().lookup(aspectKey).orElse(null);
		List<String> missingAspects = new ArrayList<>();
		for (String aspect : ASPECTS)
			if (aspects == null || !aspects.containsKey(rl(TheDeepVoidMod.MODID, aspect)))
				missingAspects.add(aspect);
		DataMapType<Item, ?> baseAspects = RegistryManager.getDataMap(Registries.ITEM, rl("thaumaturge", "base_aspects"));
		DataMapType<EntityType<?>, ?> entityAspects = RegistryManager.getDataMap(Registries.ENTITY_TYPE, rl("thaumaturge", "entity_aspects"));
		if (baseAspects == null || entityAspects == null) {
			TheDeepVoidMod.LOGGER.error("[thaumtest] Thaumaturge data map types not found");
			return false;
		}
		Item voidMatter = BuiltInRegistries.ITEM.getValue(rl(TheDeepVoidMod.MODID, "void_matter"));
		Object voidMatterEntry = voidMatter.builtInRegistryHolder().getData(baseAspects);
		Object lurkerEntry = BuiltInRegistries.ENTITY_TYPE.getValue(rl(TheDeepVoidMod.MODID, "lurker")).builtInRegistryHolder().getData(entityAspects);
		TheDeepVoidMod.LOGGER.info("[thaumtest] pack on {}, missing aspects {}, void_matter entry {}, lurker entry {}", selected, missingAspects, voidMatterEntry != null, lurkerEntry != null);

		Class<?> access = Class.forName("com.leclowndu93150.thaumaturge.api.aspect.AspectIndexAccess");
		var of = access.getMethod("of", ItemStack.class);
		Set<String> voidMatterIndex = aspectIds(of.invoke(null, new ItemStack(voidMatter)));
		// required: what players see in the creative tabs (no spawn eggs), and every mob with a spawn egg
		CreativeModeTabs.tryRebuildTabContents(server.getWorldData().enabledFeatures(), true, server.registryAccess());
		Set<Item> shown = new HashSet<>();
		for (CreativeModeTab tab : BuiltInRegistries.CREATIVE_MODE_TAB)
			tab.getDisplayItems().forEach(stack -> shown.add(stack.getItem()));
		List<String> without = new ArrayList<>();
		List<String> optionalWithout = new ArrayList<>();
		List<String> lines = new ArrayList<>();
		int total = 0;
		for (Item item : BuiltInRegistries.ITEM) {
			Identifier id = BuiltInRegistries.ITEM.getKey(item);
			if (!id.getNamespace().equals(TheDeepVoidMod.MODID) || item instanceof SpawnEggItem)
				continue;
			Object list = of.invoke(null, new ItemStack(item));
			String aspectText = describe(list);
			boolean required = shown.contains(item);
			if (required)
				total++;
			if (aspectText.isEmpty())
				(required ? without : optionalWithout).add(id.getPath());
			lines.add(id.getPath() + (item.builtInRegistryHolder().getData(baseAspects) != null ? " [base]" : "") + (required ? "" : " [not in tabs]") + ": " + aspectText);
		}
		List<String> mobsWithout = new ArrayList<>();
		List<String> optionalMobsWithout = new ArrayList<>();
		int mobs = 0;
		for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
			Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
			if (!id.getNamespace().equals(TheDeepVoidMod.MODID) || !hasAttributes(type))
				continue;
			// the mod's spawn eggs are not in SpawnEggItem's map (deferred), so by name
			boolean required = BuiltInRegistries.ITEM.containsKey(rl(TheDeepVoidMod.MODID, id.getPath() + "_spawn_egg"));
			if (required)
				mobs++;
			Object entry = type.builtInRegistryHolder().getData(entityAspects);
			if (entry == null)
				(required ? mobsWithout : optionalMobsWithout).add(id.getPath());
			lines.add("mob " + id.getPath() + (required ? "" : " [no spawn egg]") + ": " + (entry == null ? "" : entry));
		}
		Files.write(FMLPaths.GAMEDIR.get().resolve("thaumtest-aspects.txt"), lines);
		TheDeepVoidMod.LOGGER.info("[thaumtest] index: void_matter {}; {}/{} items in the creative tabs have aspects; without: {}", voidMatterIndex, total - without.size(), total, without);
		TheDeepVoidMod.LOGGER.info("[thaumtest] items outside the creative tabs without aspects: {}", optionalWithout);
		TheDeepVoidMod.LOGGER.info("[thaumtest] mobs: {}/{} with a spawn egg have entity aspects; without: {}", mobs - mobsWithout.size(), mobs, mobsWithout);
		TheDeepVoidMod.LOGGER.info("[thaumtest] mobs without a spawn egg and without entity aspects: {}", optionalMobsWithout);
		return selected && missingAspects.isEmpty() && voidMatterEntry != null && lurkerEntry != null
				&& voidMatterIndex.contains(TheDeepVoidMod.MODID + ":abyssus") && without.isEmpty() && mobsWithout.isEmpty();
	}

	/** "aspect amount, ..." of an AspectList, sorted by amount */
	@SuppressWarnings("unchecked")
	private static String describe(Object aspectList) throws ReflectiveOperationException {
		var amountOf = aspectList.getClass().getMethod("amountOf", Holder.class);
		List<String> parts = new ArrayList<>();
		for (Holder<?> holder : (Set<Holder<?>>) aspectList.getClass().getMethod("aspects").invoke(aspectList))
			parts.add(holder.unwrapKey().map(k -> k.identifier().getPath()).orElse("?") + " " + amountOf.invoke(aspectList, holder));
		parts.sort(java.util.Comparator.comparingInt((String part) -> -Integer.parseInt(part.substring(part.lastIndexOf(' ') + 1))));
		return String.join(", ", parts);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static boolean hasAttributes(EntityType<?> type) {
		return DefaultAttributes.hasSupplier((EntityType) type);
	}

	@SuppressWarnings("unchecked")
	private static Set<String> aspectIds(Object aspectList) throws ReflectiveOperationException {
		Set<Holder<?>> set = (Set<Holder<?>>) aspectList.getClass().getMethod("aspects").invoke(aspectList);
		return set.stream().map(h -> h.unwrapKey().map(k -> k.identifier().toString()).orElse("?")).collect(Collectors.toCollection(TreeSet::new));
	}

	private static Identifier rl(String namespace, String path) {
		return Identifier.fromNamespaceAndPath(namespace, path);
	}
}
