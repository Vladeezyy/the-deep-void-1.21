
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

import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.MenuType;

public class TheDeepVoidModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, TheDeepVoidMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<VoidBagGuiMenu>> VOID_BAG_GUI = REGISTRY.register("void_bag_gui", () -> IMenuTypeExtension.create(VoidBagGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SoulForgeGUIMenu>> SOUL_FORGE_GUI = REGISTRY.register("soul_forge_gui", () -> IMenuTypeExtension.create(SoulForgeGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<NecrosedSkinBackpackGUIMenu>> NECROSED_SKIN_BACKPACK_GUI = REGISTRY.register("necrosed_skin_backpack_gui", () -> IMenuTypeExtension.create(NecrosedSkinBackpackGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<UndertakerTradeGuiMenu>> UNDERTAKER_TRADE_GUI = REGISTRY.register("undertaker_trade_gui", () -> IMenuTypeExtension.create(UndertakerTradeGuiMenu::new));
}
