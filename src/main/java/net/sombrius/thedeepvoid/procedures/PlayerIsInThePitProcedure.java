package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerIsInThePitProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:the_pit"))
				&& entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).playMusicPit == true) {
			if (!entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
				{
					boolean _setval = true;
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicLock = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					double _setval = 0;
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicTick = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					double _setval = 0;
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicWait = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					String _setval = "under_the_asylum";
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicPlay = _setval;
					_vars.syncPlayerVariables(entity);
				}
			} else if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
				{
					double _setval = 4080;
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.MusicWait = _setval;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}
