package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.sombrius.thedeepvoid.port.neo.Capabilities;
import net.sombrius.thedeepvoid.port.neo.IItemHandler;
import net.sombrius.thedeepvoid.port.neo.PortCaps;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

/** The mod's item handlers of block entities and items ({@link PortCaps}; the NeoForge module also registers them as capabilities) */
public class RegisterCapabilitiesEvent extends Event {
	@FunctionalInterface
	public interface BlockProvider<BE extends BlockEntity> {
		@Nullable
		IItemHandler getCapability(BE blockEntity, @Nullable Direction side);
	}

	@FunctionalInterface
	public interface ItemProvider {
		@Nullable
		IItemHandler getCapability(ItemStack stack, @Nullable Void context);
	}

	@SuppressWarnings("unchecked")
	public <BE extends BlockEntity> void registerBlockEntity(Capabilities.Token token, BlockEntityType<BE> type, BlockProvider<? super BE> provider) {
		PortCaps.blockProviders().put(type, (be, side) -> ((BlockProvider<BE>) provider).getCapability((BE) be, side));
	}

	public void registerItem(Capabilities.Token token, ItemProvider provider, ItemLike... items) {
		for (ItemLike item : items)
			PortCaps.itemProviders().put(item.asItem(), stack -> provider.getCapability(stack, null));
	}
}
