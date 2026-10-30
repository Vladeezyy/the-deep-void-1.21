package net.sombrius.thedeepvoid.port;

import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.client.model.HumanoidModel;

/** 1.21 NeoForge extends enums from META-INF/enumextensions.json; HumanoidModel.ArmPose.create(...) is gone. */
public final class ArmPoses {
	public static final EnumProxy<HumanoidModel.ArmPose> APOSTASY = new EnumProxy<>(HumanoidModel.ArmPose.class, false, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		}
	});

	public static final EnumProxy<HumanoidModel.ArmPose> DOUBLE_BARREL_SHOTGUN = new EnumProxy<>(HumanoidModel.ArmPose.class, false, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		}
	});

	public static final EnumProxy<HumanoidModel.ArmPose> ELDRITCH_MAW = new EnumProxy<>(HumanoidModel.ArmPose.class, false, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		}
	});

	public static final EnumProxy<HumanoidModel.ArmPose> HERESY = new EnumProxy<>(HumanoidModel.ArmPose.class, false, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		}
	});

	public static final EnumProxy<HumanoidModel.ArmPose> MISANTHROPY = new EnumProxy<>(HumanoidModel.ArmPose.class, false, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
			model.leftArm.xRot = -45F + model.head.xRot;
			model.leftArm.yRot = 45F;
		}
	});

	public static final EnumProxy<HumanoidModel.ArmPose> PERIL = new EnumProxy<>(HumanoidModel.ArmPose.class, false, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.leftArm.xRot = -45F + model.head.xRot;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
		}
	});

	public static final EnumProxy<HumanoidModel.ArmPose> PERIL_OFF_HAND = new EnumProxy<>(HumanoidModel.ArmPose.class, false, false, (IArmPoseTransformer) (model, entity, arm) -> {
		if (arm == HumanoidArm.LEFT) {
			model.leftArm.xRot = -45F + model.head.xRot;
		} else {
			model.rightArm.xRot = -45F + model.head.xRot;
		}
	});
}
