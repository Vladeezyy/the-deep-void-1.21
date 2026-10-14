
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.core.registries.Registries;

import net.sombrius.thedeepvoid.block.entity.WatcherOnBlockEntity;
import net.sombrius.thedeepvoid.block.entity.VoidriumSawTrapBlockEntity;
import net.sombrius.thedeepvoid.block.entity.VoidriumGlassBlockEntity;
import net.sombrius.thedeepvoid.block.entity.SoulOrbPillarBlockEntity;
import net.sombrius.thedeepvoid.block.entity.SoulForgeBlockEntity;
import net.sombrius.thedeepvoid.block.entity.InfestedBoneCrawlerEggBlockEntity;
import net.sombrius.thedeepvoid.block.entity.GluttonBlockBlockEntity;
import net.sombrius.thedeepvoid.block.entity.DungeonDarkSteelBarsActiveBlockEntity;
import net.sombrius.thedeepvoid.block.entity.DeepslateSarcophagusBlockEntity;
import net.sombrius.thedeepvoid.block.entity.DeepslateSarcophagusBlockBlockEntity;
import net.sombrius.thedeepvoid.block.entity.BoneCrawlerEggBlockEntity;
import net.sombrius.thedeepvoid.block.entity.AncientPotBlockBlockEntity;
import net.sombrius.thedeepvoid.block.entity.AncientDeepslateSawTrapBlockEntity;
import net.sombrius.thedeepvoid.block.entity.AncientDeepslateDartTrapBlockEntity;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class TheDeepVoidModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TheDeepVoidMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> GLUTTON_BLOCK = register("glutton_block", TheDeepVoidModBlocks.GLUTTON_BLOCK, GluttonBlockBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> DEEPSLATE_SARCOPHAGUS = register("deepslate_sarcophagus", TheDeepVoidModBlocks.DEEPSLATE_SARCOPHAGUS, DeepslateSarcophagusBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> DEEPSLATE_SARCOPHAGUS_BLOCK = register("deepslate_sarcophagus_block", TheDeepVoidModBlocks.DEEPSLATE_SARCOPHAGUS_BLOCK, DeepslateSarcophagusBlockBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> BONE_CRAWLER_EGG = register("bone_crawler_egg", TheDeepVoidModBlocks.BONE_CRAWLER_EGG, BoneCrawlerEggBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> VOIDRIUM_GLASS = register("voidrium_glass", TheDeepVoidModBlocks.VOIDRIUM_GLASS, VoidriumGlassBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> SOUL_ORB_PILLAR = register("soul_orb_pillar", TheDeepVoidModBlocks.SOUL_ORB_PILLAR, SoulOrbPillarBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> SOUL_FORGE = register("soul_forge", TheDeepVoidModBlocks.SOUL_FORGE, SoulForgeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> ANCIENT_POT_BLOCK = register("ancient_pot_block", TheDeepVoidModBlocks.ANCIENT_POT_BLOCK, AncientPotBlockBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> WATCHER_ON = register("watcher_on", TheDeepVoidModBlocks.WATCHER_ON, WatcherOnBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> ANCIENT_DEEPSLATE_SAW_TRAP = register("ancient_deepslate_saw_trap", TheDeepVoidModBlocks.ANCIENT_DEEPSLATE_SAW_TRAP, AncientDeepslateSawTrapBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> VOIDRIUM_SAW_TRAP = register("voidrium_saw_trap", TheDeepVoidModBlocks.VOIDRIUM_SAW_TRAP, VoidriumSawTrapBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> ANCIENT_DEEPSLATE_DART_TRAP = register("ancient_deepslate_dart_trap", TheDeepVoidModBlocks.ANCIENT_DEEPSLATE_DART_TRAP, AncientDeepslateDartTrapBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> INFESTED_BONE_CRAWLER_EGG = register("infested_bone_crawler_egg", TheDeepVoidModBlocks.INFESTED_BONE_CRAWLER_EGG, InfestedBoneCrawlerEggBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> DUNGEON_DARK_STEEL_BARS_ACTIVE = register("dungeon_dark_steel_bars_active", TheDeepVoidModBlocks.DUNGEON_DARK_STEEL_BARS_ACTIVE, DungeonDarkSteelBarsActiveBlockEntity::new);

	private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, GLUTTON_BLOCK.get(), (blockEntity, side) -> ((GluttonBlockBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DEEPSLATE_SARCOPHAGUS.get(), (blockEntity, side) -> ((DeepslateSarcophagusBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DEEPSLATE_SARCOPHAGUS_BLOCK.get(), (blockEntity, side) -> ((DeepslateSarcophagusBlockBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BONE_CRAWLER_EGG.get(), (blockEntity, side) -> ((BoneCrawlerEggBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, VOIDRIUM_GLASS.get(), (blockEntity, side) -> ((VoidriumGlassBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SOUL_ORB_PILLAR.get(), (blockEntity, side) -> ((SoulOrbPillarBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SOUL_FORGE.get(), (blockEntity, side) -> ((SoulForgeBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ANCIENT_POT_BLOCK.get(), (blockEntity, side) -> ((AncientPotBlockBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, WATCHER_ON.get(), (blockEntity, side) -> ((WatcherOnBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ANCIENT_DEEPSLATE_SAW_TRAP.get(), (blockEntity, side) -> ((AncientDeepslateSawTrapBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, VOIDRIUM_SAW_TRAP.get(), (blockEntity, side) -> ((VoidriumSawTrapBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ANCIENT_DEEPSLATE_DART_TRAP.get(), (blockEntity, side) -> ((AncientDeepslateDartTrapBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, INFESTED_BONE_CRAWLER_EGG.get(), (blockEntity, side) -> ((InfestedBoneCrawlerEggBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DUNGEON_DARK_STEEL_BARS_ACTIVE.get(), (blockEntity, side) -> ((DungeonDarkSteelBarsActiveBlockEntity) blockEntity).getItemHandler());
	}
}
