package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.sombrius.thedeepvoid.procedures.FleshyFormationUndergroundAdditionalGenerationConditionProcedure;

/** 26.3: a feature is its own configuration; MCreator's condition before TreeFeature's placement */
public record FleshyFormationUndergroundFeature(TreeFeature feature) implements Feature {
	public static final MapCodec<FleshyFormationUndergroundFeature> CODEC = TreeFeature.CODEC.xmap(FleshyFormationUndergroundFeature::new, FleshyFormationUndergroundFeature::feature);

	@Override
	public MapCodec<FleshyFormationUndergroundFeature> codec() {
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
