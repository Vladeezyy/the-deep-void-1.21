
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.Registries;

import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.port.platform.Registrar;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class TheDeepVoidModPotions {
	public static final Registrar<Potion> REGISTRY = Registrar.create(Registries.POTION, TheDeepVoidMod.MODID);
	public static final RegistryEntry<Potion, Potion> ROT_POTION = REGISTRY.register("rot_potion", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.ROT.holder(), 460, 0, false, true)));
	public static final RegistryEntry<Potion, Potion> VANISHMENT = REGISTRY.register("vanishment", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.VANISH.holder(), 1200, 0, false, false),
			new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0, false, false), new MobEffectInstance(MobEffects.INVISIBILITY, 1200, 0, false, false)));
	public static final RegistryEntry<Potion, Potion> LONG_VANISHMENT = REGISTRY.register("long_vanishment", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.VANISH.holder(), 2400, 0, false, false),
			new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 0, false, false), new MobEffectInstance(MobEffects.INVISIBILITY, 2400, 0, false, false)));
	public static final RegistryEntry<Potion, Potion> ENHANCED_VANISHMENT = REGISTRY.register("enhanced_vanishment", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.VANISH.holder(), 500, 0, false, false),
			new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 1, false, false), new MobEffectInstance(MobEffects.INVISIBILITY, 500, 0, false, false)));
	public static final RegistryEntry<Potion, Potion> CRATE_MAGNET_POTION = REGISTRY.register("crate_magnet_potion", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CRATE_MAGNET.holder(), 3600, 0, false, true)));
	public static final RegistryEntry<Potion, Potion> EXTENDED_CRATE_MAGNET = REGISTRY.register("extended_crate_magnet", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CRATE_MAGNET.holder(), 9600, 0, false, true)));
	public static final RegistryEntry<Potion, Potion> AMPLIFIED_CRATE_MAGNET = REGISTRY.register("amplified_crate_magnet", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CRATE_MAGNET.holder(), 1800, 1, false, true)));
	public static final RegistryEntry<Potion, Potion> CREATURE_REPELLENT_POTION = REGISTRY.register("creature_repellent_potion", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CREATURE_REPELLENT.holder(), 3600, 0, false, true)));
	public static final RegistryEntry<Potion, Potion> EXTENDED_CREATURE_REPELLENT_POTION = REGISTRY.register("extended_creature_repellent_potion",
			() -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CREATURE_REPELLENT.holder(), 9600, 0, false, true)));
	public static final RegistryEntry<Potion, Potion> BAD_LUCK = REGISTRY.register("bad_luck", () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 0, false, true)));
}
