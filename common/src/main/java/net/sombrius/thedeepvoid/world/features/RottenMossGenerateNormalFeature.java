package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature;
import net.sombrius.thedeepvoid.procedures.DesolateSoilGenerateNormalAdditionalGenerationConditionProcedure;

/** 26.3: a feature is its own configuration; MCreator's condition before ReplaceBlobsFeature's placement */
public record RottenMossGenerateNormalFeature(ReplaceBlobsFeature feature) implements Feature {
	public static final MapCodec<RottenMossGenerateNormalFeature> CODEC = ReplaceBlobsFeature.CODEC.xmap(RottenMossGenerateNormalFeature::new, RottenMossGenerateNormalFeature::feature);

	@Override
	public MapCodec<RottenMossGenerateNormalFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		if (!DesolateSoilGenerateNormalAdditionalGenerationConditionProcedure.execute())
			return false;
		return this.feature.place(world, chunkGenerator, random, origin);
	}
}
