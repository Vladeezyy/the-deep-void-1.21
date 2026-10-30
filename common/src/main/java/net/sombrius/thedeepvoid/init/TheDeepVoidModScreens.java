
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.sombrius.thedeepvoid.port.bus.RegisterMenuScreensEvent;

import net.sombrius.thedeepvoid.client.gui.VoidBagGuiScreen;
import net.sombrius.thedeepvoid.client.gui.UndertakerTradeGuiScreen;
import net.sombrius.thedeepvoid.client.gui.SoulForgeGUIScreen;
import net.sombrius.thedeepvoid.client.gui.NecrosedSkinBackpackGUIScreen;

import net.sombrius.thedeepvoid.port.bus.FMLClientSetupEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TheDeepVoidModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(TheDeepVoidModMenus.VOID_BAG_GUI.get(), VoidBagGuiScreen::new);
		event.register(TheDeepVoidModMenus.SOUL_FORGE_GUI.get(), SoulForgeGUIScreen::new);
		event.register(TheDeepVoidModMenus.NECROSED_SKIN_BACKPACK_GUI.get(), NecrosedSkinBackpackGUIScreen::new);
		event.register(TheDeepVoidModMenus.UNDERTAKER_TRADE_GUI.get(), UndertakerTradeGuiScreen::new);
	}
}
