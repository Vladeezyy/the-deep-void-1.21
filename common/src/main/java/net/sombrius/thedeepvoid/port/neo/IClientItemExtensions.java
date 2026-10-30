package net.sombrius.thedeepvoid.port.neo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * NeoForge's client item extensions, the methods the mod overrides (client only; NeoForge 21.11 signatures). NeoForge
 * registers them through its own interface; Fabric uses its armor renderer registry and mixins for the arm poses. The
 * GeckoLib items have their renderers from GeckoLib's render providers.
 */
public interface IClientItemExtensions {
	@Nullable
	default HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
		return null;
	}

	default boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
		return false;
	}

	/** The armor model of a humanoid layer (the render state poses it) */
	default Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
		return original;
	}

	default Identifier getArmorTexture(ItemStack stack, EquipmentClientInfo.LayerType type, EquipmentClientInfo.Layer layer, Identifier _default) {
		return _default;
	}
}
