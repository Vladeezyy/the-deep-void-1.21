
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
import net.sombrius.thedeepvoid.procedures.PerilChargesDisplayOverlayIngameProcedure;
import net.sombrius.thedeepvoid.procedures.PerilShowSkull1Procedure;
import net.sombrius.thedeepvoid.procedures.PerilShowSkull2Procedure;
import net.sombrius.thedeepvoid.procedures.PerilShowSkull3Procedure;
import net.sombrius.thedeepvoid.procedures.PerilShowSkull4Procedure;
import net.sombrius.thedeepvoid.procedures.PerilShowSkull5Procedure;

@EventBusSubscriber({Dist.CLIENT})
public class PerilChargesOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
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
		if (PerilChargesDisplayOverlayIngameProcedure.execute(entity)) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_skull_empty.png"), w / 2 + -25, h / 2 + 16, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_skull_empty.png"), w / 2 + -15, h / 2 + 16, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_skull_empty.png"), w / 2 + -5, h / 2 + 16, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_skull_empty.png"), w / 2 + 5, h / 2 + 16, 0, 0, 9, 9, 9, 9);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_skull_empty.png"), w / 2 + 15, h / 2 + 16, 0, 0, 9, 9, 9, 9);

			if (PerilShowSkull1Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_full_skull.png"), w / 2 + -25, h / 2 + 16, 0, 0, 9, 9, 9, 9);
			}
			if (PerilShowSkull2Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_full_skull.png"), w / 2 + -15, h / 2 + 16, 0, 0, 9, 9, 9, 9);
			}
			if (PerilShowSkull3Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_full_skull.png"), w / 2 + -5, h / 2 + 16, 0, 0, 9, 9, 9, 9);
			}
			if (PerilShowSkull4Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_full_skull.png"), w / 2 + 5, h / 2 + 16, 0, 0, 9, 9, 9, 9);
			}
			if (PerilShowSkull5Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/peril_full_skull.png"), w / 2 + 15, h / 2 + 16, 0, 0, 9, 9, 9, 9);
			}
		}
	}
}
