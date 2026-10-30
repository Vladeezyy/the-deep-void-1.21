package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class LivingEntityUseItemEvent extends LivingEvent {
	private final ItemStack item;

	protected LivingEntityUseItemEvent(LivingEntity entity, ItemStack item) {
		super(entity);
		this.item = item;
	}

	public ItemStack getItem() {
		return this.item;
	}

	/** The entity finished using the item (eating, drinking); {@link #getItem()} is a copy of the stack before */
	public static class Finish extends LivingEntityUseItemEvent {
		public Finish(LivingEntity entity, ItemStack item) {
			super(entity, item);
		}
	}

	/** An entity starts using an item (server and client); canceling it stops the use */
	public static class Start extends LivingEntityUseItemEvent implements ICancellableEvent {
		private int duration;

		public Start(LivingEntity entity, ItemStack item, int duration) {
			super(entity, item);
			this.duration = duration;
		}

		public int getDuration() {
			return this.duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}
	}

	/** An entity stops using an item before it finished (server and client) */
	public static class Stop extends LivingEntityUseItemEvent implements ICancellableEvent {
		private final int duration;

		public Stop(LivingEntity entity, ItemStack item, int duration) {
			super(entity, item);
			this.duration = duration;
		}

		public int getDuration() {
			return this.duration;
		}
	}
}
