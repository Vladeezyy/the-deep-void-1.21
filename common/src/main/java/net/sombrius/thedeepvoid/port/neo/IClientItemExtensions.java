package net.sombrius.thedeepvoid.port.neo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * NeoForge's client item extensions, the methods the mod overrides (client only). NeoForge registers them through its
 * own interface; Fabric uses its item renderer and armor renderer registries and mixins for the arm poses.
 */
public interface IClientItemExtensions {
	/** The item's own renderer (only called for items that override it) */
	@Nullable
	default BlockEntityWithoutLevelRenderer getCustomRenderer() {
		return null;
	}

	@Nullable
	default HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
		return null;
	}

	default boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
		return false;
	}

	default HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
		return original;
	}
}
