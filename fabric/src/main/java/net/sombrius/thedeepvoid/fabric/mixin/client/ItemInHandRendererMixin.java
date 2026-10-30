package net.sombrius.thedeepvoid.fabric.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;

import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;

/** NeoForge's applyForgeHandTransform: the mod's guns place themselves in first person */
@Mixin(ItemInHandRenderer.class)
abstract class ItemInHandRendererMixin {
	@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
	private void deepvoid$handTransform(ItemInHandRenderer self, PoseStack poseStack, HumanoidArm arm, float equippedProgress, Operation<Void> original, AbstractClientPlayer player, float partialTicks,
			float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProg, PoseStack pose, net.minecraft.client.renderer.SubmitNodeCollector collector, int combinedLight) {
		if (stack.getItem() instanceof HasClientExtensions.Item item && player instanceof LocalPlayer local
				&& item.clientExtensions().applyForgeHandTransform(poseStack, local, arm, stack, partialTicks, equippedProgress, swingProgress))
			return;
		original.call(self, poseStack, arm, equippedProgress);
	}
}
