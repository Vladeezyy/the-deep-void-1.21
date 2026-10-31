package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.neo.Capabilities;
import net.sombrius.thedeepvoid.port.neo.IItemHandlerModifiable;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class GluttonBlockHopperItemEnterProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, String command) {
		if (command == null)
			return;
		if (world instanceof net.minecraft.world.level.LevelAccessor _ext && net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
			ItemStack _setstack = ItemStack.EMPTY.copy();
			_setstack.setCount(1);
			_itemHandlerModifiable.setStackInSlot(0, _setstack);
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.fox.bite")), SoundSource.BLOCKS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.fox.bite")), SoundSource.BLOCKS, 1, 1, false);
			}
		}
		TheDeepVoidMod.queueServerWork(20, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.BLOCKS, (float) 0.5, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.BLOCKS, (float) 0.5, 1, false);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(40, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.BLOCKS, (float) 0.5, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.BLOCKS, (float) 0.5, 1, false);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(80, () -> {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.GLUTTON_BLOCK.get()) {
				if ((command).equals("bossLoot")) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 2, 6); index0++) {
						if (world instanceof ServerLevel _level)
							_level.addFreshEntity(new ExperienceOrb(_level, (x + 0.5), (y + 1), (z + 0.5), Mth.nextInt(RandomSource.create(), 4, 8)));
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.CRIT, (x + 0.5), (y + 1), (z + 0.5), 10, 1, 1, 1, 0.1);
				} else {
					if (new Object() {
						public int getContainerSize(LevelAccessor world, BlockPos pos) {
							BlockEntity _ent = world.getBlockEntity(pos);
							if (_ent != null) {
								if (_ent instanceof BaseContainerBlockEntity _block) {
									return _block.getContainerSize();
								}
							}
							return 0;
						}

						public int getAmount(LevelAccessor world, BlockPos pos) {
							Block block = world.getBlockState(pos).getBlock();
							if (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST) {
								boolean isSingle = block.getStateDefinition().getProperty("type") instanceof EnumProperty _getep5 && world.getBlockState(pos).getValue(_getep5).toString().equals("SINGLE");
								if (!isSingle) {
									return getContainerSize(world, pos) * 2;
								}
							}
							return getContainerSize(world, pos);
						}
					}.getAmount(world, new BlockPos((int) x, (int) (y + 1), (int) z)) > 0) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, new Vec3((x + 0.5), (y + 1), (z + 0.5)), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(), command);
						GluttonBlockChestSlotCheckProcedure.execute(world, x, y, z);
					} else {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, new Vec3((x + 0.5), (y + 1), (z + 0.5)), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(), command);
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.CRIT, (x + 0.5), (y + 1), (z + 0.5), 10, 1, 1, 1, 0.1);
					}
				}
			}
		});
	}
}
