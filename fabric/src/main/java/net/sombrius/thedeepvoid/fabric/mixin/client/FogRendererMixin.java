package net.sombrius.thedeepvoid.fabric.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.ViewportEvent;

import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** NeoForge's ViewportEvent.RenderFog and ComputeFogColor (1.21.11: the fog data of the frame, before the upload) */
@Mixin(FogRenderer.class)
abstract class FogRendererMixin {
	@Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getDevice()Lcom/mojang/blaze3d/systems/GpuDevice;"))
	private void deepvoid$renderFog(Camera camera, int renderDistance, DeltaTracker deltaTracker, float darken, ClientLevel level, CallbackInfoReturnable<Vector4f> cir, @Local FogType fogType, @Local FogData fogData) {
		if (ModBus.hasListeners(ViewportEvent.RenderFog.class))
			ModBus.post(new ViewportEvent.RenderFog(camera, deltaTracker.getGameTimeDeltaPartialTick(false), fogType, fogData));
	}

	@Inject(method = "computeFogColor", at = @At("RETURN"))
	private void deepvoid$fogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darken, CallbackInfoReturnable<Vector4f> cir) {
		if (!ModBus.hasListeners(ViewportEvent.ComputeFogColor.class))
			return;
		Vector4f color = cir.getReturnValue();
		ViewportEvent.ComputeFogColor event = new ViewportEvent.ComputeFogColor(camera, partialTick, color.x(), color.y(), color.z());
		ModBus.post(event);
		color.set(event.getRed(), event.getGreen(), event.getBlue(), color.w());
	}
}
