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
import net.sombrius.thedeepvoid.world.inventory.SoulForgeGUIMenu;

public class SoulForgeGUIScreen extends AbstractContainerScreen<SoulForgeGUIMenu> {
	private final static HashMap<String, Object> guistate = SoulForgeGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	public SoulForgeGUIScreen(SoulForgeGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 176, 166);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	private static final Identifier texture = Identifier.parse("the_deep_void:textures/screens/soul_forge_gui.png");

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int gx, int gy, float partialTicks) {
		super.extractBackground(guiGraphics, gx, gy, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/plus.png"), this.leftPos + 49, this.topPos + 45, 0, 0, 13, 13, 13, 13);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/arrow.png"), this.leftPos + 93, this.topPos + 40, 0, 0, 22, 22, 22, 22);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/soul_hammer.png"), this.leftPos + 15, this.topPos + 7, 0, 0, 30, 30, 30, 30);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/soul_shard_icon.png"), this.leftPos + 25, this.topPos + 44, 0, 0, 16, 16, 16, 16);

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.parse("the_deep_void:textures/screens/netherite_ingot_icon.png"), this.leftPos + 70, this.topPos + 44, 0, 0, 16, 16, 16, 16);

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
		guiGraphics.text(this.font, Component.translatable("gui.the_deep_void.soul_forge_gui.label_soul_forge"), 61, 7, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
	}
}
