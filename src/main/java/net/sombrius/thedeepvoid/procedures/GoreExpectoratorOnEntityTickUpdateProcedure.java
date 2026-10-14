package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.BloodSpitEntity;
import net.sombrius.thedeepvoid.entity.GoreExpectoratorEntity;
import net.sombrius.thedeepvoid.entity.PusBallProjectileEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class GoreExpectoratorOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
			if ((entity instanceof GoreExpectoratorEntity _datEntI ? _datEntI.getEntityData().get(GoreExpectoratorEntity.DATA_shoot) : 0) >= 10) {
				if (entity instanceof GoreExpectoratorEntity _datEntSetI)
					_datEntSetI.getEntityData().set(GoreExpectoratorEntity.DATA_shoot, 0);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_step")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_step")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
					}
				}
				if (Math.random() < 0.05) {
					{
						Entity _shootFrom = entity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
									AbstractArrow entityToSpawn = new PusBallProjectileEntity(TheDeepVoidModEntities.PUS_BALL_PROJECTILE.get(), level);
									entityToSpawn.setOwner(shooter);
									entityToSpawn.setBaseDamage(damage);
									entityToSpawn.setKnockback(knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, entity, 5, 0);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 0.8, 1);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				} else {
					{
						Entity _shootFrom = entity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
									AbstractArrow entityToSpawn = new BloodSpitEntity(TheDeepVoidModEntities.BLOOD_SPIT.get(), level);
									entityToSpawn.setOwner(shooter);
									entityToSpawn.setBaseDamage(damage);
									entityToSpawn.setKnockback(knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, entity, 2, 0);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 0.8, 2);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				}
			} else {
				if (entity instanceof GoreExpectoratorEntity _datEntSetI)
					_datEntSetI.getEntityData().set(GoreExpectoratorEntity.DATA_shoot, (int) ((entity instanceof GoreExpectoratorEntity _datEntI ? _datEntI.getEntityData().get(GoreExpectoratorEntity.DATA_shoot) : 0) + 1));
			}
		}
	}
}
