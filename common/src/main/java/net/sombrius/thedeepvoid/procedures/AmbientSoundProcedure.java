package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class AmbientSoundProcedure {
	private static final ResourceKey<Level> DIM_DEEP_VOID = ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:deep_void"));

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().getY(), event.getEntity());
	}

	public static void execute(double y, Entity entity) {
		execute(null, y, entity);
	}

	private static void execute(@Nullable Event event, double y, Entity entity) {
		if (entity == null)
			return;
		if ((entity.level().dimension()) == DIM_DEEP_VOID) {
			if (y >= 20) {
				if (TheDeepVoidModVariables.of(entity).ForgottenValleyAmbientSound >= 12000) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.ForgottenValleyAmbientSound = 0;
						_vars.syncPlayerVariables(entity);
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER, _ent.level().getServer(), _ent), "playsound the_deep_void:stalker_screams ambient @s ~ ~ ~ 10 1 0.5");
						}
					}
				} else {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.ForgottenValleyAmbientSound = TheDeepVoidModVariables.of(entity).ForgottenValleyAmbientSound + 1;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
			if (y < 20) {
				if (TheDeepVoidModVariables.of(entity).VoidCaveAmbientSound >= 10000) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.VoidCaveAmbientSound = 0;
						_vars.syncPlayerVariables(entity);
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.level().getServer() != null) {
							_ent.level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, LevelBasedPermissionSet.OWNER, _ent.level().getServer(), _ent), "playsound the_deep_void:deep_void_cave_mood ambient @s ~ ~ ~ 6 1 0.5");
						}
					}
				} else {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
						_vars.VoidCaveAmbientSound = TheDeepVoidModVariables.of(entity).VoidCaveAmbientSound + 1;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
		}
	}
}
