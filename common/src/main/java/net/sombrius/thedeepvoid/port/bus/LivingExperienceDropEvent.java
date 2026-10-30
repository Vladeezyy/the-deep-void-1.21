package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/** A dying entity is about to drop experience (server) */
public class LivingExperienceDropEvent extends LivingEvent implements ICancellableEvent {
	@Nullable
	private final Player attackingPlayer;
	private final int originalExperience;
	private int droppedExperience;

	public LivingExperienceDropEvent(LivingEntity entity, @Nullable Player attackingPlayer, int experience) {
		super(entity);
		this.attackingPlayer = attackingPlayer;
		this.originalExperience = experience;
		this.droppedExperience = experience;
	}

	@Nullable
	public Player getAttackingPlayer() {
		return this.attackingPlayer;
	}

	public int getOriginalExperience() {
		return this.originalExperience;
	}

	public int getDroppedExperience() {
		return this.droppedExperience;
	}

	public void setDroppedExperience(int experience) {
		this.droppedExperience = experience;
	}
}
