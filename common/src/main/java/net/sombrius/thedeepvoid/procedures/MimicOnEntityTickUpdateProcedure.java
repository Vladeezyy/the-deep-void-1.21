package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.MimicEntity;

public class MimicOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
			if ((entity instanceof MimicEntity _datEntL2 && _datEntL2.getEntityData().get(MimicEntity.DATA_canMove)) == false) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 5, 99, false, false));
				if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 60, 60, 60), e -> true).isEmpty()) {
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((((Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 60, 60, 60), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getX()), (((Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 60, 60, 60), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getY() + 1.4), (((Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 60, 60, 60), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getZ())));
				}
				if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 12, 12, 12), e -> true).isEmpty()) {
					if (!(new Object() {
						public boolean checkGamemode(Entity _ent) {
							if (_ent instanceof ServerPlayer _serverPlayer) {
								return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
							} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
								return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
										&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.CREATIVE;
							}
							return false;
						}
					}.checkGamemode(((Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 12, 12, 12), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)))) && !(new Object() {
						public boolean checkGamemode(Entity _ent) {
							if (_ent instanceof ServerPlayer _serverPlayer) {
								return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
							} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
								return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
										&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.SPECTATOR;
							}
							return false;
						}
					}.checkGamemode(((Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 12, 12, 12), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null))))) {
						if (entity instanceof MimicEntity _datEntSetL)
							_datEntSetL.getEntityData().set(MimicEntity.DATA_canMove, true);
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.SPEED, 40, 2, false, false));
					}
				}
			}
			if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Player) {
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.HEAD, (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy()));
					}
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.CHEST,
								(((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy()));
					}
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.LEGS, (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.LEGS, (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy()));
					}
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.FEET, (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.FEET, (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy()));
					}
				}
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy()).copy();
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).copy()).copy();
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).has(DataComponents.FOOD)
						&& ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entUseItem48 ? _entUseItem48.getUseItem() : ItemStack.EMPTY).has(DataComponents.FOOD)
						&& !(entity instanceof LivingEntity _livEnt50 && _livEnt50.hasEffect(MobEffects.SLOWNESS))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 32, 3, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 32, 99, false, false));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
					TheDeepVoidMod.queueServerWork(10, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(20, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(30, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(32, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1), false);
							}
						}
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 8));
						if (entity instanceof LivingEntity _entity)
							_entity.swing(InteractionHand.MAIN_HAND, net.minecraft.world.item.component.SwingAnimation.DEFAULT, true);
					});
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).has(DataComponents.FOOD)
						&& ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _entUseItem73 ? _entUseItem73.getUseItem() : ItemStack.EMPTY).has(DataComponents.FOOD)
						&& !(entity instanceof LivingEntity _livEnt75 && _livEnt75.hasEffect(MobEffects.SLOWNESS))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 32, 3, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 32, 99, false, false));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
					TheDeepVoidMod.queueServerWork(10, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(20, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(30, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					});
					TheDeepVoidMod.queueServerWork(32, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1));
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.player.burp")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1), false);
							}
						}
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 8));
						if (entity instanceof LivingEntity _entity)
							_entity.swing(InteractionHand.OFF_HAND, net.minecraft.world.item.component.SwingAnimation.DEFAULT, true);
					});
				}
			}
		}
	}
}
