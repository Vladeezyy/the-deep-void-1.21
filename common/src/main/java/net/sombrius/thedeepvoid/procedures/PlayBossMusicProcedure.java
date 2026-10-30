package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayBossMusicProcedure {
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
		if (TheDeepVoidModVariables.of(entity).MusicLock) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.MusicTick = TheDeepVoidModVariables.of(entity).MusicTick + 1;
				_vars.syncPlayerVariables(entity);
			}
			if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("darkmare")) {
				PlayMusicDarkmareProcedure.execute(world, x, y, z, entity);
			} else if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("chainsaw")) {
				PlayMusicChainsawProcedure.execute(world, x, y, z, entity);
			} else if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("pitch_black")) {
				PlayMusicPitchBlackProcedure.execute(world, x, y, z, entity);
			} else if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("the_haunt")) {
				PlayMusicTheHauntProcedure.execute(world, x, y, z, entity);
			} else if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("monsters_lair")) {
				PlayMusicMonstersLairProcedure.execute(world, x, y, z, entity);
			} else if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("slaughter")) {
				PlayMusicSlaughterProcedure.execute(world, x, y, z, entity);
			} else if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("under_the_asylum")) {
				PlayMusicUnderTheAsylumProcedure.execute(entity);
			} else if ((TheDeepVoidModVariables.of(entity).MusicPlay).equals("dealing_with_the_devil")) {
				PlayMusicDealingWithTheDevilProcedure.execute(world, x, y, z, entity);
			}
		}
	}
}
