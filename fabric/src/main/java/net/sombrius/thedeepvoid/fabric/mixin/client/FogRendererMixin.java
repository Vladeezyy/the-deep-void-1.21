package net.sombrius.thedeepvoid.fabric.mixin.client;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** NeoForge's ViewportEvent.RenderFog and ComputeFogColor (26.1: setupFog returns the fog data of the frame) */
@Mixin(FogRenderer.class)
abstract class FogRendererMixin {
	@Inject(method = "setupFog", at = @At("RETURN"))
	private void deepvoid$renderFog(Camera camera, int renderDistance, DeltaTracker deltaTracker, float darken, ClientLevel level, CallbackInfoReturnable<FogData> cir) {
		if (!ModBus.hasListeners(ViewportEvent.RenderFog.class))
			return;
		FogType fogType = camera.getFluidInCamera() == FogType.NONE ? FogType.ATMOSPHERIC : camera.getFluidInCamera();
		ModBus.post(new ViewportEvent.RenderFog(camera, deltaTracker.getGameTimeDeltaPartialTick(false), fogType, cir.getReturnValue()));
	}

	@Inject(method = "computeFogColor", at = @At("RETURN"))
	private void deepvoid$fogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darken, Vector4f dest, CallbackInfo ci) {
		if (!ModBus.hasListeners(ViewportEvent.ComputeFogColor.class))
			return;
		ViewportEvent.ComputeFogColor event = new ViewportEvent.ComputeFogColor(camera, partialTick, dest.x(), dest.y(), dest.z());
		ModBus.post(event);
		dest.set(event.getRed(), event.getGreen(), event.getBlue(), dest.w());
	}
}
