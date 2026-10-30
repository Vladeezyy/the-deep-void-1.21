package net.sombrius.thedeepvoid.fabric.mixin.client;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

import net.sombrius.thedeepvoid.fabric.FabricArmPoses;
import net.sombrius.thedeepvoid.port.PortArmPoses;
import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;

/** NeoForge's IClientItemExtensions#getArmPose for the mod's guns: the pose and its name go to the render state */
@Mixin(AvatarRenderer.class)
abstract class AvatarRendererMixin {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
	private void deepvoid$armPoses(Avatar avatar, AvatarRenderState state, float partialTick, CallbackInfo ci) {
		String[] names = ((FabricArmPoses.PoseState) state).deepvoid$poses();
		for (HumanoidArm arm : HumanoidArm.values()) {
			names[arm.ordinal()] = null;
			InteractionHand hand = arm == avatar.getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
			ItemStack stack = avatar.getItemInHand(hand);
			if (!(stack.getItem() instanceof HasClientExtensions.Item item))
				continue;
			HumanoidModel.ArmPose pose = item.clientExtensions().getArmPose(avatar, hand, stack);
			if (pose == null)
				continue;
			if (arm == HumanoidArm.RIGHT)
				state.rightArmPose = pose;
			else
				state.leftArmPose = pose;
			// MCreator keeps the pose in a static field (PortArmPoses.pose runs once): the pose is named after the item
			String name = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().toUpperCase(java.util.Locale.ROOT);
			if (pose == HumanoidModel.ArmPose.ITEM && PortArmPoses.TRANSFORMERS.containsKey(name))
				names[arm.ordinal()] = name;
		}
	}
}
