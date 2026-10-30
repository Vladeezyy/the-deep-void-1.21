
package net.sombrius.thedeepvoid.command;

import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.RegisterCommandsEvent;
import net.sombrius.thedeepvoid.procedures.PlaceSepulcherCommandExecutedProcedure;

@EventBusSubscriber
public class PlaceSepulcherCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("placesepulcher").requires(s -> s.hasPermission(4)).executes(arguments -> {
			Level world = arguments.getSource().getLevel();
			double x = arguments.getSource().getPosition().x();
			double y = arguments.getSource().getPosition().y();
			double z = arguments.getSource().getPosition().z();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.fakePlayer(_servLevel);
			Direction direction = Direction.DOWN;
			if (entity != null)
				direction = entity.getDirection();

			PlaceSepulcherCommandExecutedProcedure.execute(world, entity);
			return 0;
		}));
	}
}
