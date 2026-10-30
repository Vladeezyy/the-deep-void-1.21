package net.sombrius.thedeepvoid.fabric;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.model.HumanoidModel;

import net.sombrius.thedeepvoid.port.PortArmPoses;

import java.util.Map;
import java.util.WeakHashMap;

/** The mod's gun arm poses on Fabric (client thread): which pose each player's hands got this frame */
public final class FabricArmPoses {
	/** The last pose name PortArmPoses.pose() was asked for */
	public static String lastPose;
	private static final Map<LivingEntity, String[]> POSES = new WeakHashMap<>();

	private FabricArmPoses() {
	}

	public static void clear(LivingEntity entity, InteractionHand hand) {
		String[] poses = POSES.get(entity);
		if (poses != null)
			poses[hand.ordinal()] = null;
	}

	public static void set(LivingEntity entity, InteractionHand hand, String name) {
		if (name != null && PortArmPoses.TRANSFORMERS.containsKey(name))
			POSES.computeIfAbsent(entity, e -> new String[2])[hand.ordinal()] = name;
	}

	public static void apply(HumanoidModel<?> model, LivingEntity entity) {
		String[] poses = POSES.get(entity);
		if (poses == null)
			return;
		for (InteractionHand hand : InteractionHand.values()) {
			String name = poses[hand.ordinal()];
			if (name != null) {
				HumanoidArm arm = hand == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite();
				PortArmPoses.TRANSFORMERS.get(name).applyTransform(model, entity, arm);
			}
		}
	}
}
