package net.sombrius.thedeepvoid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Tuple;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlockEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFeatures;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluidTypes;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluids;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMenus;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;
import net.sombrius.thedeepvoid.init.TheDeepVoidModPotions;
import net.sombrius.thedeepvoid.init.TheDeepVoidModSounds;
import net.sombrius.thedeepvoid.init.TheDeepVoidModTabs;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.world.features.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("the_deep_void")
public class TheDeepVoidMod {
	public static final Logger LOGGER = LogManager.getLogger(TheDeepVoidMod.class);
	public static final String MODID = "the_deep_void";

	public TheDeepVoidMod(IEventBus modEventBus) {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		NeoForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::registerNetworking);
		TheDeepVoidModSounds.REGISTRY.register(modEventBus);
		TheDeepVoidModBlocks.REGISTRY.register(modEventBus);
		TheDeepVoidModBlockEntities.REGISTRY.register(modEventBus);
		TheDeepVoidModItems.REGISTRY.register(modEventBus);
		TheDeepVoidModEntities.REGISTRY.register(modEventBus);
		TheDeepVoidModTabs.REGISTRY.register(modEventBus);
		TheDeepVoidModVariables.ATTACHMENT_TYPES.register(modEventBus);
		TheDeepVoidModFeatures.REGISTRY.register(modEventBus);
		StructureFeature.REGISTRY.register(modEventBus);
		TheDeepVoidModPotions.REGISTRY.register(modEventBus);
		TheDeepVoidModMobEffects.REGISTRY.register(modEventBus);
		TheDeepVoidModMenus.REGISTRY.register(modEventBus);
		TheDeepVoidModParticleTypes.REGISTRY.register(modEventBus);

		TheDeepVoidModFluids.REGISTRY.register(modEventBus);
		TheDeepVoidModFluidTypes.REGISTRY.register(modEventBus);
		// Start of user code block mod init
		// End of user code block mod init
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
	}

	public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(id, new NetworkMessage<>(reader, handler));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(MODID);
		MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler()));
		networkingRegistered = true;
	}

	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new Tuple<>(action, tick));
	}

	@SubscribeEvent
	public void tick(ServerTickEvent.Post event) {
		List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setB(work.getB() - 1);
			if (work.getB() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getA().run());
		workQueue.removeAll(actions);
	}
}
