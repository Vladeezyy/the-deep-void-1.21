package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature;
import net.sombrius.thedeepvoid.procedures.FleshBlobsGenerateAdditionalGenerationConditionProcedure;

/** 26.3: a feature is its own configuration; MCreator's condition before ReplaceBlobsFeature's placement */
public record RottenMossGenerateFeature(ReplaceBlobsFeature feature) implements Feature {
	public static final MapCodec<RottenMossGenerateFeature> CODEC = ReplaceBlobsFeature.CODEC.xmap(RottenMossGenerateFeature::new, RottenMossGenerateFeature::feature);

	@Override
	public MapCodec<RottenMossGenerateFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		if (!FleshBlobsGenerateAdditionalGenerationConditionProcedure.execute())
			return false;
		return this.feature.place(world, chunkGenerator, random, origin);
	}
}
