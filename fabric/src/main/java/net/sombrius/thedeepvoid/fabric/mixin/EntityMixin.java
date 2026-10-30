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

/** NeoForge's EntityTravelToDimensionEvent (entities; players in ServerPlayerMixin) */
@Mixin(Entity.class)
abstract class EntityMixin {
	@Inject(method = "teleport(Lnet/minecraft/world/level/portal/TeleportTransition;)Lnet/minecraft/world/entity/Entity;", at = @At("HEAD"), cancellable = true)
	private void deepvoid$travel(TeleportTransition transition, CallbackInfoReturnable<Entity> cir) {
		Entity self = (Entity) (Object) this;
		if (!(self instanceof ServerPlayer) && transition.newLevel().dimension() != self.level().dimension() && ModBus.post(new EntityTravelToDimensionEvent(self, transition.newLevel().dimension())))
			cir.setReturnValue(null);
	}
}
