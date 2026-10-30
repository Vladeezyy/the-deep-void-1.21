package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/** A player tries to sleep in a bed (server); {@link #setProblem} with a problem stops it */
public class CanPlayerSleepEvent extends PlayerEvent {
	private final BlockPos pos;
	@Nullable
	private final Player.BedSleepingProblem vanillaProblem;
	@Nullable
	private Player.BedSleepingProblem problem;

	public CanPlayerSleepEvent(Player player, BlockPos pos, @Nullable Player.BedSleepingProblem problem) {
		super(player);
		this.pos = pos;
		this.vanillaProblem = problem;
		this.problem = problem;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	@Nullable
	public Player.BedSleepingProblem getVanillaProblem() {
		return this.vanillaProblem;
	}

	@Nullable
	public Player.BedSleepingProblem getProblem() {
		return this.problem;
	}

	public void setProblem(@Nullable Player.BedSleepingProblem problem) {
		this.problem = problem;
	}
}
