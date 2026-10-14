package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.ApostleBossEntity;
import net.sombrius.thedeepvoid.entity.ExecutionerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class PlayerIsGrabbedByApostleProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.getPersistentData().getBoolean("grabbedByApostle") == true) {
			if (!world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).isEmpty()) {
				{
					Entity _ent = entity;
					_ent.teleportTo(((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getX() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().x * 0.25, (((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getY()), ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getZ() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().z * 0.25);
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getX() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().x * 0.25, (((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getY()), ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getZ() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().z * 0.25, _ent.getYRot(), _ent.getXRot());
				}
			} else {
				entity.getPersistentData().putBoolean("grabbedByApostle", false);
			}
		}
		if (entity.getPersistentData().getBoolean("piercedByApostle") == true) {
			if (!world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).isEmpty()) {
				{
					Entity _ent = entity;
					_ent.teleportTo(((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getX() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().x * 2, (((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getY() + 1.25), ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getZ() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().z * 2);
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getX() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().x * 2, (((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getY() + 1.25), ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getZ() + ((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().z * 2, _ent.getYRot(), _ent.getXRot());
				}
				if (entity.getPersistentData().getDouble("apostlePierceHeart") <= 0) {
					entity.getPersistentData().putDouble("apostlePierceHeart", 10);
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:boss_attack")))),
							(float) (double) DeepVoidConfigConfiguration.APOSTLEPIERCEHEART.get());
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 1));
					if (!(entity instanceof LivingEntity _livEnt25 && _livEnt25.hasEffect(TheDeepVoidModMobEffects.BROKEN_ARMOR)) && DeepVoidConfigConfiguration.BOSSESBREAKARMOR.get() == true) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.BROKEN_ARMOR, 400, 0));
					}
				} else {
					entity.getPersistentData().putDouble("apostlePierceHeart", (entity.getPersistentData().getDouble("apostlePierceHeart") - 1));
				}
			} else {
				entity.getPersistentData().putBoolean("piercedByApostle", false);
			}
		}
		if (entity.getPersistentData().getBoolean("grabbedByExecutioner") == true) {
			if (!world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).isEmpty()) {
				{
					Entity _ent = entity;
					_ent.teleportTo(((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getX() + ((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().x * 2, (((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getY() + 1.25), ((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getZ() + ((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().z * 2);
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getX() + ((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().x * 2, (((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getY() + 1.25), ((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getZ() + ((Entity) world.getEntitiesOfClass(ExecutionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).getLookAngle().z * 2, _ent.getYRot(), _ent.getXRot());
				}
				if (entity.getPersistentData().getDouble("executionerStab") >= 36) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:boss_attack")))), (float) 0.52);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 1));
				} else {
					entity.getPersistentData().putDouble("executionerStab", (entity.getPersistentData().getDouble("executionerStab") + 1));
				}
			} else {
				entity.getPersistentData().putBoolean("grabbedByExecutioner", false);
			}
		}
	}
}
