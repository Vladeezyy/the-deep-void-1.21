package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public abstract class MobEffectEvent extends LivingEvent {
	@Nullable
	private final MobEffectInstance effectInstance;

	protected MobEffectEvent(LivingEntity entity, @Nullable MobEffectInstance effectInstance) {
		super(entity);
		this.effectInstance = effectInstance;
	}

	@Nullable
	public MobEffectInstance getEffectInstance() {
		return this.effectInstance;
	}

	/** An effect is removed before it ran out (server and client); canceling it keeps the effect */
	public static class Remove extends MobEffectEvent implements ICancellableEvent {
		public Remove(LivingEntity entity, @Nullable MobEffectInstance effectInstance) {
			super(entity, effectInstance);
		}
	}

	/** An effect ran out (server) */
	public static class Expired extends MobEffectEvent {
		public Expired(LivingEntity entity, @Nullable MobEffectInstance effectInstance) {
			super(entity, effectInstance);
		}
	}
}
