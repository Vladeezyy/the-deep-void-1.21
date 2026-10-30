package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.biome.Biome;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.entity.WatchingStalkerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class StalkingStalkerSpawnsProcedure {
	private static final TagKey<Biome> BIOME_TAG_STALKER_SAFE = TagKey.create(Registries.BIOME, Identifier.parse("the_deep_void:stalker_safe"));
	private static final TagKey<Block> BLOCK_TAG_LIGHT_CANNOT_REACH = net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:light_cannot_reach"));
	private static final ResourceKey<Level> DIM_DEEP_VOID = ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:deep_void"));

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
		if (DeepVoidConfigConfiguration.STALKERSPAWNS.get() == true) {
			if ((entity.level().dimension()) == DIM_DEEP_VOID || (entity.level().dimension()) == Level.OVERWORLD && DeepVoidConfigConfiguration.STALKED.get() == true) {
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
				}.checkGamemode(entity)) && !(new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
						} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
							return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
									&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.SPECTATOR;
						}
						return false;
					}
				}.checkGamemode(entity))) {
					if (!world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_TAG_STALKER_SAFE)) {
						if (y > 0 || (entity.level().dimension()) == Level.OVERWORLD && DeepVoidConfigConfiguration.STALKED.get() == true) {
							if (TheDeepVoidModVariables.of(entity).StalkerCount < (double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get()) {
								if (!(!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3(x, y, z), 180, 180, 180)).isEmpty())
										&& !(entity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(TheDeepVoidModMobEffects.VOID_BLESSING.holder()))
										&& !(entity instanceof LivingEntity _livEnt18 && _livEnt18.hasEffect(TheDeepVoidModMobEffects.WEAVER_CURSE.holder()))) {
									if (entity instanceof LivingEntity _livEnt19 && _livEnt19.hasEffect(TheDeepVoidModMobEffects.DEAD_INSIDE.holder())) {
										{
											TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
											_vars.StalkerCount = TheDeepVoidModVariables.of(entity).StalkerCount
													+ (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.DEAD_INSIDE.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.DEAD_INSIDE.holder()).getAmplifier() : 0) + 1;
											_vars.syncPlayerVariables(entity);
										}
									} else {
										if ((world.getBlockState(BlockPos.containing(x, y - 0.5, z))).is(BLOCK_TAG_LIGHT_CANNOT_REACH)) {
											if (world.getMaxLocalRawBrightness(BlockPos.containing(x, y + 1, z)) == 0) {
												{
													TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
													_vars.StalkerCount = TheDeepVoidModVariables.of(entity).StalkerCount + 1;
													_vars.syncPlayerVariables(entity);
												}
											}
										} else {
											{
												TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
												_vars.StalkerCount = TheDeepVoidModVariables.of(entity).StalkerCount + 1;
												_vars.syncPlayerVariables(entity);
											}
										}
									}
								}
							}
							if (DeepVoidConfigConfiguration.STALKINGSTALKERSPAWNS.get() == true) {
								if (TheDeepVoidModVariables.of(entity).stalkWatcherCount >= 200 + TheDeepVoidModVariables.of(entity).randomStalkerValue) {
									{
										TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
										_vars.stalkWatcherCount = 0;
										_vars.syncPlayerVariables(entity);
									}
									TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnX = entity.getX() + Mth.nextInt(RandomSource.create(), -160, 160);
									TheDeepVoidModVariables.MapVariables.get(world).syncData(world);
									TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnZ = entity.getZ() + Mth.nextInt(RandomSource.create(), -160, 160);
									TheDeepVoidModVariables.MapVariables.get(world).syncData(world);
									if (!(!world
											.getEntitiesOfClass(Player.class,
													AABB.ofSize(new Vec3(TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnX, (entity.getY()), TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnZ), 60, 60, 60), e -> true)
											.isEmpty()) && !(!PortEntities.find(world, WatchingStalkerEntity.class, AABB.ofSize(new Vec3(x, y, z), 160, 160, 160)).isEmpty())) {
										if (world instanceof ServerLevel _serverLevel) {
											Entity entityinstance = TheDeepVoidModEntities.WATCHING_STALKER.get().create(_serverLevel, null,
													BlockPos.containing(TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnX, entity.getY(), TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnZ), EntitySpawnReason.MOB_SUMMONED, false,
													false);
											if (entityinstance != null) {
												entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
												if (entityinstance instanceof WatchingStalkerEntity) {
													((WatchingStalkerEntity) entityinstance).setAnimation("animation.stalker_digOut");
												}
												if (entityinstance instanceof LivingEntity _entity && !_entity.level().isClientSide())
													_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 75, 99, false, false));
												if (entityinstance instanceof LivingEntity _entity && !_entity.level().isClientSide())
													_entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 75, 99, false, false));
												if (entityinstance instanceof LivingEntity _entity && !_entity.level().isClientSide())
													_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 75, 99, false, false));
												_serverLevel.addFreshEntity(entityinstance);
											}
										}
									} else if (!world
											.getEntitiesOfClass(Player.class,
													AABB.ofSize(new Vec3(TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnX, (entity.getY()), TheDeepVoidModVariables.MapVariables.get(world).stalkerSpawnZ), 60, 60, 60), e -> true)
											.isEmpty()) {
										{
											TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
											_vars.stalkWatcherCount = 240;
											_vars.syncPlayerVariables(entity);
										}
									}
								} else {
									{
										TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
										_vars.stalkWatcherCount = TheDeepVoidModVariables.of(entity).stalkWatcherCount + 1;
										_vars.syncPlayerVariables(entity);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
