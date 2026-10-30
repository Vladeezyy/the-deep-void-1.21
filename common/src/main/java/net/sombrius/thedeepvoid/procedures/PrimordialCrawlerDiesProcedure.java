package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import java.util.Comparator;
import java.util.List;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.neo.ItemHandlerHelper;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.PrimordialBoneCrawlerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class PrimordialCrawlerDiesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof PrimordialBoneCrawlerEntity _datEntL0 && _datEntL0.getEntityData().get(PrimordialBoneCrawlerEntity.DATA_dying)) == false) {
			if (entity instanceof PrimordialBoneCrawlerEntity _datEntSetL)
				_datEntSetL.getEntityData().set(PrimordialBoneCrawlerEntity.DATA_dying, true);
			if (entity instanceof PrimordialBoneCrawlerEntity) {
				((PrimordialBoneCrawlerEntity) entity).setAnimation("empty");
			}
			if (entity instanceof PrimordialBoneCrawlerEntity) {
				((PrimordialBoneCrawlerEntity) entity).setAnimation("animation.primordialCrawler_death");
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:mother_crawler_hurt")), SoundSource.HOSTILE, 4, (float) 0.6);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:mother_crawler_hurt")), SoundSource.HOSTILE, 4, (float) 0.6, false);
				}
			}
			TheDeepVoidMod.queueServerWork(133, () -> {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof Player) {
							if (entityiterator instanceof Player _player) {
								ItemStack _setstack = new ItemStack(TheDeepVoidModItems.PRIMORDIAL_CRAWLER_TREASURE_BAG.get()).copy();
								_setstack.setCount(1);
								ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
							}
							if (!(entityiterator instanceof ServerPlayer _plr10 && _plr10.level() instanceof ServerLevel
									&& _plr10.getAdvancements().getOrStartProgress(_plr10.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:not_the_crawlers"))).isDone())) {
								if (entityiterator instanceof ServerPlayer _player) {
									AdvancementHolder _adv = _player.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:not_the_crawlers"));
									if (_adv != null) {
										AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
										if (!_ap.isDone()) {
											for (String criteria : _ap.getRemainingCriteria())
												_player.getAdvancements().award(_adv, criteria);
										}
									}
								}
							}
						}
					}
				}
			});
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"stopsound @a record the_deep_void:the_haunt");
			TheDeepVoidMod.queueServerWork(20, () -> {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"stopsound @a record the_deep_void:the_haunt");
			});
			TheDeepVoidMod.queueServerWork(200, () -> {
				if (DeepVoidConfigConfiguration.SUMMONBOSSRESPAWN.get() == true) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = TheDeepVoidModEntities.GIANT_BONE_CRAWLER_EGG.get().spawn(_level,
								BlockPos.containing(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:eggX", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:eggY", 0), net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:eggZ", 0)), EntitySpawnReason.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				}
			});
		}
	}
}
