
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.Registries;

import net.sombrius.thedeepvoid.world.inventory.VoidBagGuiMenu;
import net.sombrius.thedeepvoid.world.inventory.UndertakerTradeGuiMenu;
import net.sombrius.thedeepvoid.world.inventory.SoulForgeGUIMenu;
import net.sombrius.thedeepvoid.world.inventory.NecrosedSkinBackpackGUIMenu;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.port.platform.Registrar;

import net.minecraft.world.inventory.MenuType;

public class TheDeepVoidModMenus {
	public static final Registrar<MenuType<?>> REGISTRY = Registrar.create(Registries.MENU, TheDeepVoidMod.MODID);
	public static final RegistryEntry<MenuType<?>, MenuType<VoidBagGuiMenu>> VOID_BAG_GUI = REGISTRY.register("void_bag_gui", () -> net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.menuType(VoidBagGuiMenu::new));
	public static final RegistryEntry<MenuType<?>, MenuType<SoulForgeGUIMenu>> SOUL_FORGE_GUI = REGISTRY.register("soul_forge_gui", () -> net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.menuType(SoulForgeGUIMenu::new));
	public static final RegistryEntry<MenuType<?>, MenuType<NecrosedSkinBackpackGUIMenu>> NECROSED_SKIN_BACKPACK_GUI = REGISTRY.register("necrosed_skin_backpack_gui", () -> net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.menuType(NecrosedSkinBackpackGUIMenu::new));
	public static final RegistryEntry<MenuType<?>, MenuType<UndertakerTradeGuiMenu>> UNDERTAKER_TRADE_GUI = REGISTRY.register("undertaker_trade_gui", () -> net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.menuType(UndertakerTradeGuiMenu::new));
}
