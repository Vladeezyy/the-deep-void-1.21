package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.PlayerInteractEvent;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import java.util.Map;

@EventBusSubscriber
public class PlayerStripsRottenLogProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.ROTTEN_LOG.get() && ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES)
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES))) {
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = TheDeepVoidModBlocks.STRIPPED_ROTTEN_LOG.get().defaultBlockState();
				BlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
					Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
					if (_property != null && _bs.getValue(_property) != null)
						try {
							_bs = _bs.setValue(_property, (Comparable) entry.getValue());
						} catch (Exception e) {
						}
				}
				world.setBlock(_bp, _bs, 3);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ui.stonecutter.take_result")), SoundSource.BLOCKS, (float) 0.4,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ui.stonecutter.take_result")), SoundSource.BLOCKS, (float) 0.4, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1), false);
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES)) {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.OFF_HAND, true);
			}
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.ROTTEN_WOOD.get() && ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES)
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES))) {
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = TheDeepVoidModBlocks.STRIPPED_ROTTEN_WOOD.get().defaultBlockState();
				BlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
					Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
					if (_property != null && _bs.getValue(_property) != null)
						try {
							_bs = _bs.setValue(_property, (Comparable) entry.getValue());
						} catch (Exception e) {
						}
				}
				world.setBlock(_bp, _bs, 3);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ui.stonecutter.take_result")), SoundSource.BLOCKS, (float) 0.4,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ui.stonecutter.take_result")), SoundSource.BLOCKS, (float) 0.4, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1), false);
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES)) {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.OFF_HAND, true);
			}
		} else if (((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.BLOCK_OF_BONE_PILE.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.MOSSY_BONE_PILE.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.BLOCK_OF_SKULL_PILE.get())
				&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get()
						|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get())) {
			world.setBlock(BlockPos.containing(x, y, z), TheDeepVoidModBlocks.BLOCK_OF_OLD_SHATTERED_BONE.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:gravedigger_shovel_use")), SoundSource.BLOCKS, (float) 0.4,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:gravedigger_shovel_use")), SoundSource.BLOCKS, (float) 0.4, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1), false);
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get()) {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.OFF_HAND, true);
			}
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.BLOCK_OF_ROTTEN_BONE_PILE.get()
				&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get()
						|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get())) {
			world.setBlock(BlockPos.containing(x, y, z), TheDeepVoidModBlocks.BLOCK_OF_ROTTEN_SHATTERED_BONE.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:gravedigger_shovel_use")), SoundSource.BLOCKS, (float) 0.4,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:gravedigger_shovel_use")), SoundSource.BLOCKS, (float) 0.4, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1), false);
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get()) {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.OFF_HAND, true);
			}
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.BLOCK_OF_BONE.get()
				&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get()
						|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get())) {
			world.setBlock(BlockPos.containing(x, y, z), TheDeepVoidModBlocks.BLOCK_OF_SHATTERED_BONE.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:gravedigger_shovel_use")), SoundSource.BLOCKS, (float) 0.4,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:gravedigger_shovel_use")), SoundSource.BLOCKS, (float) 0.4, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1), false);
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRAVEDIGGER_SHOVEL.get()) {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.OFF_HAND, true);
			}
		}
	}
}
