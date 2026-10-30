package net.sombrius.thedeepvoid.neoforge;

import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.BlockPos;

import net.sombrius.thedeepvoid.port.platform.Platform;
import net.sombrius.thedeepvoid.port.neo.IPayloadHandler;
import net.sombrius.thedeepvoid.port.neo.IPayloadContext;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluidTypes;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import javax.annotation.Nullable;

import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.nio.file.Path;

/** The Deep Void on NeoForge: the NeoForge APIs behind the common code */
public final class NeoForgePlatform implements Platform {
	/** Deferred registers per registry, filled by the common code before {@link #registerAll} */
	private static final Map<ResourceKey<?>, DeferredRegister<?>> REGISTERS = new LinkedHashMap<>();
	private static final List<Consumer<PayloadRegistrar>> PAYLOADS = new ArrayList<>();

	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, TheDeepVoidMod.MODID);
	static final Supplier<AttachmentType<TheDeepVoidModVariables.PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register(TheDeepVoidModVariables.PLAYER_VARIABLES_ID.getPath(),
			() -> AttachmentType.builder(TheDeepVoidModVariables.PlayerVariables::new).serialize(new IAttachmentSerializer<TheDeepVoidModVariables.PlayerVariables>() {
				// 21.9+: attachments save to ValueOutput; the variables keep their compound under "data"
				@Override
				public TheDeepVoidModVariables.PlayerVariables read(IAttachmentHolder holder, ValueInput input) {
					TheDeepVoidModVariables.PlayerVariables variables = new TheDeepVoidModVariables.PlayerVariables();
					input.read("data", CompoundTag.CODEC).ifPresent(tag -> variables.deserializeNBT(registries(holder), tag));
					return variables;
				}

				@Override
				public boolean write(TheDeepVoidModVariables.PlayerVariables variables, ValueOutput output) {
					output.store("data", CompoundTag.CODEC, variables.serializeNBT(registries(null)));
					return true;
				}
			}).build());

	private static HolderLookup.Provider registries(IAttachmentHolder holder) {
		if (holder instanceof net.minecraft.world.entity.Entity entity)
			return entity.registryAccess();
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		return server != null ? server.registryAccess() : net.minecraft.core.RegistryAccess.EMPTY;
	}

	static void registerAll(IEventBus modBus) {
		REGISTERS.values().forEach(register -> register.register(modBus));
		ATTACHMENT_TYPES.register(modBus);
		TheDeepVoidModFluidTypes.REGISTRY.register(modBus);
		modBus.addListener((RegisterPayloadHandlersEvent event) -> {
			PayloadRegistrar registrar = event.registrar(TheDeepVoidMod.MODID);
			PAYLOADS.forEach(payload -> payload.accept(registrar));
		});
	}

	@SuppressWarnings("unchecked")
	private static <R> DeferredRegister<R> registerFor(ResourceKey<? extends Registry<R>> registry) {
		return (DeferredRegister<R>) REGISTERS.computeIfAbsent(registry, key -> DeferredRegister.create((ResourceKey<? extends Registry<R>>) key, TheDeepVoidMod.MODID));
	}

	@Override
	public <R, T extends R> void register(ResourceKey<? extends Registry<R>> registry, Identifier id, Supplier<? extends T> factory) {
		registerFor(registry).register(id.getPath(), factory);
	}

	@Override
	public Path gameDir() {
		return FMLPaths.GAMEDIR.get();
	}

	@Override
	public Path configDir() {
		return FMLPaths.CONFIGDIR.get();
	}

	@Override
	@Nullable
	public MinecraftServer currentServer() {
		return ServerLifecycleHooks.getCurrentServer();
	}

	@Override
	public CompoundTag persistentData(Entity entity) {
		return entity.getPersistentData();
	}

	@Override
	public CompoundTag persistentData(net.minecraft.world.level.block.entity.BlockEntity blockEntity) {
		return blockEntity.getPersistentData();
	}

	@Override
	public TheDeepVoidModVariables.PlayerVariables playerVariables(Entity entity) {
		return entity.getData(PLAYER_VARIABLES);
	}

	@Override
	public void setPlayerVariables(Entity entity, TheDeepVoidModVariables.PlayerVariables variables) {
		entity.setData(PLAYER_VARIABLES, variables);
	}

	@Override
	public <T extends CustomPacketPayload> void registerPayload(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		PAYLOADS.add(registrar -> registrar.playBidirectional(type, codec, (payload, context) -> handler.handle(payload, new IPayloadContext() {
			@Override
			public PacketFlow flow() {
				return context.flow();
			}

			@Override
			public Player player() {
				return context.player();
			}

			@Override
			public CompletableFuture<Void> enqueueWork(Runnable work) {
				return context.enqueueWork(work);
			}

			@Override
			public Connection connection() {
				return reason -> context.disconnect(reason);
			}
		})));
	}

	@Override
	public void sendToServer(CustomPacketPayload payload) {
		net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(payload);
	}

	@Override
	public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
		PacketDistributor.sendToPlayer(player, payload);
	}

	@Override
	public void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload) {
		PacketDistributor.sendToPlayersInDimension(level, payload);
	}

	@Override
	public void sendToAllPlayers(CustomPacketPayload payload) {
		PacketDistributor.sendToAllPlayers(payload);
	}

	@Override
	public <M extends AbstractContainerMenu> MenuType<M> menuType(MenuFactory<M> factory) {
		return IMenuTypeExtension.create(factory::create);
	}

	@Override
	public void openMenu(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraData) {
		player.openMenu(provider, extraData);
	}

	@Override
	public ServerPlayer fakePlayer(ServerLevel level) {
		return FakePlayerFactory.getMinecraft(level);
	}

	@Override
	@Nullable
	public Object fluidType(String name) {
		for (var entry : TheDeepVoidModFluidTypes.REGISTRY.getEntries()) {
			if (entry.getId().getPath().equals(name))
				return entry.get();
		}
		return null;
	}

	@Override
	public boolean fluidConvertsToSource(Fluid fluid) {
		return fluid instanceof net.sombrius.thedeepvoid.port.neo.BaseFlowingFluid base && base.fluidTypeObject() instanceof FluidType type && type.canConvertToSource(fluid.defaultFluidState(), null, BlockPos.ZERO);
	}

	@Override
	public boolean onAnimalTame(Mob animal, Player tamer) {
		return animal instanceof Animal a && EventHooks.onAnimalTame(a, tamer);
	}

	@Override
	public boolean canCropGrow(Level level, BlockPos pos, BlockState state, boolean def) {
		return CommonHooks.canCropGrow(level, pos, state, def);
	}

	@Override
	public void fireCropGrowPost(Level level, BlockPos pos, BlockState state) {
		CommonHooks.fireCropGrowPost(level, pos, state);
	}

	@Override
	public CreativeModeTab.Builder creativeTabBuilder() {
		return CreativeModeTab.builder();
	}
}
