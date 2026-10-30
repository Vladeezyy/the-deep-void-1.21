package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerEvent;
import net.sombrius.thedeepvoid.port.neo.ItemHandlerHelper;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.init.TheDeepVoidModGameRules;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerEntersWorldProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.StalkerCountFinish = false;
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.StalkerStalkCount = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
			_vars.randomStalkerValue = Mth.nextInt(RandomSource.create(), 0, 60);
			_vars.syncPlayerVariables(entity);
		}
		if (TheDeepVoidModVariables.of(entity).receivedWikiBook == false && DeepVoidConfigConfiguration.GIVEWIKIBOOK.get() == true) {
			if (world.getLevelData().getGameRules().getBoolean(TheDeepVoidModGameRules.GIVE_WIKI_BOOK) == true) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.receivedWikiBook = true;
					_vars.syncPlayerVariables(entity);
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(TheDeepVoidModItems.BOOK_OF_ANCIENT_KNOWLEDGE.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
			}
		}
	}
}
