package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.entity.LivingEntity;

/** An entity is about to heal (server and client); canceling it stops the healing */
public class LivingHealEvent extends LivingEvent implements ICancellableEvent {
	private float amount;

	public LivingHealEvent(LivingEntity entity, float amount) {
		super(entity);
		this.amount = amount;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
}
