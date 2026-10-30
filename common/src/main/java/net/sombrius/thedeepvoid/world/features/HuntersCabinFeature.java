
package net.sombrius.thedeepvoid.world.features;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.sombrius.thedeepvoid.procedures.HuntersCabinAdditionalGenerationConditionProcedure;
import net.sombrius.thedeepvoid.world.features.configurations.StructureFeatureConfiguration;

public class HuntersCabinFeature extends StructureFeature {
	public HuntersCabinFeature() {
		super(StructureFeatureConfiguration.CODEC);
	}

	public boolean place(FeaturePlaceContext<StructureFeatureConfiguration> context) {
		WorldGenLevel world = context.level();
		int x = context.origin().getX();
		int y = context.origin().getY();
		int z = context.origin().getZ();
		if (!HuntersCabinAdditionalGenerationConditionProcedure.execute())
			return false;
		return super.place(context);
	}
}
