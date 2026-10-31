package net.sombrius.thedeepvoid.mixins;

import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.world.level.MoonPhase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 1.21.11: the sky of a dimension is its dimension type (skybox and environment attributes). The Deep Void and The
 * Pit drew only the sky disc and the dark disc below the horizon (CustomSkyDeepVoidProcedure of 1.99.3.1): the
 * overworld skybox without sun, moon and stars (26.3: drawn in the sky's render pass).
 */
@Mixin(SkyRenderer.class)
public class SkyRendererMixin {
	@Inject(method = "renderSunMoonAndStars", at = @At("HEAD"), cancellable = true)
	private void the_deep_void$noCelestials(RenderPass renderPass, PoseStack poseStack, float sunAngle, float moonAngle, float starAngle, MoonPhase moonPhase, float rainBrightness, float starBrightness, CallbackInfo ci) {
		var level = Minecraft.getInstance().level;
		if (level != null && level.dimension().identifier().getNamespace().equals("the_deep_void"))
			ci.cancel();
	}
}
