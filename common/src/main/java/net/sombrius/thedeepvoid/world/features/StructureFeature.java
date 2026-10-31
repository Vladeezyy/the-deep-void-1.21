package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.port.platform.Registrar;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;

/** MCreator's structure feature: a template placed at the origin (26.3: the feature is its configuration) */
public record StructureFeature(Identifier structure, boolean randomRotation, boolean randomMirror, HolderSet<Block> ignoredBlocks, Vec3i offset) implements Feature {
	public static final Registrar<MapCodec<? extends Feature>> REGISTRY = Registrar.create(Registries.FEATURE_TYPE, TheDeepVoidMod.MODID);
	public static final MapCodec<StructureFeature> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(Identifier.CODEC.fieldOf("structure").forGetter(StructureFeature::structure), com.mojang.serialization.Codec.BOOL.fieldOf("random_rotation").orElse(false).forGetter(StructureFeature::randomRotation),
					com.mojang.serialization.Codec.BOOL.fieldOf("random_mirror").orElse(false).forGetter(StructureFeature::randomMirror),
					RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("ignored_blocks").forGetter(StructureFeature::ignoredBlocks),
					Vec3i.offsetCodec(48).optionalFieldOf("offset", Vec3i.ZERO).forGetter(StructureFeature::offset))
			.apply(builder, StructureFeature::new));
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<StructureFeature>> STRUCTURE_FEATURE = REGISTRY.register("structure_feature", () -> CODEC);

	@Override
	public MapCodec<StructureFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		Rotation rotation = this.randomRotation ? Rotation.getRandom(random) : Rotation.NONE;
		Mirror mirror = this.randomMirror ? Mirror.values()[random.nextInt(2)] : Mirror.NONE;
		BlockPos placePos = origin.offset(this.offset);
		StructureTemplateManager structureManager = worldGenLevel.getLevel().getServer().getStructureTemplateManager();
		StructureTemplate template = structureManager.getOrCreate(this.structure);
		StructurePlaceSettings placeSettings = (new StructurePlaceSettings()).setRotation(rotation).setMirror(mirror).setRandom(random).setIgnoreEntities(false)
				.addProcessor(new BlockIgnoreProcessor(this.ignoredBlocks.stream().map(Holder::value).toList()));
		// A feature may only write into its own chunk and the neighbouring ones; clip the template to that area, one
		// block inside (placing reads the neighbours of the edge blocks)
		ChunkPos chunk = ChunkPos.containing(origin);
		placeSettings.setBoundingBox(new BoundingBox(chunk.getMinBlockX() - 15, worldGenLevel.getMinY(), chunk.getMinBlockZ() - 15, chunk.getMaxBlockX() + 15,
				worldGenLevel.getMaxY(), chunk.getMaxBlockZ() + 15));
		template.placeInWorld(worldGenLevel, placePos, placePos, placeSettings, random, 4);
		return true;
	}
}
