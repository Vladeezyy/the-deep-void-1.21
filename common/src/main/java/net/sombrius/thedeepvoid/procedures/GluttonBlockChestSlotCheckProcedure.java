package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.neo.Capabilities;
import net.sombrius.thedeepvoid.port.neo.IItemHandler;
import net.sombrius.thedeepvoid.port.neo.IItemHandlerModifiable;

public class GluttonBlockChestSlotCheckProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		{
			final Vec3 _center = new Vec3((x + 0.5), (y + 1), (z + 0.5));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof ItemEntity) {
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null)
							net.sombrius.thedeepvoid.port.PortUtil.data(_blockEntity).putDouble("slot", 0);
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					while (new Object() {
						public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
							if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
								IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
								if (_itemHandler != null)
									return _itemHandler.getStackInSlot(slotid).getCount();
							}
							return 0;
						}
					}.getAmount(world, BlockPos.containing(x, y + 1, z), (int) (new Object() {
						public double getValue(LevelAccessor world, BlockPos pos, String tag) {
							BlockEntity blockEntity = world.getBlockEntity(pos);
							if (blockEntity != null)
								return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
							return -1;
						}
					}.getValue(world, BlockPos.containing(x, y, z), "slot"))) != 0 && !((new Object() {
						public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
							if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
								IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
								if (_itemHandler != null)
									return _itemHandler.getStackInSlot(slotid).copy();
							}
							return ItemStack.EMPTY;
						}
					}.getItemStack(world, BlockPos.containing(x, y + 1, z), (int) (new Object() {
						public double getValue(LevelAccessor world, BlockPos pos, String tag) {
							BlockEntity blockEntity = world.getBlockEntity(pos);
							if (blockEntity != null)
								return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
							return -1;
						}
					}.getValue(world, BlockPos.containing(x, y, z), "slot")))).getItem() == (entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).getItem()) && new Object() {
						public double getValue(LevelAccessor world, BlockPos pos, String tag) {
							BlockEntity blockEntity = world.getBlockEntity(pos);
							if (blockEntity != null)
								return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
							return -1;
						}
					}.getValue(world, BlockPos.containing(x, y, z), "slot") < (new Object() {
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
					}.getAmount(world, new BlockPos((int) x, (int) (y + 1), (int) z)) - 1)) {
						if (!world.isClientSide()) {
							BlockPos _bp = BlockPos.containing(x, y, z);
							BlockEntity _blockEntity = world.getBlockEntity(_bp);
							BlockState _bs = world.getBlockState(_bp);
							if (_blockEntity != null)
								net.sombrius.thedeepvoid.port.PortUtil.data(_blockEntity).putDouble("slot", (new Object() {
									public double getValue(LevelAccessor world, BlockPos pos, String tag) {
										BlockEntity blockEntity = world.getBlockEntity(pos);
										if (blockEntity != null)
											return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
										return -1;
									}
								}.getValue(world, BlockPos.containing(x, y, z), "slot") + 1));
							if (world instanceof Level _level)
								_level.sendBlockUpdated(_bp, _bs, _bs, 3);
						}
					}
					if (new Object() {
						public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
							if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
								IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
								if (_itemHandler != null)
									return _itemHandler.getStackInSlot(slotid).getCount();
							}
							return 0;
						}
					}.getAmount(world, BlockPos.containing(x, y + 1, z), (int) (new Object() {
						public double getValue(LevelAccessor world, BlockPos pos, String tag) {
							BlockEntity blockEntity = world.getBlockEntity(pos);
							if (blockEntity != null)
								return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
							return -1;
						}
					}.getValue(world, BlockPos.containing(x, y, z), "slot"))) + (entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).getCount() > 64) {
						if (new Object() {
							public double getValue(LevelAccessor world, BlockPos pos, String tag) {
								BlockEntity blockEntity = world.getBlockEntity(pos);
								if (blockEntity != null)
									return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
								return -1;
							}
						}.getValue(world, BlockPos.containing(x, y, z), "slot") + 1 < (new Object() {
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
						}.getAmount(world, new BlockPos((int) x, (int) (y + 1), (int) z)) - 1)) {
							if (!world.isClientSide()) {
								BlockPos _bp = BlockPos.containing(x, y, z);
								BlockEntity _blockEntity = world.getBlockEntity(_bp);
								BlockState _bs = world.getBlockState(_bp);
								if (_blockEntity != null)
									net.sombrius.thedeepvoid.port.PortUtil.data(_blockEntity).putDouble("slot", (new Object() {
										public double getValue(LevelAccessor world, BlockPos pos, String tag) {
											BlockEntity blockEntity = world.getBlockEntity(pos);
											if (blockEntity != null)
												return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
											return -1;
										}
									}.getValue(world, BlockPos.containing(x, y, z), "slot") + 1));
								if (world instanceof Level _level)
									_level.sendBlockUpdated(_bp, _bs, _bs, 3);
							}
						}
						while (new Object() {
							public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
								if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
									IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
									if (_itemHandler != null)
										return _itemHandler.getStackInSlot(slotid).getCount();
								}
								return 0;
							}
						}.getAmount(world, BlockPos.containing(x, y + 1, z), (int) (new Object() {
							public double getValue(LevelAccessor world, BlockPos pos, String tag) {
								BlockEntity blockEntity = world.getBlockEntity(pos);
								if (blockEntity != null)
									return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
								return -1;
							}
						}.getValue(world, BlockPos.containing(x, y, z), "slot"))) != 0 && !((new Object() {
							public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
								if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
									IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
									if (_itemHandler != null)
										return _itemHandler.getStackInSlot(slotid).copy();
								}
								return ItemStack.EMPTY;
							}
						}.getItemStack(world, BlockPos.containing(x, y + 1, z), (int) (new Object() {
							public double getValue(LevelAccessor world, BlockPos pos, String tag) {
								BlockEntity blockEntity = world.getBlockEntity(pos);
								if (blockEntity != null)
									return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
								return -1;
							}
						}.getValue(world, BlockPos.containing(x, y, z), "slot")))).getItem() == (entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).getItem()) && new Object() {
							public double getValue(LevelAccessor world, BlockPos pos, String tag) {
								BlockEntity blockEntity = world.getBlockEntity(pos);
								if (blockEntity != null)
									return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
								return -1;
							}
						}.getValue(world, BlockPos.containing(x, y, z), "slot") < (new Object() {
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
						}.getAmount(world, new BlockPos((int) x, (int) (y + 1), (int) z)) - 1)) {
							if (!world.isClientSide()) {
								BlockPos _bp = BlockPos.containing(x, y, z);
								BlockEntity _blockEntity = world.getBlockEntity(_bp);
								BlockState _bs = world.getBlockState(_bp);
								if (_blockEntity != null)
									net.sombrius.thedeepvoid.port.PortUtil.data(_blockEntity).putDouble("slot", (new Object() {
										public double getValue(LevelAccessor world, BlockPos pos, String tag) {
											BlockEntity blockEntity = world.getBlockEntity(pos);
											if (blockEntity != null)
												return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
											return -1;
										}
									}.getValue(world, BlockPos.containing(x, y, z), "slot") + 1));
								if (world instanceof Level _level)
									_level.sendBlockUpdated(_bp, _bs, _bs, 3);
							}
						}
					}
					if (new Object() {
						public double getValue(LevelAccessor world, BlockPos pos, String tag) {
							BlockEntity blockEntity = world.getBlockEntity(pos);
							if (blockEntity != null)
								return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
							return -1;
						}
					}.getValue(world, BlockPos.containing(x, y, z), "slot") < (new Object() {
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
					}.getAmount(world, new BlockPos((int) x, (int) (y + 1), (int) z)) - 1)) {
						if (world instanceof net.minecraft.world.level.LevelAccessor _ext && net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, BlockPos.containing(x, y + 1, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
							ItemStack _setstack = (entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).copy();
							_setstack.setCount((int) (new Object() {
								public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
									if (world instanceof net.minecraft.world.level.LevelAccessor _ext) {
										IItemHandler _itemHandler = net.sombrius.thedeepvoid.port.neo.PortCaps.block(_ext, pos, null);
										if (_itemHandler != null)
											return _itemHandler.getStackInSlot(slotid).getCount();
									}
									return 0;
								}
							}.getAmount(world, BlockPos.containing(x, y + 1, z), (int) (new Object() {
								public double getValue(LevelAccessor world, BlockPos pos, String tag) {
									BlockEntity blockEntity = world.getBlockEntity(pos);
									if (blockEntity != null)
										return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
									return -1;
								}
							}.getValue(world, BlockPos.containing(x, y, z), "slot"))) + (entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).getCount()));
							_itemHandlerModifiable.setStackInSlot((int) (new Object() {
								public double getValue(LevelAccessor world, BlockPos pos, String tag) {
									BlockEntity blockEntity = world.getBlockEntity(pos);
									if (blockEntity != null)
										return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getDoubleOr(tag, 0);
									return -1;
								}
							}.getValue(world, BlockPos.containing(x, y, z), "slot")), _setstack);
						}
						if (!entityiterator.level().isClientSide())
							entityiterator.discard();
					}
				}
			}
		}
	}
}
