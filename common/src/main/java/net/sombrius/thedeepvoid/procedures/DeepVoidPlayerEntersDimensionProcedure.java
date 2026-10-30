package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.EntityTravelToDimensionEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class DeepVoidPlayerEntersDimensionProcedure {
	@SubscribeEvent
	public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
		execute(event, event.getEntity().level(), event.getDimension(), event.getEntity());
	}

	public static void execute(LevelAccessor world, ResourceKey<Level> dimension, Entity entity) {
		execute(null, world, dimension, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, ResourceKey<Level> dimension, Entity entity) {
		if (dimension == null || entity == null)
			return;
		if (dimension == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"))) {
			if (entity instanceof Player) {
				if (TheDeepVoidModVariables.of(entity).naturalTeleport == true) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.naturalTeleport = false;
						_vars.syncPlayerVariables(entity);
					}
					TheDeepVoidMod.queueServerWork(5, () -> {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX()), 300, (entity.getZ()));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX()), 300, (entity.getZ()), _ent.getYRot(), _ent.getXRot());
						}
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
							_vars.FallSound = true;
							_vars.syncPlayerVariables(entity);
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "stopsound @s ambient minecraft:block.portal.travel");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:wind_falling player @s ~ ~ ~ 10 1 1");
							}
						}
					});
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 45, 0));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.VOID_BLESSING.holder(), (int) (double) DeepVoidConfigConfiguration.VOIDBLESSINGTIMER.get(), 0));
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.StalkerSpawn = false;
						_vars.syncPlayerVariables(entity);
					}
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.StalkerCountFinish = false;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
		}
	}
}
