package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortEntities;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.entity.UndertakerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class SpawnUndertakerInDeepVoidProcedure {
	private static final TagKey<Biome> BIOME_TAG_UNDERTAKER_CANNOT_SPAWN = TagKey.create(Registries.BIOME, ResourceLocation.parse("the_deep_void:undertaker_cannot_spawn"));
	private static final ResourceKey<Level> DIM_DEEP_VOID = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"));

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
		Entity chosenPlayer = null;
		double rndX = 0;
		double rndY = 0;
		double rndZ = 0;
		if (DeepVoidConfigConfiguration.SPAWNUNDERTAKER.get() == true) {
			if (TheDeepVoidModVariables.MapVariables.get(world).undertakerSpawn > 0 && world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) == true) {
				TheDeepVoidModVariables.MapVariables.get(world).undertakerSpawn = TheDeepVoidModVariables.MapVariables.get(world).undertakerSpawn - 1 / (world.players().size() <= 0 ? 1 : world.players().size());
				TheDeepVoidModVariables.MapVariables.get(world).setDirty(); // only the server reads it: saved, not sent every tick
			} else if (TheDeepVoidModVariables.MapVariables.get(world).undertakerSpawn <= 0 && world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) == true
					&& !(entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(TheDeepVoidModMobEffects.WEAVER_CURSE.holder())) && !(entity instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect(TheDeepVoidModMobEffects.CALL_OF_THE_VOID.holder()))
					&& (entity.level().dimension()) == DIM_DEEP_VOID) {
				TheDeepVoidModVariables.MapVariables.get(world).undertakerSpawn = (double) DeepVoidConfigConfiguration.UNDERTAKERSPAWNTIMER.get();
				TheDeepVoidModVariables.MapVariables.get(world).syncData(world);
				if (Math.random() < (double) DeepVoidConfigConfiguration.UNDERTAKERSPAWNCHANCE.get()) {
					for (int index0 = 0; index0 < 100; index0++) {
						rndX = entity.getX() + Mth.nextInt(RandomSource.create(), -48, 48);
						rndY = entity.getY() + Mth.nextInt(RandomSource.create(), -4, 4);
						rndZ = entity.getZ() + Mth.nextInt(RandomSource.create(), -48, 48);
						if (world.isEmptyBlock(BlockPos.containing(rndX, rndY, rndZ)) && world.isEmptyBlock(BlockPos.containing(rndX, rndY + 1, rndZ)) && world.getBlockState(BlockPos.containing(rndX, rndY - 1, rndZ)).canOcclude()
								&& world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) rndX, (int) rndZ) == rndY
								&& !(!PortEntities.find(world, UndertakerEntity.class, AABB.ofSize(new Vec3(rndX, rndY, rndZ), 96, 96, 96)).isEmpty())
								&& !(!PortEntities.find(world, StalkerEntity.class, AABB.ofSize(new Vec3(rndX, rndY, rndZ), 96, 96, 96)).isEmpty())
								&& !world.getBiome(BlockPos.containing(rndX, rndY, rndZ)).is(BIOME_TAG_UNDERTAKER_CANNOT_SPAWN)) {
							if (world instanceof ServerLevel _serverLevel) {
								Entity entityinstance = TheDeepVoidModEntities.UNDERTAKER.get().create(_serverLevel, null, BlockPos.containing(rndX, rndY, rndZ), MobSpawnType.MOB_SUMMONED, false, false);
								if (entityinstance != null) {
									entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
									if (entityinstance instanceof UndertakerEntity _datEntSetI)
										_datEntSetI.getEntityData().set(UndertakerEntity.DATA_despawn, 24000);
									_serverLevel.addFreshEntity(entityinstance);
								}
							}
							break;
						}
					}
				}
			}
		}
	}
}
