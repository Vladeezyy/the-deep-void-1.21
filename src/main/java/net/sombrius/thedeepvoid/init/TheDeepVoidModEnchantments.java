
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.Registries;

import net.sombrius.thedeepvoid.enchantment.VoidVisionEnchantment;
import net.sombrius.thedeepvoid.enchantment.ScourgeOfFleshEnchantment;
import net.sombrius.thedeepvoid.enchantment.RadianceEnchantment;
import net.sombrius.thedeepvoid.enchantment.QuickTriggerEnchantment;
import net.sombrius.thedeepvoid.enchantment.NecrosedEnchantment;
import net.sombrius.thedeepvoid.enchantment.MarksmanEnchantment;
import net.sombrius.thedeepvoid.enchantment.DimensionalBurstEnchantment;
import net.sombrius.thedeepvoid.enchantment.CurseOfSorrowEnchantment;
import net.sombrius.thedeepvoid.enchantment.CurseOfGodsBloodEnchantment;
import net.sombrius.thedeepvoid.enchantment.CrateCatcherEnchantment;
import net.sombrius.thedeepvoid.enchantment.ConservationEnchantment;
import net.sombrius.thedeepvoid.enchantment.BoneWalkerEnchantment;
import net.sombrius.thedeepvoid.enchantment.BaitEnchantment;
import net.sombrius.thedeepvoid.enchantment.AkimboEnchantment;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

public class TheDeepVoidModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(Registries.ENCHANTMENT, TheDeepVoidMod.MODID);
	public static final DeferredHolder<Enchantment, Enchantment> VOID_VISION = REGISTRY.register("void_vision", () -> new VoidVisionEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> NECROSED = REGISTRY.register("necrosed", () -> new NecrosedEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> SCOURGE_OF_FLESH = REGISTRY.register("scourge_of_flesh", () -> new ScourgeOfFleshEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> BONE_WALKER = REGISTRY.register("bone_walker", () -> new BoneWalkerEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> CURSE_OF_SORROW = REGISTRY.register("curse_of_sorrow", () -> new CurseOfSorrowEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> CRATE_CATCHER = REGISTRY.register("crate_catcher", () -> new CrateCatcherEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> CURSE_OF_GODS_BLOOD = REGISTRY.register("curse_of_gods_blood", () -> new CurseOfGodsBloodEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> BAIT = REGISTRY.register("bait", () -> new BaitEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> RADIANCE = REGISTRY.register("radiance", () -> new RadianceEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> CONSERVATION = REGISTRY.register("conservation", () -> new ConservationEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> DIMENSIONAL_BURST = REGISTRY.register("dimensional_burst", () -> new DimensionalBurstEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> QUICK_TRIGGER = REGISTRY.register("quick_trigger", () -> new QuickTriggerEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> MARKSMAN = REGISTRY.register("marksman", () -> new MarksmanEnchantment());
	public static final DeferredHolder<Enchantment, Enchantment> AKIMBO = REGISTRY.register("akimbo", () -> new AkimboEnchantment());
}
