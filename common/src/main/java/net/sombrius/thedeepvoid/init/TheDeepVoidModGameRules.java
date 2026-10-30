
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.world.level.GameRules;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.FMLCommonSetupEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class TheDeepVoidModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> SPAWN_HOLE_STRUCTURE;
	public static GameRules.Key<GameRules.BooleanValue> GIVE_WIKI_BOOK;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		SPAWN_HOLE_STRUCTURE = GameRules.register("spawnHoleStructure", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
		GIVE_WIKI_BOOK = GameRules.register("giveWikiBook", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	}
}
