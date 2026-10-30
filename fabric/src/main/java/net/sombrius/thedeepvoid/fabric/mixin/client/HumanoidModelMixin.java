package net.sombrius.thedeepvoid.fabric.mixin.client;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.model.HumanoidModel;

import net.sombrius.thedeepvoid.fabric.FabricArmPoses;

/** The mod's gun arm poses on Fabric: the transformer after the model's own animation */
@Mixin(HumanoidModel.class)
abstract class HumanoidModelMixin {
	@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("TAIL"))
	private void deepvoid$customPose(HumanoidRenderState state, CallbackInfo ci) {
		FabricArmPoses.apply((HumanoidModel<?>) (Object) this, state);
	}
}
