package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class AmbientSoundProcedure {
	private static final ResourceKey<Level> DIM_DEEP_VOID = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"));

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
				if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).ForgottenValleyAmbientSound >= 12000) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.ForgottenValleyAmbientSound = 0;
						_vars.syncPlayerVariables(entity);
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:stalker_screams ambient @s ~ ~ ~ 10 1 0.5");
						}
					}
				} else {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.ForgottenValleyAmbientSound = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).ForgottenValleyAmbientSound + 1;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
			if (y < 20) {
				if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).VoidCaveAmbientSound >= 10000) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.VoidCaveAmbientSound = 0;
						_vars.syncPlayerVariables(entity);
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound the_deep_void:deep_void_cave_mood ambient @s ~ ~ ~ 6 1 0.5");
						}
					}
				} else {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.VoidCaveAmbientSound = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).VoidCaveAmbientSound + 1;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
		}
	}
}
