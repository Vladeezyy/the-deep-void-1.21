package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.entity.player.Player;

public abstract class PlayerXpEvent extends PlayerEvent {
	protected PlayerXpEvent(Player player) {
		super(player);
	}

	/** A player's experience points change (server); canceling it stops the change */
	public static class XpChange extends PlayerXpEvent implements ICancellableEvent {
		private int amount;

		public XpChange(Player player, int amount) {
			super(player);
			this.amount = amount;
		}

		public int getAmount() {
			return this.amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}
	}
}
