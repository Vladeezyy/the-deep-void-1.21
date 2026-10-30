package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class HeartbeatPlaysProcedure {
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
		if (TheDeepVoidModVariables.of(entity).StalkerCount == Math.floor(((double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get() * 40) / 100) && world.getMaxLocalRawBrightness(BlockPos.containing(x, y, z)) == 0 && y > 0
				&& (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.VOID_BLESSING.holder())) == false && (entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(TheDeepVoidModMobEffects.WEAVER_CURSE.holder())) == false) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerCount = Math.floor(((double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get() * 40) / 100) + 1;
				_vars.syncPlayerVariables(entity);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.heartbeatIndicator = 1;
				_vars.syncPlayerVariables(entity);
			}
			TheDeepVoidMod.queueServerWork(4, () -> {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.heartbeatIndicator = 2;
					_vars.syncPlayerVariables(entity);
				}
			});
			TheDeepVoidMod.queueServerWork(10, () -> {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.heartbeatIndicator = 0;
					_vars.syncPlayerVariables(entity);
				}
			});
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
					_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound entity.warden.heartbeat hostile @s ~ ~ ~ 4 0.6 0.5");
				}
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
					_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:stalker_ambient hostile @s ~ ~ ~ 4 0.8 0.5");
				}
			}
		}
		if (TheDeepVoidModVariables.of(entity).StalkerCount == Math.floor(((double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get() * 75) / 100) && world.getMaxLocalRawBrightness(BlockPos.containing(x, y, z)) == 0 && y > 0
				&& (entity instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(TheDeepVoidModMobEffects.VOID_BLESSING.holder())) == false
				&& (entity instanceof LivingEntity _livEnt12 && _livEnt12.hasEffect(TheDeepVoidModMobEffects.WEAVER_CURSE.holder())) == false) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerCount = Math.floor(((double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get() * 75) / 100) + 1;
				_vars.syncPlayerVariables(entity);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.heartbeatIndicator = 1;
				_vars.syncPlayerVariables(entity);
			}
			TheDeepVoidMod.queueServerWork(4, () -> {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.heartbeatIndicator = 2;
					_vars.syncPlayerVariables(entity);
				}
			});
			TheDeepVoidMod.queueServerWork(10, () -> {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.heartbeatIndicator = 0;
					_vars.syncPlayerVariables(entity);
				}
			});
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
					_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound entity.warden.heartbeat hostile @s ~ ~ ~ 4 0.6 0.5");
				}
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
					_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:stalker_ambient hostile @s ~ ~ ~ 4 0.6 0.5");
				}
			}
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
					_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound particle.soul_escape hostile @s ~ ~ ~ 4 0.8 0.5");
				}
			}
		}
		if (TheDeepVoidModVariables.of(entity).StalkerCount >= Math.floor(((double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get() * 92) / 100)
				&& TheDeepVoidModVariables.of(entity).StalkerCount < (double) DeepVoidConfigConfiguration.STALKERSPAWNTIMER.get()) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.PARANOIA.holder(), 5, 0, false, false));
		}
	}
}
