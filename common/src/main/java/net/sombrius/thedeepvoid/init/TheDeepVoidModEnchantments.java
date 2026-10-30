/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.sombrius.thedeepvoid.TheDeepVoidMod;

/** 1.21: enchantments are data driven (data/the_deep_void/enchantment); the code refers to them by key. */
public class TheDeepVoidModEnchantments {
	public static final ResourceKey<Enchantment> AKIMBO = key("akimbo");
	public static final ResourceKey<Enchantment> BAIT = key("bait");
	public static final ResourceKey<Enchantment> BONE_WALKER = key("bone_walker");
	public static final ResourceKey<Enchantment> CONSERVATION = key("conservation");
	public static final ResourceKey<Enchantment> CRATE_CATCHER = key("crate_catcher");
	public static final ResourceKey<Enchantment> CURSE_OF_GODS_BLOOD = key("curse_of_gods_blood");
	public static final ResourceKey<Enchantment> CURSE_OF_SORROW = key("curse_of_sorrow");
	public static final ResourceKey<Enchantment> DIMENSIONAL_BURST = key("dimensional_burst");
	public static final ResourceKey<Enchantment> MARKSMAN = key("marksman");
	public static final ResourceKey<Enchantment> NECROSED = key("necrosed");
	public static final ResourceKey<Enchantment> QUICK_TRIGGER = key("quick_trigger");
	public static final ResourceKey<Enchantment> RADIANCE = key("radiance");
	public static final ResourceKey<Enchantment> SCOURGE_OF_FLESH = key("scourge_of_flesh");
	public static final ResourceKey<Enchantment> VOID_VISION = key("void_vision");

	private static ResourceKey<Enchantment> key(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, name));
	}
}
