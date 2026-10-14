package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.neoforged.bus.api.ICancellableEvent;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;

@EventBusSubscriber
public class PlayerFishesInLiquidVoidProcedure {
	@SubscribeEvent
	public static void onPlayerFishItem(ItemFishedEvent event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double itemX = 0;
		double itemY = 0;
		double itemZ = 0;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"))) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof FishingHook) {
						entity.getPersistentData().putDouble("rottenRodX", (entityiterator.getX()));
						entity.getPersistentData().putDouble("rottenRodY", (entityiterator.getY()));
						entity.getPersistentData().putDouble("rottenRodZ", (entityiterator.getZ()));
					}
				}
			}
			if (world.getBiome(BlockPos.containing(entity.getPersistentData().getDouble("rottenRodX"), entity.getPersistentData().getDouble("rottenRodY"), entity.getPersistentData().getDouble("rottenRodZ")))
					.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("the_deep_void:flesh_biome")))) {
				if (Math.random() < 0.1 + PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), TheDeepVoidModEnchantments.BAIT) / 10
						&& !(entity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(TheDeepVoidModMobEffects.CREATURE_REPELLENT))) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.FLESH_LAMPREY.get().spawn(_level,
								BlockPos.containing(entity.getPersistentData().getDouble("rottenRodX"), entity.getPersistentData().getDouble("rottenRodY"), entity.getPersistentData().getDouble("rottenRodZ")), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				} else {
					if (Math.random() < PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), TheDeepVoidModEnchantments.CRATE_CATCHER) / 20
							+ (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("crateChance")
							+ ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.CRATE_MAGNET) ? _livEnt.getEffect(TheDeepVoidModMobEffects.CRATE_MAGNET).getAmplifier() : 0) + 1) / 10) {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands()
									.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
											new Vec3((entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ"))), Vec2.ZERO, _level, 4, "",
											Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/loot spawn ~ ~ ~ loot the_deep_void:gameplay/flesh_crate_fishing");
						{
							final String _tagName = "crateChance";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					} else {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands()
									.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
											new Vec3((entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ"))), Vec2.ZERO, _level, 4, "",
											Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/loot spawn ~ ~ ~ loot the_deep_void:gameplay/flesh_fishing");
						{
							final String _tagName = "crateChance";
							final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("crateChance") + 0.01);
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
				}
			} else if (world.getBiome(BlockPos.containing(entity.getPersistentData().getDouble("rottenRodX"), entity.getPersistentData().getDouble("rottenRodY"), entity.getPersistentData().getDouble("rottenRodZ")))
					.is(TagKey.create(Registries.BIOME, ResourceLocation.parse("the_deep_void:bone_crawler_nest")))) {
				if (Math.random() < 0.1 + PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), TheDeepVoidModEnchantments.BAIT) / 10
						&& !(entity instanceof LivingEntity _livEnt47 && _livEnt47.hasEffect(TheDeepVoidModMobEffects.CREATURE_REPELLENT))) {
					if (Math.random() < 0.5) {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.BLACK_FLY.get().spawn(_level,
									BlockPos.containing(entity.getPersistentData().getDouble("rottenRodX"), entity.getPersistentData().getDouble("rottenRodY"), entity.getPersistentData().getDouble("rottenRodZ")), MobSpawnType.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
					} else {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = TheDeepVoidModEntities.MAGGOT.get().spawn(_level,
									BlockPos.containing(entity.getPersistentData().getDouble("rottenRodX"), entity.getPersistentData().getDouble("rottenRodY"), entity.getPersistentData().getDouble("rottenRodZ")), MobSpawnType.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
					}
				} else {
					if (Math.random() < PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), TheDeepVoidModEnchantments.CRATE_CATCHER) / 20
							+ (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("crateChance")
							+ ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.CRATE_MAGNET) ? _livEnt.getEffect(TheDeepVoidModMobEffects.CRATE_MAGNET).getAmplifier() : 0) + 1) / 10) {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands()
									.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
											new Vec3((entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ"))), Vec2.ZERO, _level, 4, "",
											Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/loot spawn ~ ~ ~ loot the_deep_void:gameplay/nest_crate_fishing");
						{
							final String _tagName = "crateChance";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					} else {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands()
									.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
											new Vec3((entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ"))), Vec2.ZERO, _level, 4, "",
											Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/loot spawn ~ ~ ~ loot the_deep_void:gameplay/nest_fishing");
						{
							final String _tagName = "crateChance";
							final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("crateChance") + 0.01);
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
				}
			} else {
				if (Math.random() < 0.1 + PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), TheDeepVoidModEnchantments.BAIT) / 10
						&& !(entity instanceof LivingEntity _livEnt77 && _livEnt77.hasEffect(TheDeepVoidModMobEffects.CREATURE_REPELLENT))) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.SEEKER.get().spawn(_level,
								BlockPos.containing(entity.getPersistentData().getDouble("rottenRodX"), entity.getPersistentData().getDouble("rottenRodY"), entity.getPersistentData().getDouble("rottenRodZ")), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				} else {
					if (Math.random() < PortUtil.enchantmentLevel((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), TheDeepVoidModEnchantments.CRATE_CATCHER) / 20
							+ (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("crateChance")
							+ ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.CRATE_MAGNET) ? _livEnt.getEffect(TheDeepVoidModMobEffects.CRATE_MAGNET).getAmplifier() : 0) + 1) / 10) {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands()
									.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
											new Vec3((entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ"))), Vec2.ZERO, _level, 4, "",
											Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/loot spawn ~ ~ ~ loot the_deep_void:gameplay/void_crate_fishing");
						{
							final String _tagName = "crateChance";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					} else {
						if (Math.random() < 0.002) {
							if (world instanceof ServerLevel _level) {
								ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ")),
										new ItemStack(TheDeepVoidModItems.KNIGHTFISH.get()));
								entityToSpawn.setPickUpDelay(10);
								entityToSpawn.setUnlimitedLifetime();
								_level.addFreshEntity(entityToSpawn);
							}
						} else {
							if (world instanceof ServerLevel _level)
								_level.getServer().getCommands()
										.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,
												new Vec3((entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ"))), Vec2.ZERO, _level, 4, "",
												Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/loot spawn ~ ~ ~ loot the_deep_void:gameplay/void_fishing");
						}
						{
							final String _tagName = "crateChance";
							final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("crateChance") + 0.01);
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
				}
			}
			{
				final Vec3 _center = new Vec3((entity.getPersistentData().getDouble("rottenRodX")), (entity.getPersistentData().getDouble("rottenRodY")), (entity.getPersistentData().getDouble("rottenRodZ")));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof ItemEntity || entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:can_be_fished")))) {
						entityiterator.setDeltaMovement(new Vec3(((entity.getX() - entityiterator.getX()) * 0.15), (((entity.getY() + 1) - entityiterator.getY()) * 0.15), ((entity.getZ() - entityiterator.getZ()) * 0.15)));
					}
				}
			}
		}
	}
}
