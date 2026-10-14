package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

@EventBusSubscriber
public class SpongeIsPlacedProcedure {
	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
	}

	public static void execute(LevelAccessor world, double x, double y, double z) {
		execute(null, world, x, y, z);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Blocks.SPONGE) {
			if ((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == TheDeepVoidModBlocks.LIQUID_VOID.get() || (world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == TheDeepVoidModBlocks.LIQUID_VOID.get()
					|| (world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == TheDeepVoidModBlocks.LIQUID_VOID.get() || (world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == TheDeepVoidModBlocks.LIQUID_VOID.get()) {
				TheDeepVoidMod.queueServerWork(1, () -> {
					{
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockState _bs = TheDeepVoidModBlocks.VOID_FILLED_SPONGE.get().defaultBlockState();
						BlockState _bso = world.getBlockState(_bp);
						for (Property<?> _propertyOld : _bso.getProperties()) {
							Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
							if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
								try {
									_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
								} catch (Exception e) {
								}
						}
						world.setBlock(_bp, _bs, 3);
					}
				});
			}
		}
	}
}
