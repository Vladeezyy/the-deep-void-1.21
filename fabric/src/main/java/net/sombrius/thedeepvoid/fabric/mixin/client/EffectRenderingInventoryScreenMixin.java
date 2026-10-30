package net.sombrius.thedeepvoid.fabric.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;

import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;

import java.util.Collection;

/** isVisibleInInventory (1.21.6+: EffectsInInventory draws the effects of container screens) of the mod's effects: hidden ones are left out of the inventory's effect list */
@Mixin(EffectsInInventory.class)
abstract class EffectRenderingInventoryScreenMixin {
	@ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getActiveEffects()Ljava/util/Collection;"))
	private Collection<MobEffectInstance> deepvoid$visibleInInventory(Collection<MobEffectInstance> effects) {
		if (effects.stream().noneMatch(e -> e.getEffect().value() instanceof HasClientExtensions.Effect))
			return effects;
		return effects.stream().filter(e -> !(e.getEffect().value() instanceof HasClientExtensions.Effect effect) || effect.clientExtensions().isVisibleInInventory(e)).toList();
	}
}
