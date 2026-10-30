package net.sombrius.thedeepvoid.init;


import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.neo.ModConfig;
import net.sombrius.thedeepvoid.port.bus.FMLConstructModEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;

@EventBusSubscriber(modid = TheDeepVoidMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TheDeepVoidModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			DeepVoidConfigConfiguration.SPEC.load(net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.configFile("Deep Void Config.toml"));
		});
	}
}
