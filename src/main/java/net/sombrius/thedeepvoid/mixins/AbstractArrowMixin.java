package net.sombrius.thedeepvoid.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;

/** Brings back the 1.20 arrow knockback value (vanilla 1.20.1 AbstractArrow#onHitEntity). */
@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements PortUtil.ArrowKnockback {
	@Unique
	private int deepvoid$knockback;

	@Override
	public void deepvoid$setKnockback(int knockback) {
		this.deepvoid$knockback = knockback;
	}

	@Override
	public int deepvoid$getKnockback() {
		return this.deepvoid$knockback;
	}

	@Inject(method = "doKnockback", at = @At("TAIL"))
	private void deepvoid$applyKnockback(LivingEntity target, DamageSource source, CallbackInfo ci) {
		if (this.deepvoid$knockback > 0) {
			double resistance = Math.max(0.0D, 1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			Vec3 push = ((AbstractArrow) (Object) this).getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.deepvoid$knockback * 0.6D * resistance);
			if (push.lengthSqr() > 0.0D)
				target.push(push.x, 0.1D, push.z);
		}
	}
}
