package net.sombrius.thedeepvoid.fabric.mixin.client;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.HumanoidModel;

import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;
import net.sombrius.thedeepvoid.fabric.FabricArmPoses;

/** NeoForge's IClientItemExtensions#getArmPose for the mod's guns (their transformer runs in HumanoidModelMixin) */
@Mixin(PlayerRenderer.class)
abstract class PlayerRendererMixin {
	@Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
	private static void deepvoid$armPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
		ItemStack stack = player.getItemInHand(hand);
		FabricArmPoses.clear(player, hand);
		if (stack.getItem() instanceof HasClientExtensions.Item item) {
			FabricArmPoses.lastPose = null;
			HumanoidModel.ArmPose pose = item.clientExtensions().getArmPose(player, hand, stack);
			if (pose != null) {
				FabricArmPoses.set(player, hand, FabricArmPoses.lastPose);
				cir.setReturnValue(pose);
			}
		}
	}
}
