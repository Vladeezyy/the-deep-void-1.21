package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class FalseHydraSummonArmsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double rnd = 0;
		if (entity instanceof FalseHydraEntity) {
			((FalseHydraEntity) entity).setAnimation("empty");
		}
		if (entity instanceof FalseHydraEntity) {
			((FalseHydraEntity) entity).setAnimation("animation.falseHydra_arms");
		}
		TheDeepVoidMod.queueServerWork(15, () -> {
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, (entity.getX()), (entity.getY()), (entity.getZ()), 0, Level.ExplosionInteraction.NONE);
			{
				final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(42 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player) {
						for (int index0 = 0; index0 < (int) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2.1 ? 9 : 7); index0++) {
							net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).putDouble("xPosHydra", (entityiterator.getX() + Mth.nextInt(RandomSource.create(), -9, 9)));
							net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).putDouble("yPosHydra", (entityiterator.getY() + Mth.nextInt(RandomSource.create(), -1, 1)));
							net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).putDouble("zPosHydra", (entityiterator.getZ() + Mth.nextInt(RandomSource.create(), -9, 9)));
							if (world.isEmptyBlock(BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("xPosHydra"), net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("yPosHydra"), net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("zPosHydra")))
									&& world.getBlockState(
											BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("xPosHydra"), net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("yPosHydra") - 1, net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("zPosHydra")))
											.canOcclude()) {
								if (world instanceof ServerLevel _serverLevel) {
									Entity entityinstance = TheDeepVoidModEntities.FALSE_HYDRA_ARM.get().create(_serverLevel, null,
											BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("xPosHydra"), net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("yPosHydra"), net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getDouble("zPosHydra")),
											MobSpawnType.MOB_SUMMONED, false, false);
									if (entityinstance != null) {
										entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
										if (entityinstance instanceof Mob _entity && entityiterator instanceof LivingEntity _ent)
											_entity.setTarget(_ent);
										_serverLevel.addFreshEntity(entityinstance);
									}
								}
							}
						}
					}
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(9 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof LivingEntity && !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:boss")))) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:boss_attack"))), entity),
								(float) ((double) DeepVoidConfigConfiguration.HYDRAHITGROUND.get()
										+ (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.DAMAGE_BOOST) ? _livEnt.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() : 0)));
					}
				}
			}
			for (int index1 = 0; index1 < 24; index1++) {
				world.levelEvent(2001, BlockPos.containing(entity.getX() + Mth.nextInt(RandomSource.create(), -5, 5) + 0.5, entity.getY(), entity.getZ() + Mth.nextInt(RandomSource.create(), -5, 5) + 0.5),
						Block.getId((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ())))));
			}
			for (int index2 = 0; index2 < Mth.nextInt(RandomSource.create(), 6, 12); index2++) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = TheDeepVoidModEntities.FALLING_CORPSE.get().spawn(_level, BlockPos.containing(entity.getX(), entity.getY() + 1, entity.getZ()), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot((float) Mth.nextDouble(RandomSource.create(), -1, 1));
						entityToSpawn.setYBodyRot((float) Mth.nextDouble(RandomSource.create(), -1, 1));
						entityToSpawn.setYHeadRot((float) Mth.nextDouble(RandomSource.create(), -1, 1));
						entityToSpawn.setXRot((float) Mth.nextDouble(RandomSource.create(), -1, 1));
						entityToSpawn.setDeltaMovement((Mth.nextDouble(RandomSource.create(), -1, 1)), (Mth.nextDouble(RandomSource.create(), 0.25, 1)), (Mth.nextDouble(RandomSource.create(), -1, 1)));
					}
				}
			}
		});
	}
}
