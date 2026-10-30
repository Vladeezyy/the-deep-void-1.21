package net.sombrius.thedeepvoid.fabric.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.EntityTickEvent;

/** NeoForge's EntityTickEvent.Pre on the server (the client: client.ClientLevelMixin) */
@Mixin(ServerLevel.class)
abstract class EntityTickMixin {
	@Inject(method = "tickNonPassenger", at = @At("HEAD"))
	private void deepvoid$entityTick(Entity entity, CallbackInfo ci) {
		ModBus.post(new EntityTickEvent.Pre(entity));
	}
}
