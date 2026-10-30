package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/** A shield blocks damage (server and client); {@link #setBlocked(boolean) setBlocked(false)} lets it through */
public class LivingShieldBlockEvent extends LivingEvent implements ICancellableEvent {
	private final DamageSource source;
	private final float originalBlockedDamage;
	private float blockedDamage;
	private boolean shieldTakesDamage = true;
	private boolean blocked;

	public LivingShieldBlockEvent(LivingEntity entity, DamageSource source, float blockedDamage, boolean blocked) {
		super(entity);
		this.source = source;
		this.originalBlockedDamage = blockedDamage;
		this.blockedDamage = blockedDamage;
		this.blocked = blocked;
	}

	public DamageSource getDamageSource() {
		return this.source;
	}

	public float getOriginalBlockedDamage() {
		return this.originalBlockedDamage;
	}

	public float getBlockedDamage() {
		return this.blockedDamage;
	}

	public void setBlockedDamage(float blockedDamage) {
		this.blockedDamage = blockedDamage;
	}

	public boolean shieldTakesDamage() {
		return this.shieldTakesDamage;
	}

	public void setShieldTakesDamage(boolean shieldTakesDamage) {
		this.shieldTakesDamage = shieldTakesDamage;
	}

	public boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}
