package net.sombrius.thedeepvoid;

import net.sombrius.thedeepvoid.world.features.StructureFeature;
import net.sombrius.thedeepvoid.port.platform.Platform;
import net.sombrius.thedeepvoid.port.neo.IPayloadHandler;
import net.sombrius.thedeepvoid.port.bus.ServerTickEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.EventPriority;
import net.sombrius.thedeepvoid.port.bus.EventIndex;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.init.TheDeepVoidModTabs;
import net.sombrius.thedeepvoid.init.TheDeepVoidModGameRules;
import net.sombrius.thedeepvoid.init.TheDeepVoidModSounds;
import net.sombrius.thedeepvoid.init.TheDeepVoidModRecipeTypes;
import net.sombrius.thedeepvoid.init.TheDeepVoidModPotions;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMenus;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluids;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFeatures;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlockEntities;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraft.server.MinecraftServer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

/** The mod's common init; the NeoForge and Fabric entry points call {@link #init()} first (MCreator's mod constructor) */
public class TheDeepVoidMod {
	public static final Logger LOGGER = LogManager.getLogger(TheDeepVoidMod.class);
	public static final String MODID = "the_deep_void";

	private static boolean initialized;

	public static void init() {
		if (initialized)
			return;
		initialized = true;
		// the registry classes queue their entries (Registrar) when loaded, in MCreator's order
		TheDeepVoidModSounds.REGISTRY.getRegistryKey();
		TheDeepVoidModBlocks.REGISTRY.getRegistryKey();
		TheDeepVoidModBlockEntities.REGISTRY.getRegistryKey();
		TheDeepVoidModItems.REGISTRY.getRegistryKey();
		TheDeepVoidModEntities.REGISTRY.getRegistryKey();
		TheDeepVoidModTabs.REGISTRY.getRegistryKey();
		TheDeepVoidModFeatures.REGISTRY.getRegistryKey();
		StructureFeature.REGISTRY.getRegistryKey();
		TheDeepVoidModPotions.REGISTRY.getRegistryKey();
		TheDeepVoidModMobEffects.REGISTRY.getRegistryKey();
		TheDeepVoidModMenus.REGISTRY.getRegistryKey();
		TheDeepVoidModParticleTypes.REGISTRY.getRegistryKey();
		TheDeepVoidModFluids.REGISTRY.getRegistryKey();
		TheDeepVoidModRecipeTypes.SERIALIZERS.getRegistryKey();
		TheDeepVoidModRecipeTypes.TYPES.getRegistryKey();
		TheDeepVoidModGameRules.REGISTRY.getRegistryKey();
		TheDeepVoidModVariables.registerPackets();
		// MCreator registered the key and GUI messages on FMLCommonSetupEvent; payloads are registered here, before the
		// loaders' payload registration (1.99.3.1.1 missed them: every key press and these GUI buttons crashed)
		net.sombrius.thedeepvoid.network.ArmorAbilityMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.ArmorToggleMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.MotherCallOutMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.MountDashMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.MountDownMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.MountFlyMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.MountUpMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.NecrosedSkinBackpackGUIButtonMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.SoulForgeGUISlotMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.UndertakerTradeGuiButtonMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.UndertakerTradeGuiSlotMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.WeaverBootsNoGravityMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.WingsBackwardMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.WingsDownMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.WingsFowardMessage.registerMessage(null);
		net.sombrius.thedeepvoid.network.WingsUpMessage.registerMessage(null);
		EventIndex.register();
		ModBus.add(ServerTickEvent.Post.class, EventPriority.NORMAL, TheDeepVoidMod::tick);
	}

	/** MCreator's bidirectional packets (the handlers check the flow); call during {@link #init()} */
	@SuppressWarnings("unchecked")
	public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		Platform.INSTANCE.registerPayload(id, (StreamCodec<? super RegistryFriendlyByteBuf, T>) reader, handler);
	}

	/** A queued action and its ticks left (26.2 removed net.minecraft.util.Tuple, MCreator's pair) */
	private static final class Work {
		final Runnable action;
		int ticks;

		Work(Runnable action, int ticks) {
			this.action = action;
			this.ticks = ticks;
		}
	}

	private static final Collection<Work> workQueue = new ConcurrentLinkedQueue<>();

	/** Runs {@code action} after {@code tick} server ticks (only when called on the server thread, as in MCreator) */
	public static void queueServerWork(int tick, Runnable action) {
		MinecraftServer server = Platform.INSTANCE.currentServer();
		if (server != null && server.isSameThread())
			workQueue.add(new Work(action, tick));
	}

	private static void tick(ServerTickEvent.Post event) {
		List<Work> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.ticks--;
			if (work.ticks == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.action.run());
		workQueue.removeAll(actions);
	}
}
