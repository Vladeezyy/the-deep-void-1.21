package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.IchorFlameShotEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class IchorCloudOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity)
			_entity.removeAllEffects();
		if (Math.random() < 0.5) {
			for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 3); index0++) {
				if (world instanceof ServerLevel projectileLevel) {
					Projectile _entityToSpawn = new Object() {
						public Projectile getArrow(Level level, float damage, int knockback) {
							AbstractArrow entityToSpawn = new IchorFlameShotEntity(TheDeepVoidModEntities.ICHOR_FLAME_SHOT.get(), level);
							entityToSpawn.setBaseDamage(damage);
							PortUtil.setKnockback(entityToSpawn, knockback);
							entityToSpawn.setSilent(true);
							return entityToSpawn;
						}
					}.getArrow(projectileLevel, 4, 0);
					_entityToSpawn.setPos(x, (y + 0.25), z);
					_entityToSpawn.shoot((Mth.nextDouble(RandomSource.create(), -0.5, 0.5)), 1, (Mth.nextDouble(RandomSource.create(), -0.5, 0.5)), (float) Mth.nextDouble(RandomSource.create(), 0.1, 0.8), 45);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.HOSTILE, (float) 0.7, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.HOSTILE, (float) 0.7, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("despawn") >= 60) {
			if (!entity.level().isClientSide())
				entity.discard();
		} else {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("despawn", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("despawn") + 1));
		}
	}
}
