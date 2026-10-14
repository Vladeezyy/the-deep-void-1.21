package net.sombrius.thedeepvoid.port;

import net.neoforged.neoforge.common.NeoForge;

import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.item.SoulWingsItem;
import net.sombrius.thedeepvoid.item.SoulWingsFlyItem;
import net.sombrius.thedeepvoid.item.FlyWingsItem;
import net.sombrius.thedeepvoid.item.FlyWingsFlyItem;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluidTypes;
import net.sombrius.thedeepvoid.fluid.types.LiquidIchorFluidType;
import net.sombrius.thedeepvoid.fluid.types.LiquidVoidFluidType;
import net.sombrius.thedeepvoid.item.ApostasyItem;
import net.sombrius.thedeepvoid.item.DoubleBarrelShotgunItem;
import net.sombrius.thedeepvoid.item.EldritchMawItem;
import net.sombrius.thedeepvoid.item.HeresyItem;
import net.sombrius.thedeepvoid.item.HuntersItem;
import net.sombrius.thedeepvoid.item.MisanthropyItem;
import net.sombrius.thedeepvoid.item.PerilItem;
import net.sombrius.thedeepvoid.item.PerilOffHandItem;
import net.sombrius.thedeepvoid.item.PlagueDoctorItem;
import net.sombrius.thedeepvoid.item.VultureItem;
import net.sombrius.thedeepvoid.potion.ComboMobEffect;
import net.sombrius.thedeepvoid.potion.DistractedMobEffect;
import net.sombrius.thedeepvoid.potion.LookingMobEffect;
import net.sombrius.thedeepvoid.potion.LurkerHeadNearMobEffect;
import net.sombrius.thedeepvoid.potion.ParanoiaMobEffect;
import net.sombrius.thedeepvoid.potion.PitShakeMobEffect;
import net.sombrius.thedeepvoid.potion.PowerfulShotMobEffect;
import net.sombrius.thedeepvoid.potion.RootBlindMobEffect;
import net.sombrius.thedeepvoid.potion.StareMobEffect;
import net.sombrius.thedeepvoid.potion.TotalBlindnessMobEffect;
import net.sombrius.thedeepvoid.potion.TrembleMobEffect;

