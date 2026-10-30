package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class PlayerInteractEvent extends PlayerEvent {
	private final InteractionHand hand;
	private final BlockPos pos;

	protected PlayerInteractEvent(Player player, InteractionHand hand, BlockPos pos) {
		super(player);
		this.hand = hand;
		this.pos = pos;
	}

	public InteractionHand getHand() {
		return this.hand;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public Level getLevel() {
		return this.getEntity().level();
	}

	/** A player right-clicks a block (client and server); listeners here only observe it */
	public static class RightClickBlock extends PlayerInteractEvent implements ICancellableEvent {
		private final Direction face;

		public RightClickBlock(Player player, InteractionHand hand, BlockPos pos, Direction face) {
			super(player, hand, pos);
			this.face = face;
		}

		public Direction getFace() {
			return this.face;
		}
	}

	/** A player right-clicks an entity (client and server); canceling it stops the interaction */
	public static class EntityInteract extends PlayerInteractEvent implements ICancellableEvent {
		private final Entity target;

		public EntityInteract(Player player, InteractionHand hand, Entity target) {
			super(player, hand, target.blockPosition());
			this.target = target;
		}

		public Entity getTarget() {
			return this.target;
		}
	}

	/** A player starts breaking a block (client and server); canceling it stops it */
	public static class LeftClickBlock extends PlayerInteractEvent implements ICancellableEvent {
		private final Direction face;

		public LeftClickBlock(Player player, BlockPos pos, Direction face) {
			super(player, InteractionHand.MAIN_HAND, pos);
			this.face = face;
		}

		public Direction getFace() {
			return this.face;
		}
	}
}
