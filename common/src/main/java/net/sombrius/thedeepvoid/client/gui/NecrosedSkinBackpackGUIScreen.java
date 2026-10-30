package net.sombrius.thedeepvoid.client.gui;

import net.minecraft.client.renderer.RenderPipelines;
import net.sombrius.thedeepvoid.port.PortImageButton;

import net.sombrius.thedeepvoid.port.neo.PacketDistributor;

import java.util.HashMap;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.network.NecrosedSkinBackpackGUIButtonMessage;
import net.sombrius.thedeepvoid.world.inventory.NecrosedSkinBackpackGUIMenu;

public class NecrosedSkinBackpackGUIScreen extends AbstractContainerScreen<NecrosedSkinBackpackGUIMenu> {
	private final static HashMap<String, Object> guistate = NecrosedSkinBackpackGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_crafting_icon;

	public NecrosedSkinBackpackGUIScreen(NecrosedSkinBackpackGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 176, 180);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	private static final Identifier texture = Identifier.parse("the_deep_void:textures/screens/necrosed_skin_backpack_gui.png");

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int gx, int gy, float partialTicks) {
		super.extractBackground(guiGraphics, gx, gy, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/gui_tool_slot.png"), this.leftPos + -17, this.topPos + 15, 0, 0, 18, 18, 18, 18);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/gui_tool_slot.png"), this.leftPos + -17, this.topPos + 33, 0, 0, 18, 18, 18, 18);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/gui_tool_slot.png"), this.leftPos + -17, this.topPos + 51, 0, 0, 18, 18, 18, 18);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/gui_tool_slot.png"), this.leftPos + -17, this.topPos + 69, 0, 0, 18, 18, 18, 18);

	}

	@Override
	public boolean keyPressed(net.minecraft.client.input.KeyEvent event) {
		int key = event.key();
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, Component.translatable("gui.the_deep_void.necrosed_skin_backpack_gui.label_backpack"), 7, 5, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_crafting_icon = new PortImageButton(this.leftPos + -17, this.topPos + -3, 18, 18, 0, 0, 18, Identifier.parse("the_deep_void:textures/screens/atlas/imagebutton_crafting_icon.png"), 18, 36, e -> {
			if (true) {
				PacketDistributor.sendToServer(new NecrosedSkinBackpackGUIButtonMessage(0, x, y, z));
				NecrosedSkinBackpackGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		});
		guistate.put("button:imagebutton_crafting_icon", imagebutton_crafting_icon);
		this.addRenderableWidget(imagebutton_crafting_icon);
	}
}
