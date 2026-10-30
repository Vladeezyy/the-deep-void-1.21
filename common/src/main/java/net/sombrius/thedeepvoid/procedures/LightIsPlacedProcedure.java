package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.BlockEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.LightEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class LightIsPlacedProcedure {
	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (DeepVoidConfigConfiguration.DESTROYLIGHTSOURCES.get() == true) {
			if ((world instanceof Level _lvl ? _lvl.dimension() : (world instanceof WorldGenLevel _wgl ? _wgl.getLevel().dimension() : Level.OVERWORLD)) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"))) {
				if (entity instanceof LivingEntity) {
					if (DeepVoidConfigConfiguration.ALLOWSOULLIGHT.get() == true) {
						if (world.getBlockState(BlockPos.containing(x, y, z)).getLightEmission() > 0
								&& !(world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, ResourceLocation.parse("the_deep_void:safe_lights")))
								&& !(world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, ResourceLocation.parse("the_deep_void:soul_lights")))) {
							if (!world.getEntitiesOfClass(LightEntity.class, AABB.ofSize(new Vec3(x, (y + 0.45), z), 0.5, 0.5, 0.5), e -> true).isEmpty() == false) {
								if (entity instanceof LivingEntity _livEnt12 && _livEnt12.hasEffect(TheDeepVoidModMobEffects.BRAIN_FOG.holder())) {
									if (world instanceof ServerLevel _serverLevel) {
										Entity entityinstance = TheDeepVoidModEntities.LIGHT.get().create(_serverLevel, null, BlockPos.containing(x, y + 0.45, z), MobSpawnType.MOB_SUMMONED, false, false);
										if (entityinstance != null) {
											entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
											net.sombrius.thedeepvoid.port.PortUtil.data(entityinstance).putBoolean("brainFog", true);
											_serverLevel.addFreshEntity(entityinstance);
										}
									}
								} else {
									if (world instanceof ServerLevel _level) {
										Entity entityToSpawn = TheDeepVoidModEntities.LIGHT.get().spawn(_level, BlockPos.containing(x, y + 0.45, z), MobSpawnType.MOB_SUMMONED);
										if (entityToSpawn != null) {
											entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
										}
									}
								}
							}
						}
					} else {
						if (world.getBlockState(BlockPos.containing(x, y, z)).getLightEmission() > 0
								&& !(world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, ResourceLocation.parse("the_deep_void:safe_lights")))) {
							if (!world.getEntitiesOfClass(LightEntity.class, AABB.ofSize(new Vec3(x, (y + 0.45), z), 0.5, 0.5, 0.5), e -> true).isEmpty() == false) {
								if (entity instanceof LivingEntity _livEnt21 && _livEnt21.hasEffect(TheDeepVoidModMobEffects.BRAIN_FOG.holder())) {
									if (world instanceof ServerLevel _serverLevel) {
										Entity entityinstance = TheDeepVoidModEntities.LIGHT.get().create(_serverLevel, null, BlockPos.containing(x, y + 0.45, z), MobSpawnType.MOB_SUMMONED, false, false);
										if (entityinstance != null) {
											entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
											net.sombrius.thedeepvoid.port.PortUtil.data(entityinstance).putBoolean("brainFog", true);
											_serverLevel.addFreshEntity(entityinstance);
										}
									}
								} else {
									if (world instanceof ServerLevel _level) {
										Entity entityToSpawn = TheDeepVoidModEntities.LIGHT.get().spawn(_level, BlockPos.containing(x, y + 0.45, z), MobSpawnType.MOB_SUMMONED);
										if (entityToSpawn != null) {
											entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
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
}
