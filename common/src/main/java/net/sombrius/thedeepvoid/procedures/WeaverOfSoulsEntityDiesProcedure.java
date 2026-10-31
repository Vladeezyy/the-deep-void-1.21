package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import java.util.Comparator;
import java.util.List;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.neo.ItemHandlerHelper;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class WeaverOfSoulsEntityDiesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof WeaverOfSoulsBossEntity _datEntL0 && _datEntL0.getEntityData().get(WeaverOfSoulsBossEntity.DATA_dying)) == false) {
			if (entity instanceof WeaverOfSoulsBossEntity _datEntSetL)
				_datEntSetL.getEntityData().set(WeaverOfSoulsBossEntity.DATA_dying, true);
			if (entity instanceof WeaverOfSoulsBossEntity) {
				((WeaverOfSoulsBossEntity) entity).setAnimation("empty");
			}
			if (entity instanceof WeaverOfSoulsBossEntity) {
				((WeaverOfSoulsBossEntity) entity).setAnimation("animation.fallenWeaver_death");
			}
			TheDeepVoidMod.queueServerWork(73, () -> {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.SCULK_SOUL, x, y, z, 20, 0.4, 0.4, 0.4, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 20, 0.4, 0.4, 0.4, 0.1);
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof Player) {
							if (entityiterator instanceof Player _player) {
								ItemStack _setstack = new ItemStack(TheDeepVoidModItems.WEAVER_OF_SOULS_TREASURE_BAG.get()).copy();
								_setstack.setCount(1);
								ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
							}
							if (!(entityiterator instanceof ServerPlayer _plr8 && _plr8.level() instanceof ServerLevel
									&& _plr8.getAdvancements().getOrStartProgress(_plr8.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:weave_at_no_end"))).isDone())) {
								if (entityiterator instanceof ServerPlayer _player) {
									AdvancementHolder _adv = _player.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:weave_at_no_end"));
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
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(),
						"stopsound @a record the_deep_void:darkmare");
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(),
						"stopsound @a ambient the_deep_void:ominous_murmurs");
			TheDeepVoidMod.queueServerWork(20, () -> {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(),
							"stopsound @a record the_deep_void:darkmare");
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(),
							"stopsound @a ambient the_deep_void:ominous_murmurs");
			});
		}
	}
}
