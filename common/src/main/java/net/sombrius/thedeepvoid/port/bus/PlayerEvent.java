package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;

public abstract class PlayerEvent extends LivingEvent {
	protected PlayerEvent(Player player) {
		super(player);
	}

	@Override
	public Player getEntity() {
		return (Player) super.getEntity();
	}

	public static class PlayerLoggedInEvent extends PlayerEvent {
		public PlayerLoggedInEvent(Player player) {
			super(player);
		}
	}

	public static class PlayerRespawnEvent extends PlayerEvent {
		private final boolean endConquered;

		public PlayerRespawnEvent(Player player, boolean endConquered) {
			super(player);
			this.endConquered = endConquered;
		}

		public boolean isEndConquered() {
			return this.endConquered;
		}
	}

	public static class PlayerChangedDimensionEvent extends PlayerEvent {
		private final ResourceKey<Level> from;
		private final ResourceKey<Level> to;

		public PlayerChangedDimensionEvent(Player player, ResourceKey<Level> from, ResourceKey<Level> to) {
			super(player);
			this.from = from;
			this.to = to;
		}

		public ResourceKey<Level> getFrom() {
			return this.from;
		}

		public ResourceKey<Level> getTo() {
			return this.to;
		}
	}

	/** The new player object after death or the return from the End; {@link #getOriginal()} is the old one */
	public static class Clone extends PlayerEvent {
		private final Player original;
		private final boolean wasDeath;

		public Clone(Player player, Player original, boolean wasDeath) {
			super(player);
			this.original = original;
			this.wasDeath = wasDeath;
		}

		public Player getOriginal() {
			return this.original;
		}

		public boolean isWasDeath() {
			return this.wasDeath;
		}
	}

	public static class ItemCraftedEvent extends PlayerEvent {
		private final ItemStack crafting;

		public ItemCraftedEvent(Player player, ItemStack crafting) {
			super(player);
			this.crafting = crafting;
		}

		public ItemStack getCrafting() {
			return this.crafting;
		}
	}

	public static class ItemSmeltedEvent extends PlayerEvent {
		private final ItemStack smelting;

		public ItemSmeltedEvent(Player player, ItemStack smelting) {
			super(player);
			this.smelting = smelting;
		}

		public ItemStack getSmelting() {
			return this.smelting;
		}
	}
}
