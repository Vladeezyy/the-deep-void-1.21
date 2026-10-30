
package net.sombrius.thedeepvoid.client.screens;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.EventPriority;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.RenderGuiEvent;
import net.sombrius.thedeepvoid.procedures.AnxietySubtleProcedureProcedure;

@EventBusSubscriber({Dist.CLIENT})
public class AnxietySubtleOverlay {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		if (AnxietySubtleProcedureProcedure.execute(world, x, y, z, entity)) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/anxiety1.png"), 0, 0, 0, 0, w, h, w, h);
		}
	}
}
