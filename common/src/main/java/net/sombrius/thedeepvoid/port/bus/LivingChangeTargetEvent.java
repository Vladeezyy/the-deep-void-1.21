package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

/** A mob is about to change its attack target (server); canceling it keeps the old one */
public class LivingChangeTargetEvent extends LivingEvent implements ICancellableEvent {
	@Nullable
	private final LivingEntity originalTarget;
	@Nullable
	private LivingEntity newTarget;

	public LivingChangeTargetEvent(LivingEntity entity, @Nullable LivingEntity target) {
		super(entity);
		this.originalTarget = target;
		this.newTarget = target;
	}

	@Nullable
	public LivingEntity getOriginalAboutToBeSetTarget() {
		return this.originalTarget;
	}

	@Nullable
	public LivingEntity getNewAboutToBeSetTarget() {
		return this.newTarget;
	}

	public void setNewAboutToBeSetTarget(@Nullable LivingEntity target) {
		this.newTarget = target;
	}
}
