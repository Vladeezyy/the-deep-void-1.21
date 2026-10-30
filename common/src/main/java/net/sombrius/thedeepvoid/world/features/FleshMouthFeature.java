
package net.sombrius.thedeepvoid.world.features;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.sombrius.thedeepvoid.procedures.FleshyFormationUndergroundAdditionalGenerationConditionProcedure;
import net.sombrius.thedeepvoid.world.features.configurations.StructureFeatureConfiguration;

public class FleshMouthFeature extends StructureFeature {
	public FleshMouthFeature() {
		super(StructureFeatureConfiguration.CODEC);
	}

	public boolean place(FeaturePlaceContext<StructureFeatureConfiguration> context) {
		WorldGenLevel world = context.level();
		int x = context.origin().getX();
		int y = context.origin().getY();
		int z = context.origin().getZ();
		if (!FleshyFormationUndergroundAdditionalGenerationConditionProcedure.execute(world, x, y, z))
			return false;
		return super.place(context);
	}
}
