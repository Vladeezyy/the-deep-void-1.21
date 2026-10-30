package net.sombrius.thedeepvoid.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.sombrius.thedeepvoid.port.PortEntities;

import net.minecraft.world.entity.Entity;

/** Keeps {@link PortEntities} in step with the entities the client level tracks. */
@Mixin(targets = "net.minecraft.client.multiplayer.ClientLevel$EntityCallbacks")
public abstract class ClientLevelEntityCallbacksMixin {
	@Inject(method = "onTrackingStart(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
	private void deepvoid$trackingStart(Entity entity, CallbackInfo ci) {
		PortEntities.onTrackingStart(entity);
	}

	@Inject(method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"))
	private void deepvoid$trackingEnd(Entity entity, CallbackInfo ci) {
		PortEntities.onTrackingEnd(entity);
	}
}
