package net.sombrius.thedeepvoid.init;

import net.neoforged.fml.ModList;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;

@EventBusSubscriber(modid = TheDeepVoidMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TheDeepVoidModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModList.get().getModContainerById(TheDeepVoidMod.MODID).ifPresent(container -> container.registerConfig(ModConfig.Type.COMMON, DeepVoidConfigConfiguration.SPEC, "Deep Void Config.toml"));
		});
	}
}
