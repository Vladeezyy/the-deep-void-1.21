
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.Registries;

import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class TheDeepVoidModPotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(Registries.POTION, TheDeepVoidMod.MODID);
	public static final DeferredHolder<Potion, Potion> ROT_POTION = REGISTRY.register("rot_potion", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.ROT, 460, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> VANISHMENT = REGISTRY.register("vanishment", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.VANISH, 1200, 0, false, false),
			new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0, false, false), new MobEffectInstance(MobEffects.INVISIBILITY, 1200, 0, false, false)));
	public static final DeferredHolder<Potion, Potion> LONG_VANISHMENT = REGISTRY.register("long_vanishment", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.VANISH, 2400, 0, false, false),
			new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 0, false, false), new MobEffectInstance(MobEffects.INVISIBILITY, 2400, 0, false, false)));
	public static final DeferredHolder<Potion, Potion> ENHANCED_VANISHMENT = REGISTRY.register("enhanced_vanishment", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.VANISH, 500, 0, false, false),
			new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 1, false, false), new MobEffectInstance(MobEffects.INVISIBILITY, 500, 0, false, false)));
	public static final DeferredHolder<Potion, Potion> CRATE_MAGNET_POTION = REGISTRY.register("crate_magnet_potion", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CRATE_MAGNET, 3600, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> EXTENDED_CRATE_MAGNET = REGISTRY.register("extended_crate_magnet", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CRATE_MAGNET, 9600, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> AMPLIFIED_CRATE_MAGNET = REGISTRY.register("amplified_crate_magnet", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CRATE_MAGNET, 1800, 1, false, true)));
	public static final DeferredHolder<Potion, Potion> CREATURE_REPELLENT_POTION = REGISTRY.register("creature_repellent_potion", () -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CREATURE_REPELLENT, 3600, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> EXTENDED_CREATURE_REPELLENT_POTION = REGISTRY.register("extended_creature_repellent_potion",
			() -> new Potion(new MobEffectInstance(TheDeepVoidModMobEffects.CREATURE_REPELLENT, 9600, 0, false, true)));
	public static final DeferredHolder<Potion, Potion> BAD_LUCK = REGISTRY.register("bad_luck", () -> new Potion(new MobEffectInstance(MobEffects.UNLUCK, 6000, 0, false, true)));
}
