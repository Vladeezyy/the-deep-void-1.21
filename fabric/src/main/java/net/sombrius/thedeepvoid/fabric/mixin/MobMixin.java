package net.sombrius.thedeepvoid.fabric.mixin;

import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.llamalad7.mixinextras.sugar.Local;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.LivingChangeTargetEvent;

/** NeoForge's LivingChangeTargetEvent (mob targets) */
@Mixin(Mob.class)
abstract class MobMixin {
	@Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
	private void deepvoid$changeTarget(LivingEntity target, CallbackInfo ci, @Local(argsOnly = true) LocalRef<LivingEntity> newTarget) {
		LivingChangeTargetEvent event = new LivingChangeTargetEvent((Mob) (Object) this, target);
		if (ModBus.post(event)) {
			ci.cancel();
			return;
		}
		newTarget.set(event.getNewAboutToBeSetTarget());
	}
}
