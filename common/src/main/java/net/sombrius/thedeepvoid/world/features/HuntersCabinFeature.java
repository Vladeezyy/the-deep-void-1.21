package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.sombrius.thedeepvoid.world.features.StructureFeature;
import net.sombrius.thedeepvoid.procedures.HuntersCabinAdditionalGenerationConditionProcedure;

/** 26.3: a feature is its own configuration; MCreator's condition before StructureFeature's placement */
public record HuntersCabinFeature(StructureFeature feature) implements Feature {
	public static final MapCodec<HuntersCabinFeature> CODEC = StructureFeature.CODEC.xmap(HuntersCabinFeature::new, HuntersCabinFeature::feature);

	@Override
	public MapCodec<HuntersCabinFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		if (!HuntersCabinAdditionalGenerationConditionProcedure.execute())
			return false;
		return this.feature.place(world, chunkGenerator, random, origin);
	}
}
