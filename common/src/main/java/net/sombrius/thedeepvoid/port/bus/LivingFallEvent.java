package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.entity.LivingEntity;

/** An entity lands (server and client); canceling it cancels the fall damage */
public class LivingFallEvent extends LivingEvent implements ICancellableEvent {
	private float distance;
	private float damageMultiplier;

	public LivingFallEvent(LivingEntity entity, float distance, float damageMultiplier) {
		super(entity);
		this.distance = distance;
		this.damageMultiplier = damageMultiplier;
	}

	public float getDistance() {
		return this.distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDamageMultiplier() {
		return this.damageMultiplier;
	}

	public void setDamageMultiplier(float damageMultiplier) {
		this.damageMultiplier = damageMultiplier;
	}
}
