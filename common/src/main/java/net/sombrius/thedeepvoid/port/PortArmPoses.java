package net.sombrius.thedeepvoid.port;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

import java.util.Map;
import java.util.function.Function;

/**
 * The mod's gun arm poses (1.20's {@code ArmPose.create}). NeoForge makes them enum values from
 * {@code META-INF/enumextensions.json} (the neoforge module's {@code ArmPoses}); Fabric applies the transformer after the
 * model's own animation (mixin). Client only.
 */
public final class PortArmPoses {
	/** NeoForge's {@code IArmPoseTransformer} */
	@FunctionalInterface
	public interface Transformer {
		void applyTransform(HumanoidModel<?> model, HumanoidRenderState state, HumanoidArm arm);
	}

	/** All the mod's poses lift both arms the same way, as in 1.99.3.1 */
	private static final Transformer AIM = (model, state, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		}
	};

	public static final Map<String, Transformer> TRANSFORMERS = Map.of("APOSTASY", AIM, "DOUBLE_BARREL_SHOTGUN", AIM, "ELDRITCH_MAW", AIM, "HERESY", AIM, "MISANTHROPY", AIM, "PERIL", AIM,
			"PERIL_OFF_HAND", AIM);

	/** The pose of a name (set by the loader's client entry point) */
	public static Function<String, HumanoidModel.ArmPose> poses = name -> HumanoidModel.ArmPose.ITEM;

	private PortArmPoses() {
	}

	public static HumanoidModel.ArmPose pose(String name) {
		return poses.apply(name);
	}
}
