package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
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
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.MusicTick = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicTick + 1;
				_vars.syncPlayerVariables(entity);
			}
			if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("darkmare")) {
				PlayMusicDarkmareProcedure.execute(world, x, y, z, entity);
			} else if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("chainsaw")) {
				PlayMusicChainsawProcedure.execute(world, x, y, z, entity);
			} else if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("pitch_black")) {
				PlayMusicPitchBlackProcedure.execute(world, x, y, z, entity);
			} else if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("the_haunt")) {
				PlayMusicTheHauntProcedure.execute(world, x, y, z, entity);
			} else if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("monsters_lair")) {
				PlayMusicMonstersLairProcedure.execute(world, x, y, z, entity);
			} else if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("slaughter")) {
				PlayMusicSlaughterProcedure.execute(world, x, y, z, entity);
			} else if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("under_the_asylum")) {
				PlayMusicUnderTheAsylumProcedure.execute(entity);
			} else if ((entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicPlay).equals("dealing_with_the_devil")) {
				PlayMusicDealingWithTheDevilProcedure.execute(world, x, y, z, entity);
			}
		}
	}
}
