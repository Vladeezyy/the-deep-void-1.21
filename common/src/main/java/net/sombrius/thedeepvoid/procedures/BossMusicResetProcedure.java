package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerEvent;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class BossMusicResetProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.MusicWait = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.MusicTick = -1;
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.MusicPlay = "";
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.MusicLock = false;
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.MusicReplace = false;
			_vars.syncPlayerVariables(entity);
		}
	}
}
