package net.sombrius.thedeepvoid.port;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

import java.util.List;
import java.util.Map;

/** MCreator's armor models (a HumanoidModel of Blockbench parts) on 1.21.2+ */
public final class PortArmorModels {
	private PortArmorModels() {
	}

	/**
	 * The head part of a HumanoidModel has the hat as a child since 1.21.2: the Blockbench head goes into a part
	 * that has its pose (the pivot HumanoidModel turns) and an empty hat.
	 */
	public static ModelPart head(ModelPart head) {
		ModelPart part = new ModelPart(List.of(), Map.of("hat", new ModelPart(List.of(), Map.of()), "the_deep_void_head", head));
		part.setInitialPose(head.getInitialPose());
		part.loadPose(head.getInitialPose());
		head.setInitialPose(PartPose.ZERO);
		head.loadPose(PartPose.ZERO);
		return part;
	}
}
