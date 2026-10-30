package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;

public class HiveWatcherTickUpdateNewProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2 && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("moveRandomlyFrequency") != 12) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("moveRandomlyFrequency", 12);
		} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 4 && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("moveRandomlyFrequency") != 8) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("moveRandomlyFrequency", 8);
		} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 4 && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("moveRandomlyFrequency") != 0) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("moveRandomlyFrequency", 0);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:everhunger_scream")), SoundSource.HOSTILE, 4, (float) 0.6);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:everhunger_scream")), SoundSource.HOSTILE, 4, (float) 0.6, false);
				}
			}
		}
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 0) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("foundPlayer") == false) {
				if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("findPlayerCooldown") <= 0) {
					HiveWatcherFindPlayerProcedure.execute(world, entity);
				}
				if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("moveInPos") == false) {
					if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("moveRandomly") >= net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("moveRandomlyFrequency")) {
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("moveRandomly", 0);
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("moveInPos", true);
						HiveWatcherMoveRandomlyProcedure.execute(world, entity);
					} else {
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("moveRandomly", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("moveRandomly") + 1));
					}
				} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("moveInPos") == true) {
					if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("stopMove") < ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2 ? 30 : 35)) {
						HiveWatcherWhileMovingProcedure.execute(world, x, y, z, entity);
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("stopMove", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("stopMove") + 1));
					} else {
						HiveWatcherResetTagsProcedure.execute(entity);
						HiveWatcherShootIchorProcedure.execute(world, entity);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("foundPlayer") == true) {
				HiveWatcherRamPlayerProcedure.execute(world, x, y, z, entity);
				HiveWatcherResetTagsProcedure.execute(entity);
			}
		} else if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("foundPlayer", false);
			HiveWatcherResetTagsProcedure.execute(entity);
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("findPlayerCooldown") > 0) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("findPlayerCooldown", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("findPlayerCooldown") - 1));
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("shieldCooldown") > 0) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("shieldCooldown", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("shieldCooldown") - 1));
		}
		HiveWatcherTickAdditionsProcedure.execute(world, x, y, z, entity);
	}
}
