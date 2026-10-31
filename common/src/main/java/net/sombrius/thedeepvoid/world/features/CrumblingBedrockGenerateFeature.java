package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.sombrius.thedeepvoid.procedures.CrumblingBedrockGenerateAdditionalGenerationConditionProcedure;

/** 26.3: a feature is its own configuration; MCreator's condition before OreFeature's placement */
public record CrumblingBedrockGenerateFeature(OreFeature feature) implements Feature {
	public static final MapCodec<CrumblingBedrockGenerateFeature> CODEC = OreFeature.CODEC.xmap(CrumblingBedrockGenerateFeature::new, CrumblingBedrockGenerateFeature::feature);

	@Override
	public MapCodec<CrumblingBedrockGenerateFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		if (!CrumblingBedrockGenerateAdditionalGenerationConditionProcedure.execute())
			return false;
		return this.feature.place(world, chunkGenerator, random, origin);
	}
}
