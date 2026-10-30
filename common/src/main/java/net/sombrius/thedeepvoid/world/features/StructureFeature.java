package net.sombrius.thedeepvoid.world.features;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.ChunkPos;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.port.platform.Registrar;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.world.features.configurations.StructureFeatureConfiguration;

public class StructureFeature extends Feature<StructureFeatureConfiguration> {
	public static final Registrar<Feature<?>> REGISTRY = Registrar.create(Registries.FEATURE, TheDeepVoidMod.MODID);
	public static final RegistryEntry<Feature<?>, StructureFeature> STRUCTURE_FEATURE = REGISTRY.register("structure_feature", () -> new StructureFeature(StructureFeatureConfiguration.CODEC));

	public StructureFeature(Codec<StructureFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<StructureFeatureConfiguration> context) {
		RandomSource random = context.random();
		WorldGenLevel worldGenLevel = context.level();
		StructureFeatureConfiguration config = context.config();
		Rotation rotation = config.randomRotation() ? Rotation.getRandom(random) : Rotation.NONE;
		Mirror mirror = config.randomMirror() ? Mirror.values()[random.nextInt(2)] : Mirror.NONE;
		BlockPos placePos = context.origin().offset(config.offset());
		// Load the structure template
		StructureTemplateManager structureManager = worldGenLevel.getLevel().getServer().getStructureManager();
		StructureTemplate template = structureManager.getOrCreate(config.structure());
		StructurePlaceSettings placeSettings = (new StructurePlaceSettings()).setRotation(rotation).setMirror(mirror).setRandom(random).setIgnoreEntities(false)
				.addProcessor(new BlockIgnoreProcessor(config.ignoredBlocks().stream().map(Holder::value).toList()));
		// A feature may only write into its own chunk and the neighbouring ones; clip the template to that area, one
		// block inside (placing reads the neighbours of the edge blocks)
		// (1.20.1 dropped the blocks outside it as well, logging an error for each).
		ChunkPos chunk = ChunkPos.containing(context.origin());
		placeSettings.setBoundingBox(new BoundingBox(chunk.getMinBlockX() - 15, worldGenLevel.getMinY(), chunk.getMinBlockZ() - 15, chunk.getMaxBlockX() + 15,
				worldGenLevel.getMaxY(), chunk.getMaxBlockZ() + 15));
		template.placeInWorld(worldGenLevel, placePos, placePos, placeSettings, random, 4);
		return true;
	}
}
