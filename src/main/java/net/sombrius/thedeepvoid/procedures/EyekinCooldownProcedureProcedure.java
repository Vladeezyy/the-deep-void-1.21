package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class EyekinCooldownProcedureProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).eyekinCooldown > 0) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.eyekinCooldown = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).eyekinCooldown - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).eyekinDash > 0) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.eyekinDash = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).eyekinDash - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
