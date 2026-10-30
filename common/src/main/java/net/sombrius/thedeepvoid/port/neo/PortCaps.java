package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The item handlers of blocks, entities and stacks (NeoForge's {@code getCapability(Capabilities.ItemHandler.*)}):
 * what the mod registered for its own block entities and items, else a wrapper of the vanilla container. The NeoForge
 * module also registers the mod's providers as real capabilities, so other mods see them.
 */
public final class PortCaps {
	private static final Map<BlockEntityType<?>, BiFunction<BlockEntity, Direction, IItemHandler>> BLOCKS = new IdentityHashMap<>();
	private static final Map<Item, Function<ItemStack, IItemHandler>> ITEMS = new IdentityHashMap<>();

	private PortCaps() {
	}

	public static Map<BlockEntityType<?>, BiFunction<BlockEntity, Direction, IItemHandler>> blockProviders() {
		return BLOCKS;
	}

	public static Map<Item, Function<ItemStack, IItemHandler>> itemProviders() {
		return ITEMS;
	}

	@Nullable
	public static IItemHandler block(LevelAccessor level, BlockPos pos, @Nullable Direction side) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be == null)
			return null;
		BiFunction<BlockEntity, Direction, IItemHandler> provider = BLOCKS.get(be.getType());
		if (provider != null)
			return provider.apply(be, side);
		if (be instanceof WorldlyContainer worldly && side != null)
			return new SidedInvWrapper(worldly, side);
		if (be instanceof Container container)
			return new InvWrapper(container);
		return null;
	}

	@Nullable
	public static IItemHandler block(Level level, BlockPos pos, @Nullable Direction side) {
		return block((LevelAccessor) level, pos, side);
	}

	/** NeoForge's entity item handler: a player's inventory (main, armor, offhand), or the entity's container */
	@Nullable
	public static IItemHandler entity(Entity entity) {
		if (entity instanceof Player player)
			return new InvWrapper(player.getInventory());
		if (entity instanceof Container container)
			return new InvWrapper(container);
		return null;
	}

	@Nullable
	public static IItemHandler entity(Entity entity, @Nullable Direction side) {
		return entity(entity);
	}

	@Nullable
	public static IItemHandler item(ItemStack stack) {
		Function<ItemStack, IItemHandler> provider = ITEMS.get(stack.getItem());
		return provider == null ? null : provider.apply(stack);
	}
}
