package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortEntities;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.entity.LightEntity;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class CallOfTheVoidDigProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double xPos = 0;
		double yPos = 0;
		double zPos = 0;
		if (TheDeepVoidModVariables.of(entity).playerCallOfTheVoid == true) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:digTime", 0) < 190) {
				if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:digCooldown", 0) >= 10) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digCooldown", 0);
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digTime", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:digTime", 0) + 10));
					if (!(!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 10, 10, 10)).isEmpty()) && !PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0))), 350, 350, 350))
							.isEmpty()) {
						if (world.getBlockState(BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0) - 1, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)))
								.canOcclude()) {
							world.levelEvent(2001, BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)),
									Block.getId((world.getBlockState(
											BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0) - 1, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0))))));
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)),
											BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.gravel.hit")), SoundSource.AMBIENT, 1, 1);
								} else {
									_level.playLocalSound((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)),
											BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.gravel.hit")), SoundSource.AMBIENT, 1, 1, false);
								}
							}
						}
					} else if (!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 10, 10, 10)).isEmpty() || !(!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0))), 350, 350, 350))
							.isEmpty())) {
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
							_vars.playerCallOfTheVoid = false;
							_vars.syncPlayerVariables(entity);
						}
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digCooldown", 0);
						net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digTime", 0);
					}
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digCooldown", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:digCooldown", 0) + 1));
				}
			}
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:digTime", 0) >= 190) {
				if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:digCooldown", 0) >= 10) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.playerCallOfTheVoid = false;
						_vars.syncPlayerVariables(entity);
					}
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digCooldown", 0);
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digTime", 0);
					if (!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0))), 350, 350, 350))
							.isEmpty()) {
						if (!(!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 10, 10, 10)).isEmpty())
								&& !world
										.getEntitiesOfClass(Player.class,
												AABB.ofSize(new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0))), 25,
														25, 25),
												e -> true)
										.isEmpty()
								&& world.getBlockState(BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0) - 1, net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)))
										.canOcclude()) {
							{
								Entity _ent = ((Entity) PortEntities.find(world, StalkerEntity.class, AABB.ofSize(
										new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0))), 350, 350, 350))
										.stream().sorted(new Object() {
											Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
												return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
											}
										}.compareDistOf((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)))).findFirst()
										.orElse(null));
								_ent.teleportTo((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0) + 1), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)));
								if (_ent instanceof ServerPlayer _serverPlayer)
									_serverPlayer.connection.teleport((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0) + 1), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)),
											_ent.getYRot(), _ent.getXRot());
							}
							{
								final Vec3 _center = new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)));
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
								for (Entity entityiterator : _entfound) {
									if (entityiterator instanceof StalkerEntity) {
										if (entityiterator instanceof StalkerEntity) {
											((StalkerEntity) entityiterator).setAnimation("animation.stalker_digOut");
										}
										if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 80, 99, false, false));
										if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 80, 99, false, false));
										if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 99, false, false));
									}
									if (entityiterator instanceof LightEntity) {
										if (world.getBlockState(BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ())).getLightEmission() > 0) {
											world.setBlock(BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), Blocks.AIR.defaultBlockState(), 3);
											if (world instanceof ServerLevel _level)
												_level.sendParticles(ParticleTypes.SOUL, (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 1, 0, 1, 0, 0.1);
											if (!entityiterator.level().isClientSide())
												entityiterator.discard();
										}
									}
								}
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)),
											BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:stalker_emerges_new")), SoundSource.NEUTRAL, 2, 1);
								} else {
									_level.playLocalSound((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:xDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:yDig", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:zDig", 0)),
											BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:stalker_emerges_new")), SoundSource.NEUTRAL, 2, 1, false);
								}
							}
						}
					}
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:digCooldown", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:digCooldown", 0) + 1));
				}
			}
		}
	}
}
