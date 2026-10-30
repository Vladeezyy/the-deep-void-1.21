package net.sombrius.thedeepvoid.fabric.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.EntityTravelToDimensionEvent;

/** NeoForge's EntityTravelToDimensionEvent for players */
@Mixin(ServerPlayer.class)
abstract class ServerPlayerMixin {
	@Inject(method = "teleport(Lnet/minecraft/world/level/portal/TeleportTransition;)Lnet/minecraft/server/level/ServerPlayer;", at = @At("HEAD"), cancellable = true)
	private void deepvoid$travel(TeleportTransition transition, CallbackInfoReturnable<ServerPlayer> cir) {
		ServerPlayer self = (ServerPlayer) (Object) this;
		// 1.21.2: teleport() also moves within a dimension; the event is for dimension changes
		if (transition.newLevel().dimension() != self.level().dimension() && ModBus.post(new EntityTravelToDimensionEvent(self, transition.newLevel().dimension())))
			cir.setReturnValue(null);
	}
}
