package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.sombrius.thedeepvoid.world.features.StructureFeature;
import net.sombrius.thedeepvoid.procedures.FleshyFormationUndergroundAdditionalGenerationConditionProcedure;

/** 26.3: a feature is its own configuration; MCreator's condition before StructureFeature's placement */
public record FleshMouthFeature(StructureFeature feature) implements Feature {
	public static final MapCodec<FleshMouthFeature> CODEC = StructureFeature.CODEC.xmap(FleshMouthFeature::new, FleshMouthFeature::feature);

	@Override
	public MapCodec<FleshMouthFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		if (!FleshyFormationUndergroundAdditionalGenerationConditionProcedure.execute(world, x, y, z))
			return false;
		return this.feature.place(world, chunkGenerator, random, origin);
	}
}
