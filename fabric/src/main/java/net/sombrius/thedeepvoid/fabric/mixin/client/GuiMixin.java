package net.sombrius.thedeepvoid.fabric.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.DeltaTracker;

import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;
import net.sombrius.thedeepvoid.port.bus.RenderGuiEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;

/** (26.2: the HUD is Hud) NeoForge's RenderGuiEvent.Pre (before every HUD layer) and isVisibleInGui of the mod's effects */
@Mixin(Hud.class)
abstract class GuiMixin {
	@Inject(method = "extractRenderState", at = @At("HEAD"))
	private void deepvoid$renderPre(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		ModBus.post(new RenderGuiEvent.Pre(guiGraphics));
	}

	@ModifyExpressionValue(method = "extractEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;showIcon()Z"))
	private boolean deepvoid$visibleInGui(boolean showIcon, @Local MobEffectInstance instance) {
		return showIcon && !(instance.getEffect().value() instanceof HasClientExtensions.Effect effect && !effect.clientExtensions().isVisibleInGui(instance));
	}
}