/** 1.21 NeoForge: client extensions are registered by event instead of the removed initializeClient(Consumer). */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientExtensionsRegistration {
	@SubscribeEvent
	public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		event.registerItem(((HeresyItem) TheDeepVoidModItems.HERESY.get()).clientExtensions(), TheDeepVoidModItems.HERESY.get());
		event.registerItem(((HuntersItem.Helmet) TheDeepVoidModItems.HUNTERS_HELMET.get()).clientExtensions(), TheDeepVoidModItems.HUNTERS_HELMET.get());
		event.registerItem(((ApostasyItem) TheDeepVoidModItems.APOSTASY.get()).clientExtensions(), TheDeepVoidModItems.APOSTASY.get());
		event.registerItem(((PlagueDoctorItem.Helmet) TheDeepVoidModItems.PLAGUE_DOCTOR_HELMET.get()).clientExtensions(), TheDeepVoidModItems.PLAGUE_DOCTOR_HELMET.get());
		event.registerItem(((MisanthropyItem) TheDeepVoidModItems.MISANTHROPY.get()).clientExtensions(), TheDeepVoidModItems.MISANTHROPY.get());
		event.registerItem(((EldritchMawItem) TheDeepVoidModItems.ELDRITCH_MAW.get()).clientExtensions(), TheDeepVoidModItems.ELDRITCH_MAW.get());
		event.registerItem(((PerilItem) TheDeepVoidModItems.PERIL.get()).clientExtensions(), TheDeepVoidModItems.PERIL.get());
		event.registerItem(((DoubleBarrelShotgunItem) TheDeepVoidModItems.DOUBLE_BARREL_SHOTGUN.get()).clientExtensions(), TheDeepVoidModItems.DOUBLE_BARREL_SHOTGUN.get());
		event.registerItem(((VultureItem.Helmet) TheDeepVoidModItems.VULTURE_HELMET.get()).clientExtensions(), TheDeepVoidModItems.VULTURE_HELMET.get());
		event.registerItem(((VultureItem.Chestplate) TheDeepVoidModItems.VULTURE_CHESTPLATE.get()).clientExtensions(), TheDeepVoidModItems.VULTURE_CHESTPLATE.get());
		event.registerItem(((PerilOffHandItem) TheDeepVoidModItems.PERIL_OFF_HAND.get()).clientExtensions(), TheDeepVoidModItems.PERIL_OFF_HAND.get());
		event.registerMobEffect(((ComboMobEffect) TheDeepVoidModMobEffects.COMBO.value()).clientExtensions(), TheDeepVoidModMobEffects.COMBO.value());
		event.registerMobEffect(((StareMobEffect) TheDeepVoidModMobEffects.STARE.value()).clientExtensions(), TheDeepVoidModMobEffects.STARE.value());
		event.registerMobEffect(((TrembleMobEffect) TheDeepVoidModMobEffects.TREMBLE.value()).clientExtensions(), TheDeepVoidModMobEffects.TREMBLE.value());
		event.registerMobEffect(((ParanoiaMobEffect) TheDeepVoidModMobEffects.PARANOIA.value()).clientExtensions(), TheDeepVoidModMobEffects.PARANOIA.value());
		event.registerMobEffect(((LookingMobEffect) TheDeepVoidModMobEffects.LOOKING.value()).clientExtensions(), TheDeepVoidModMobEffects.LOOKING.value());
		event.registerMobEffect(((RootBlindMobEffect) TheDeepVoidModMobEffects.ROOT_BLIND.value()).clientExtensions(), TheDeepVoidModMobEffects.ROOT_BLIND.value());
		event.registerMobEffect(((LurkerHeadNearMobEffect) TheDeepVoidModMobEffects.LURKER_HEAD_NEAR.value()).clientExtensions(), TheDeepVoidModMobEffects.LURKER_HEAD_NEAR.value());
		event.registerMobEffect(((DistractedMobEffect) TheDeepVoidModMobEffects.DISTRACTED.value()).clientExtensions(), TheDeepVoidModMobEffects.DISTRACTED.value());
		event.registerMobEffect(((PowerfulShotMobEffect) TheDeepVoidModMobEffects.POWERFUL_SHOT.value()).clientExtensions(), TheDeepVoidModMobEffects.POWERFUL_SHOT.value());
		event.registerMobEffect(((TotalBlindnessMobEffect) TheDeepVoidModMobEffects.TOTAL_BLINDNESS.value()).clientExtensions(), TheDeepVoidModMobEffects.TOTAL_BLINDNESS.value());
		event.registerMobEffect(((PitShakeMobEffect) TheDeepVoidModMobEffects.PIT_SHAKE.value()).clientExtensions(), TheDeepVoidModMobEffects.PIT_SHAKE.value());
		event.registerFluidType(((LiquidVoidFluidType) TheDeepVoidModFluidTypes.LIQUID_VOID_TYPE.get()).clientExtensions(), TheDeepVoidModFluidTypes.LIQUID_VOID_TYPE.get());
		event.registerFluidType(((LiquidIchorFluidType) TheDeepVoidModFluidTypes.LIQUID_ICHOR_TYPE.get()).clientExtensions(), TheDeepVoidModFluidTypes.LIQUID_ICHOR_TYPE.get());
		event.registerItem(TheDeepVoidModItems.SOUL_WINGS_CHESTPLATE.get().clientExtensions(), TheDeepVoidModItems.SOUL_WINGS_CHESTPLATE.get());
		event.registerItem(TheDeepVoidModItems.SOUL_WINGS_FLY_CHESTPLATE.get().clientExtensions(), TheDeepVoidModItems.SOUL_WINGS_FLY_CHESTPLATE.get());
		event.registerItem(TheDeepVoidModItems.FLY_WINGS_CHESTPLATE.get().clientExtensions(), TheDeepVoidModItems.FLY_WINGS_CHESTPLATE.get());
		event.registerItem(TheDeepVoidModItems.FLY_WINGS_FLY_CHESTPLATE.get().clientExtensions(), TheDeepVoidModItems.FLY_WINGS_FLY_CHESTPLATE.get());
	}
}
