package net.sombrius.thedeepvoid.fabric.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.ViewportEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** NeoForge's ViewportEvent.RenderFog and ComputeFogColor, after vanilla has set its values */
@Mixin(FogRenderer.class)
abstract class FogRendererMixin {
	@Shadow
	private static float fogRed;
	@Shadow
	private static float fogGreen;
	@Shadow
	private static float fogBlue;

	@Inject(method = "setupFog", at = @At("TAIL"))
	private static void deepvoid$renderFog(Camera camera, FogRenderer.FogMode mode, float farPlaneDistance, boolean shouldCreateFog, float partialTick, CallbackInfo ci) {
		if (!ModBus.hasListeners(ViewportEvent.RenderFog.class))
			return;
		ViewportEvent.RenderFog event = new ViewportEvent.RenderFog(mode, camera, partialTick, RenderSystem.getShaderFogStart(), RenderSystem.getShaderFogEnd(), RenderSystem.getShaderFogShape());
		if (ModBus.post(event)) {
			RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
			RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
			RenderSystem.setShaderFogShape(event.getFogShape());
		}
	}

	@Inject(method = "setupColor", at = @At("TAIL"))
	private static void deepvoid$fogColor(Camera camera, float partialTick, ClientLevel level, int renderDistanceChunks, float bossColorModifier, CallbackInfo ci) {
		if (!ModBus.hasListeners(ViewportEvent.ComputeFogColor.class))
			return;
		ViewportEvent.ComputeFogColor event = new ViewportEvent.ComputeFogColor(camera, partialTick, fogRed, fogGreen, fogBlue);
		ModBus.post(event);
		if (event.getRed() != fogRed || event.getGreen() != fogGreen || event.getBlue() != fogBlue) {
			fogRed = event.getRed();
			fogGreen = event.getGreen();
			fogBlue = event.getBlue();
			RenderSystem.clearColor(fogRed, fogGreen, fogBlue, 0.0F);
		}
	}
}
