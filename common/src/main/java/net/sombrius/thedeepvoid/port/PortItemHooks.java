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
}
