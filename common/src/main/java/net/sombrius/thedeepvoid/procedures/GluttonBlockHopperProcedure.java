package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.neo.Capabilities;
import net.sombrius.thedeepvoid.port.neo.IItemHandler;
import net.sombrius.thedeepvoid.port.neo.IItemHandlerModifiable;

public class GluttonBlockHopperProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (new Object() {
			public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
				if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
					IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
					if (_itemHandler != null)
						return _itemHandler.getStackInSlot(slotid).getCount();
				}
				return 0;
			}
		}.getAmount(world, BlockPos.containing(x, y, z), 0) > 0) {
			if ((new Object() {
				public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
						IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).copy();
					}
					return ItemStack.EMPTY;
				}
			}.getItemStack(world, BlockPos.containing(x, y, z), 0)).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:glutton_tier1")))) {
				GluttonBlockHopperItemEnterProcedure.execute(world, x, y, z, "/loot spawn ~ ~ ~ loot the_deep_void:blocks/glutton_tier_1");
			} else if ((new Object() {
				public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
						IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).copy();
					}
					return ItemStack.EMPTY;
				}
			}.getItemStack(world, BlockPos.containing(x, y, z), 0)).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:glutton_tier2")))) {
				GluttonBlockHopperItemEnterProcedure.execute(world, x, y, z, "/loot spawn ~ ~ ~ loot the_deep_void:blocks/glutton_tier_2");
			} else if ((new Object() {
				public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
						IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).copy();
					}
					return ItemStack.EMPTY;
				}
			}.getItemStack(world, BlockPos.containing(x, y, z), 0)).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:glutton_tier3")))) {
				GluttonBlockHopperItemEnterProcedure.execute(world, x, y, z, "/loot spawn ~ ~ ~ loot the_deep_void:blocks/glutton_tier_3");
			} else if ((new Object() {
				public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
						IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).copy();
					}
					return ItemStack.EMPTY;
				}
			}.getItemStack(world, BlockPos.containing(x, y, z), 0)).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:glutton_desolate")))) {
				GluttonBlockHopperItemEnterProcedure.execute(world, x, y, z, "/loot spawn ~ ~ ~ loot the_deep_void:blocks/glutton_desolate_soil");
			} else if ((new Object() {
				public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
						IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).copy();
					}
					return ItemStack.EMPTY;
				}
			}.getItemStack(world, BlockPos.containing(x, y, z), 0)).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("minecraft:soul_fire_base_blocks")))) {
				GluttonBlockHopperItemEnterProcedure.execute(world, x, y, z, "/loot spawn ~ ~ ~ loot the_deep_void:blocks/glutton_soul_soil");
			} else if ((new Object() {
				public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
						IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).copy();
					}
					return ItemStack.EMPTY;
				}
			}.getItemStack(world, BlockPos.containing(x, y, z), 0)).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:glutton_end")))) {
				GluttonBlockHopperItemEnterProcedure.execute(world, x, y, z, "/loot spawn ~ ~ ~ loot the_deep_void:blocks/glutton_end");
			} else if ((new Object() {
				public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
						IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).copy();
					}
					return ItemStack.EMPTY;
				}
			}.getItemStack(world, BlockPos.containing(x, y, z), 0)).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:glutton_givexp")))) {
				GluttonBlockHopperItemEnterProcedure.execute(world, x, y, z, "bossLoot");
			} else {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.fox.aggro")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.fox.aggro")), SoundSource.BLOCKS, 1, 1, false);
					}
				}
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, (new Object() {
						public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
							if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
								IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
								if (_itemHandler != null)
									return _itemHandler.getStackInSlot(slotid).copy();
							}
							return ItemStack.EMPTY;
						}
					}.getItemStack(world, BlockPos.containing(x, y, z), 0)));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof net.minecraft.world.level.LevelAccessor _ext && net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
					ItemStack _setstack = ItemStack.EMPTY.copy();
					_setstack.setCount(1);
					_itemHandlerModifiable.setStackInSlot(0, _setstack);
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, (x + 0.5), (y + 1), (z + 0.5), 5, 1, 1, 1, 0.1);
			}
		}
	}
}
