package net.sombrius.thedeepvoid.fabric;

import io.netty.buffer.Unpooled;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.BlockPos;

import net.sombrius.thedeepvoid.port.platform.Platform;
import net.sombrius.thedeepvoid.port.neo.IPayloadHandler;
import net.sombrius.thedeepvoid.port.neo.IPayloadContext;
import net.sombrius.thedeepvoid.port.bus.RegisterEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.PortUtil;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluids;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import javax.annotation.Nullable;

import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.nio.file.Path;

/**
 * The Deep Void on Fabric. Entries are queued and registered by {@link #registerAll()} in NeoForge's registry order (each
 * registry after the mod's RegisterEvent listeners for it); the player variables and NeoForge's persistent data are
 * Fabric data attachments.
 */
public final class FabricPlatform implements Platform {
	private record Pending(ResourceLocation id, Supplier<?> factory) {
	}

	/** A packet the client handles (registered by the client entry point) */
	record Clientbound<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, IPayloadHandler<T> handler) {
	}

	private static final Map<ResourceKey<?>, List<Pending>> PENDING = new LinkedHashMap<>();
	static final List<Clientbound<?>> CLIENTBOUND = new ArrayList<>();
	static MinecraftServer server;

	/** NeoForge's getPersistentData(): the entity's / block entity's own tag, saved with it */
	static final AttachmentType<CompoundTag> PERSISTENT_DATA = AttachmentRegistry.<CompoundTag>builder().persistent(CompoundTag.CODEC).initializer(CompoundTag::new)
			.buildAndRegister(ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, "persistent_data"));
	/** The player variables (their item stacks need the registries: the server's, else the client's) */
	static final AttachmentType<TheDeepVoidModVariables.PlayerVariables> PLAYER_VARIABLES = AttachmentRegistry.<TheDeepVoidModVariables.PlayerVariables>builder()
			.persistent(CompoundTag.CODEC.xmap(tag -> {
				TheDeepVoidModVariables.PlayerVariables variables = new TheDeepVoidModVariables.PlayerVariables();
				variables.deserializeNBT(registries(), tag);
				return variables;
			}, variables -> variables.serializeNBT(registries()))).initializer(TheDeepVoidModVariables.PlayerVariables::new).buildAndRegister(TheDeepVoidModVariables.PLAYER_VARIABLES_ID);

	@Nullable
	private static HolderLookup.Provider registries() {
		return server != null ? server.registryAccess() : PortUtil.clientRegistries.get();
	}

	/**
	 * Registers the queued entries: attributes, data components, armor materials (the mod's armor items need theirs), then
	 * vanilla order, then modded registries. The mod's RegisterEvent listeners run for each registry first.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	static void registerAll() {
		Set<ResourceLocation> order = new LinkedHashSet<>();
		order.add(Registries.ATTRIBUTE.location());
		order.add(Registries.DATA_COMPONENT_TYPE.location());
		order.add(Registries.SOUND_EVENT.location());
		order.add(Registries.ARMOR_MATERIAL.location());
		List<ResourceLocation> modded = new ArrayList<>();
		// the registry of registries in registration order (its keySet() has no order)
		for (Registry<?> each : BuiltInRegistries.REGISTRY) {
			ResourceLocation id = each.key().location();
			if (id.getNamespace().equals("minecraft"))
				order.add(id);
			else
				modded.add(id);
		}
		order.addAll(modded);
		for (ResourceLocation registryId : order) {
			ResourceKey<? extends Registry<?>> key = ResourceKey.createRegistryKey(registryId);
			ModBus.post(new RegisterEvent(key, new RegisterEvent.Sink() {
				@Override
				public <T> void register(ResourceKey<? extends Registry<T>> registry, ResourceLocation id, T value) {
					Registry.register((Registry<T>) BuiltInRegistries.REGISTRY.get(registry.location()), id, value);
				}
			}));
			List<Pending> entries = PENDING.remove(key);
			if (entries == null)
				continue;
			Registry registry = BuiltInRegistries.REGISTRY.get(registryId);
			for (Pending entry : entries)
				Registry.register(registry, entry.id(), entry.factory().get());
		}
		if (!PENDING.isEmpty())
			throw new IllegalStateException("Entries for registries that do not exist: " + PENDING.keySet());
	}

	@Override
	public <R, T extends R> void register(ResourceKey<? extends Registry<R>> registry, ResourceLocation id, Supplier<? extends T> factory) {
		PENDING.computeIfAbsent(registry, key -> new ArrayList<>()).add(new Pending(id, factory));
	}

	@Override
	public Path gameDir() {
		return FabricLoader.getInstance().getGameDir();
	}

	@Override
	public Path configDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	@Override
	@Nullable
	public MinecraftServer currentServer() {
		return server;
	}

	@Override
	public CompoundTag persistentData(Entity entity) {
		return entity.getAttachedOrCreate(PERSISTENT_DATA);
	}

	@Override
	public CompoundTag persistentData(BlockEntity blockEntity) {
		return blockEntity.getAttachedOrCreate(PERSISTENT_DATA);
	}

	@Override
	public TheDeepVoidModVariables.PlayerVariables playerVariables(Entity entity) {
		return entity.getAttachedOrCreate(PLAYER_VARIABLES);
	}

	@Override
	public void setPlayerVariables(Entity entity, TheDeepVoidModVariables.PlayerVariables variables) {
		entity.setAttached(PLAYER_VARIABLES, variables);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends CustomPacketPayload> void registerPayload(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		StreamCodec<RegistryFriendlyByteBuf, T> registryCodec = (StreamCodec<RegistryFriendlyByteBuf, T>) codec;
		PayloadTypeRegistry.playS2C().register(type, registryCodec);
		PayloadTypeRegistry.playC2S().register(type, registryCodec);
		// Fabric runs the receivers on the game thread
		ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) -> handler.handle(payload, context(PacketFlow.SERVERBOUND, context.player(),
				reason -> context.player().connection.disconnect(reason))));
		CLIENTBOUND.add(new Clientbound<>(type, handler));
	}

	static IPayloadContext context(PacketFlow flow, Player player, IPayloadContext.Connection connection) {
		return new IPayloadContext() {
			@Override
			public PacketFlow flow() {
				return flow;
			}

			@Override
			public Player player() {
				return player;
			}

			@Override
			public CompletableFuture<Void> enqueueWork(Runnable work) {
				try {
					work.run();
					return CompletableFuture.completedFuture(null);
				} catch (RuntimeException e) {
					return CompletableFuture.failedFuture(e);
				}
			}

			@Override
			public Connection connection() {
				return connection;
			}
		};
	}

	/** Set by the client entry point */
	static Consumer<CustomPacketPayload> toServer = payload -> {
	};

	@Override
	public void sendToServer(CustomPacketPayload payload) {
		toServer.accept(payload);
	}

	@Override
	public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
		ServerPlayNetworking.send(player, payload);
	}

	@Override
	public void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload) {
		for (ServerPlayer player : level.players())
			ServerPlayNetworking.send(player, payload);
	}

	@Override
	public void sendToAllPlayers(CustomPacketPayload payload) {
		if (server != null)
			for (ServerPlayer player : server.getPlayerList().getPlayers())
				ServerPlayNetworking.send(player, payload);
	}

	@Override
	public <M extends AbstractContainerMenu> MenuType<M> menuType(MenuFactory<M> factory) {
		// the extra data travels as bytes and is read back with the client's registries
		return new ExtendedScreenHandlerType<>((int id, Inventory inventory, byte[] data) -> factory.create(id, inventory,
				new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(data), inventory.player.registryAccess())), ByteBufCodecs.BYTE_ARRAY);
	}

	@Override
	public void openMenu(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraData) {
		RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess());
		extraData.accept(buf);
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		player.openMenu(new ExtendedScreenHandlerFactory<byte[]>() {
			@Override
			public byte[] getScreenOpeningData(ServerPlayer opener) {
				return data;
			}

			@Override
			public Component getDisplayName() {
				return provider.getDisplayName();
			}

			@Override
			public AbstractContainerMenu createMenu(int id, Inventory inventory, Player opener) {
				return provider.createMenu(id, inventory, opener);
			}
		});
	}

	@Override
	public ServerPlayer fakePlayer(ServerLevel level) {
		return FakePlayer.get(level);
	}

	@Override
	@Nullable
	public Object fluidType(String name) {
		return null;
	}

	/** What the mod's NeoForge fluid types say: liquid void turns into a source like water, liquid ichor does not */
	@Override
	public boolean fluidConvertsToSource(Fluid fluid) {
		return fluid == TheDeepVoidModFluids.LIQUID_VOID.get() || fluid == TheDeepVoidModFluids.FLOWING_LIQUID_VOID.get();
	}

	@Override
	public boolean onAnimalTame(Mob animal, Player tamer) {
		return false;
	}

	@Override
	public boolean canCropGrow(Level level, BlockPos pos, BlockState state, boolean def) {
		return def;
	}

	@Override
	public void fireCropGrowPost(Level level, BlockPos pos, BlockState state) {
	}

	@Override
	public CreativeModeTab.Builder creativeTabBuilder() {
		return FabricItemGroup.builder();
	}
}
