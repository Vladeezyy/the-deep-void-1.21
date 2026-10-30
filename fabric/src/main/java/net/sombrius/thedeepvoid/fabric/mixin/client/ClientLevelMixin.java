package net.sombrius.thedeepvoid.fabric.mixin.client;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.client.multiplayer.ClientLevel;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.EntityTickEvent;

/** NeoForge's EntityTickEvent.Pre on the client */
@Mixin(ClientLevel.class)
abstract class ClientLevelMixin {
	@Inject(method = "tickNonPassenger", at = @At("HEAD"))
	private void deepvoid$entityTick(Entity entity, CallbackInfo ci) {
		ModBus.post(new EntityTickEvent.Pre(entity));
	}
}
