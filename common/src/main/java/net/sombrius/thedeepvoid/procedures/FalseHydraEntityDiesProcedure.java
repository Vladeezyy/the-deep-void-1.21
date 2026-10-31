package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import java.util.Comparator;
import java.util.List;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.neo.ItemHandlerHelper;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.FalseHydraArmEntity;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class FalseHydraEntityDiesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof FalseHydraEntity _datEntL0 && _datEntL0.getEntityData().get(FalseHydraEntity.DATA_dying)) == false) {
			if (entity instanceof FalseHydraEntity _datEntSetL)
				_datEntSetL.getEntityData().set(FalseHydraEntity.DATA_dying, true);
			if (entity instanceof FalseHydraEntity) {
				((FalseHydraEntity) entity).setAnimation("empty");
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:false_hydra_scream")), SoundSource.HOSTILE, 3, (float) 0.8);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:false_hydra_scream")), SoundSource.HOSTILE, 3, (float) 0.8, false);
				}
			}
			TheDeepVoidMod.queueServerWork(40, () -> {
				if (DeepVoidConfigConfiguration.FALSEHYDRAREMOVESBRAINFOG.get() == true) {
					TheDeepVoidModVariables.MapVariables.get(world).hydraKilled = true;
					TheDeepVoidModVariables.MapVariables.get(world).syncData(world);
				}
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof Player) {
							if (entityiterator instanceof Player _player) {
								ItemStack _setstack = new ItemStack(TheDeepVoidModItems.FALSE_HYDRA_TREASURE_BAG.get()).copy();
								_setstack.setCount(1);
								ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
							}
							if (!(entityiterator instanceof ServerPlayer _plr7 && _plr7.level() instanceof ServerLevel
									&& _plr7.getAdvancements().getOrStartProgress(_plr7.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:copycat"))).isDone())) {
								if (entityiterator instanceof ServerPlayer _player) {
									AdvancementHolder _adv = _player.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:copycat"));
									if (_adv != null) {
										AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
										if (!_ap.isDone()) {
											for (String criteria : _ap.getRemainingCriteria())
												_player.getAdvancements().award(_adv, criteria);
										}
									}
								}
							}
							if (entityiterator instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect(TheDeepVoidModMobEffects.BRAIN_FOG.holder())) {
								if (entityiterator instanceof LivingEntity _entity)
									_entity.removeEffect(TheDeepVoidModMobEffects.BRAIN_FOG.holder());
							}
						}
					}
				}
			});
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(),
						"stopsound @a record the_deep_void:slaughter");
			TheDeepVoidMod.queueServerWork(20, () -> {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(),
							"stopsound @a record the_deep_void:slaughter");
			});
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(42 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof FalseHydraArmEntity) {
					net.sombrius.thedeepvoid.port.PortUtil.kill(entityiterator);
				}
			}
		}
	}
}
