package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.HiveWatcherEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class HiveWatcherRamPlayerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof HiveWatcherEntity _datEntL0 && _datEntL0.getEntityData().get(HiveWatcherEntity.DATA_changeTexture)) == false) {
			if (entity instanceof HiveWatcherEntity _datEntSetL)
				_datEntSetL.getEntityData().set(HiveWatcherEntity.DATA_changeTexture, true);
			if (entity instanceof HiveWatcherEntity animatable)
				animatable.setTexture("hivewatcher");
		}
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("speedBuildUpTarget",
				(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUpTarget", 0) + ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 1.4 ? 0.025 : 0.015)));
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("stopRamming", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("stopRamming", 0) + 1));
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("playerIsIn", 0) == 1) {
			entity.setDeltaMovement(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUpTarget", 0)), 0, 0));
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("playerIsIn", 0) == 2) {
			entity.setDeltaMovement(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUpTarget", 0) * (-1)), 0, 0));
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("playerIsIn", 0) == 3) {
			entity.setDeltaMovement(new Vec3(0, 0, (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUpTarget", 0))));
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("playerIsIn", 0) == 4) {
			entity.setDeltaMovement(new Vec3(0, 0, (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUpTarget", 0) * (-1))));
		}
		if ((world.getBlockFloorHeight(BlockPos.containing(entity.getX() + 3, entity.getY(), entity.getZ())) > 0 || world.getBlockFloorHeight(BlockPos.containing(entity.getX() - 3, entity.getY(), entity.getZ())) > 0
				|| world.getBlockFloorHeight(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 3)) > 0 || world.getBlockFloorHeight(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 3)) > 0)
				&& net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("speedBuildUpTarget", 0) >= 0.5 || net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("stopRamming", 0) >= 60) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("foundPlayer", false);
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("playerIsIn", 0);
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("findPlayerCooldown", 40);
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 4) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("findPlayerCooldown", 5);
			} else {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("findPlayerCooldown", 60);
			}
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("speedBuildUpTarget", 0);
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("stopRamming", 0);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:primordial_crawler_impact")), SoundSource.HOSTILE, 4, (float) 0.9);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:primordial_crawler_impact")), SoundSource.HOSTILE, 4, (float) 0.9, false);
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.TREMBLE.holder(), 20, 1, false, false));
					}
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 4
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 1.2) {
				HiveWatcherSpawnTeethProcedure.execute(world, x, y, z, entity);
			}
			if ((entity instanceof HiveWatcherEntity _datEntL61 && _datEntL61.getEntityData().get(HiveWatcherEntity.DATA_changeTexture)) == true) {
				if (entity instanceof HiveWatcherEntity _datEntSetL)
					_datEntSetL.getEntityData().set(HiveWatcherEntity.DATA_changeTexture, false);
				if (entity instanceof HiveWatcherEntity animatable)
					animatable.setTexture("hivewatchernoeyes");
			}
		}
	}
}
