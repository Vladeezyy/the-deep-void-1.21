package net.sombrius.thedeepvoid.fabric.mixin.client;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Camera;

import net.sombrius.thedeepvoid.procedures.RenderSkyDeepVoidProcedure;

import javax.annotation.Nullable;

/** NeoForge's DimensionSpecialEffects sky, cloud and weather hooks, for the mod's own dimension effects */
@Mixin(LevelRenderer.class)
abstract class LevelRendererMixin {
	@Shadow
	@Nullable
	private ClientLevel level;
	@Shadow
	private int ticks;

	@Nullable
	private RenderSkyDeepVoidProcedure.TheDeepVoidModDimensionSpecialEffects deepvoid$effects() {
		return this.level != null && this.level.effects() instanceof RenderSkyDeepVoidProcedure.TheDeepVoidModDimensionSpecialEffects effects ? effects : null;
	}

	@Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
	private void deepvoid$sky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
		var effects = deepvoid$effects();
		if (effects != null && effects.renderSky(this.level, this.ticks, partialTick, frustumMatrix, camera, projectionMatrix, isFoggy, skyFogSetup))
			ci.cancel();
	}

	@Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
	private void deepvoid$clouds(PoseStack poseStack, Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
		var effects = deepvoid$effects();
		if (effects != null && effects.renderClouds(this.level, this.ticks, partialTick, poseStack, camX, camY, camZ, frustumMatrix, projectionMatrix))
			ci.cancel();
	}

	@Inject(method = "renderSnowAndRain", at = @At("HEAD"), cancellable = true)
	private void deepvoid$weather(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
		var effects = deepvoid$effects();
		if (effects != null && effects.renderSnowAndRain(this.level, this.ticks, partialTick, lightTexture, camX, camY, camZ))
			ci.cancel();
	}

	@Inject(method = "tickRain", at = @At("HEAD"), cancellable = true)
	private void deepvoid$tickRain(Camera camera, CallbackInfo ci) {
		var effects = deepvoid$effects();
		if (effects != null && effects.tickRain(this.level, this.ticks, camera))
			ci.cancel();
	}
}
