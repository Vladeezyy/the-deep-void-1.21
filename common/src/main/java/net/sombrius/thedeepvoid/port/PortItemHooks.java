package net.sombrius.thedeepvoid.port;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/** NeoForge item hooks the mod's items implement; NeoForge calls them itself, the Fabric module through mixins */
public final class PortItemHooks {
	private PortItemHooks() {
	}

	/** NeoForge's {@code onEntitySwing}: true when the item handled the swing */
	public interface EntitySwing {
		boolean onEntitySwing(ItemStack itemstack, LivingEntity entity);
	}

	/**
	 * 1.21.5+: ItemStack.inventoryTick calls the item on the server only; the client ticks its own player's items
	 * through this ({@link PortClientItemTick}), as both sides did on 1.21.1.
	 */
	public interface ClientInventoryTick {
		void clientInventoryTick(ItemStack itemstack, net.minecraft.world.level.Level world, net.minecraft.world.entity.Entity entity, @javax.annotation.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot);
	}
}
