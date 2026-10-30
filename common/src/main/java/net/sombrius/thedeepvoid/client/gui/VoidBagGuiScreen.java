package net.sombrius.thedeepvoid.client.gui;

import net.minecraft.client.renderer.RenderPipelines;
import java.util.HashMap;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.world.inventory.VoidBagGuiMenu;

public class VoidBagGuiScreen extends AbstractContainerScreen<VoidBagGuiMenu> {
	private final static HashMap<String, Object> guistate = VoidBagGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	public VoidBagGuiScreen(VoidBagGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 176, 166);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	private static final Identifier texture = Identifier.parse("the_deep_void:textures/screens/void_bag_gui.png");

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int gx, int gy, float partialTicks) {
		super.extractBackground(guiGraphics, gx, gy, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
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
		guiGraphics.text(this.font, Component.translatable("gui.the_deep_void.void_bag_gui.label_void_bag"), 6, 4, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
	}
}
