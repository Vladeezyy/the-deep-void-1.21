package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerIsInThePitProcedure {
	private static final ResourceKey<Level> DIM_THE_PIT = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:the_pit"));

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
		if ((entity.level().dimension()) == DIM_THE_PIT
				&& TheDeepVoidModVariables.of(entity).playMusicPit == true) {
			if (!TheDeepVoidModVariables.of(entity).MusicLock) {
				{
					boolean _setval = true;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicLock = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					double _setval = 0;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicTick = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					double _setval = 0;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicWait = _setval;
					_vars.syncPlayerVariables(entity);
				}
				{
					String _setval = "under_the_asylum";
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicPlay = _setval;
					_vars.syncPlayerVariables(entity);
				}
			} else if (TheDeepVoidModVariables.of(entity).MusicReplace) {
				{
					double _setval = 4080;
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.MusicWait = _setval;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}
