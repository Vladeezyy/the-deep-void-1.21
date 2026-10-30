package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.EntityType;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.EntityTickEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;

@EventBusSubscriber
public class StalkerBreaksBlocksProcedure {
	private static final TagKey<Block> BLOCK_TAG_BREAKABLE = net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:breakable"));
	private static final TagKey<EntityType<?>> ENTITY_TAG_CAN_BREAK_BLOCKS = TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:can_break_blocks"));

	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (DeepVoidConfigConfiguration.STALKERBREAKSBLOCKS.get() == true) {
			if (entity.getType().is(ENTITY_TAG_CAN_BREAK_BLOCKS)) {
				if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
					if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:break_block", 0) >= 10) {
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:break_block", 0);
						if ((world.getBlockState(BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ()))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ());
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ()), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ()))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ());
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ()), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						if ((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 1))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 1);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 1), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() + 1, entity.getZ() + 1))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX(), entity.getY() + 1, entity.getZ() + 1);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX(), entity.getY() + 1, entity.getZ() + 1), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						if ((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 1))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 1);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 1), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() + 1, entity.getZ() - 1))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX(), entity.getY() + 1, entity.getZ() - 1);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX(), entity.getY() + 1, entity.getZ() - 1), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						if ((world.getBlockState(BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ()))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ());
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ()), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ()))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ());
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ()), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						if ((world.getBlockState(BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ() - 1))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ() - 1);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ() - 1), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ() - 1))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ() - 1);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ() - 1), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						if ((world.getBlockState(BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ() + 1))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ() + 1);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ() + 1), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ() + 1))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ() + 1);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ() + 1), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						if ((world.getBlockState(BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ() + 1))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ() + 1);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ() + 1), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ() + 1))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ() + 1);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() - 1, entity.getY() + 1, entity.getZ() + 1), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
						if ((world.getBlockState(BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ() - 1))).is(BLOCK_TAG_BREAKABLE)) {
							{
								BlockPos _pos = BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ() - 1);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ() - 1), null);
								world.destroyBlock(_pos, false);
							}
							if ((world.getBlockState(BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ() - 1))).is(BLOCK_TAG_BREAKABLE)) {
								{
									BlockPos _pos = BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ() - 1);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(entity.getX() + 1, entity.getY() + 1, entity.getZ() - 1), null);
									world.destroyBlock(_pos, false);
								}
							}
						}
					} else {
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:break_block", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:break_block", 0) + 1));
					}
				}
			}
		}
	}
}
