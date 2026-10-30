package net.sombrius.thedeepvoid.fabric;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;

import net.sombrius.thedeepvoid.port.PortArmPoses;

/**
 * NeoForge's custom arm poses on Fabric: the player renderer stores the mod's pose names in the render state
 * (AvatarRendererMixin), the humanoid model applies their transformer after its own animation (HumanoidModelMixin).
 */
public final class FabricArmPoses {
	/** The last pose name PortArmPoses.pose() was asked for */
	public static String lastPose;

	/** The mod's pose names of a humanoid render state, by HumanoidArm ordinal (HumanoidRenderStateMixin) */
	public interface PoseState {
		String[] deepvoid$poses();
	}

	private FabricArmPoses() {
	}

	public static void apply(HumanoidModel<?> model, HumanoidRenderState state) {
		String[] poses = ((PoseState) state).deepvoid$poses();
		for (HumanoidArm arm : HumanoidArm.values()) {
			String name = poses[arm.ordinal()];
			if (name != null)
				PortArmPoses.TRANSFORMERS.get(name).applyTransform(model, state, arm);
		}
	}
}
