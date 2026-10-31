package net.sombrius.thedeepvoid.fabric.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;

import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;

/** NeoForge's applyForgeHandTransform: the mod's guns place themselves in first person */
// 26.3: the first person hands are FirstPersonHandsAndItemsRenderer's
@Mixin(FirstPersonHandsAndItemsRenderer.class)
abstract class ItemInHandRendererMixin {
	@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
	private void deepvoid$handTransform(FirstPersonHandsAndItemsRenderer self, PoseStack poseStack, HumanoidArm arm, float equippedProgress, Operation<Void> original, PlayerRenderState playerState,
			FirstPersonHandsAndItemsRenderState state, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float inverseArmHeight, PoseStack pose,
			net.minecraft.client.renderer.SubmitNodeCollector collector, int combinedLight) {
		// first person: the local player
		if (stack.getItem() instanceof HasClientExtensions.Item item && Minecraft.getInstance().player != null
				&& item.clientExtensions().applyForgeHandTransform(poseStack, Minecraft.getInstance().player, arm, stack, partialTicks, equippedProgress, swingProgress))
			return;
		original.call(self, poseStack, arm, equippedProgress);
	}
}
