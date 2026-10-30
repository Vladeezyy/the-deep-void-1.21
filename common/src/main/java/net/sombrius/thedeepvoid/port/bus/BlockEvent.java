package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class BlockEvent extends Event {
	private final LevelAccessor level;
	private final BlockPos pos;
	private final BlockState state;

	protected BlockEvent(LevelAccessor level, BlockPos pos, BlockState state) {
		this.level = level;
		this.pos = pos;
		this.state = state;
	}

	public LevelAccessor getLevel() {
		return this.level;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public BlockState getState() {
		return this.state;
	}

	/** A player is about to break a block (server); canceling it keeps the block */
	public static class BreakEvent extends BlockEvent implements ICancellableEvent {
		private final Player player;

		public BreakEvent(LevelAccessor level, BlockPos pos, BlockState state, Player player) {
			super(level, pos, state);
			this.player = player;
		}

		public Player getPlayer() {
			return this.player;
		}
	}

	/** A block was placed by an entity (server); canceling it undoes the placement */
	public static class EntityPlaceEvent extends BlockEvent implements ICancellableEvent {
		@Nullable
		private final Entity entity;
		private final BlockState placedAgainst;

		public EntityPlaceEvent(LevelAccessor level, BlockPos pos, BlockState placed, BlockState placedAgainst, @Nullable Entity entity) {
			super(level, pos, placed);
			this.entity = entity;
			this.placedAgainst = placedAgainst;
		}

		@Nullable
		public Entity getEntity() {
			return this.entity;
		}

		public BlockState getPlacedBlock() {
			return this.getState();
		}

		public BlockState getPlacedAgainst() {
			return this.placedAgainst;
		}
	}
}
