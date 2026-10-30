package net.sombrius.thedeepvoid.fabric.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.EntityTravelToDimensionEvent;

/** NeoForge's EntityTravelToDimensionEvent for players */
@Mixin(ServerPlayer.class)
abstract class ServerPlayerMixin {
	@Inject(method = "changeDimension", at = @At("HEAD"), cancellable = true)
	private void deepvoid$travel(DimensionTransition transition, CallbackInfoReturnable<Entity> cir) {
		if (ModBus.post(new EntityTravelToDimensionEvent((ServerPlayer) (Object) this, transition.newLevel().dimension())))
			cir.setReturnValue(null);
	}
}
