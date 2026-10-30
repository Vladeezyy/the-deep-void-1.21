package net.sombrius.thedeepvoid.port.platform;

import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import net.sombrius.thedeepvoid.port.neo.IPayloadHandler;
import java.util.function.Supplier;

/** What the common code needs from the loader; NeoForge and Fabric implement it (found by {@link ServiceLoader}) */
public interface Platform {
	Platform INSTANCE = ServiceLoader.load(Platform.class, Platform.class.getClassLoader()).findFirst().orElseThrow();

	/** Registers {@code factory}'s value under {@code id} (NeoForge: on its register event, Fabric: in registry order at init) */
	<R, T extends R> void register(ResourceKey<? extends Registry<R>> registry, Identifier id, Supplier<? extends T> factory);

	Path gameDir();

	Path configDir();

	@Nullable
	MinecraftServer currentServer();

	/** The entity's own saved tag (NeoForge's {@code getPersistentData()}) */
	CompoundTag persistentData(Entity entity);

	/** The block entity's own saved tag (NeoForge's {@code getPersistentData()}); call {@code setChanged()} after writing */
	CompoundTag persistentData(net.minecraft.world.level.block.entity.BlockEntity blockEntity);

	/** The player variables of an entity (created on first use) */
	TheDeepVoidModVariables.PlayerVariables playerVariables(Entity entity);

	void setPlayerVariables(Entity entity, TheDeepVoidModVariables.PlayerVariables variables);

	/**
	 * A packet both ways (MCreator's {@code playBidirectional}); the handler gets NeoForge's context and checks the flow.
	 * Called during the mod's init, before the loader's networking is set up.
	 */
	<T extends CustomPacketPayload> void registerPayload(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler);

	/** NeoForge's FluidType registered for {@code name} (null on Fabric) */
	@Nullable
	Object fluidType(String name);

	/** What the fluid type of one of the mod's fluids says about source conversion (only Fabric asks) */
	boolean fluidConvertsToSource(net.minecraft.world.level.material.Fluid fluid);

	/** Client to server (client thread) */
	void sendToServer(CustomPacketPayload payload);

	void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

	void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload);

	void sendToAllPlayers(CustomPacketPayload payload);

	/** A menu type whose client side reads extra data written by {@link #openMenu} */
	<M extends AbstractContainerMenu> MenuType<M> menuType(MenuFactory<M> factory);

	void openMenu(ServerPlayer player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> extraData);

	default void openMenu(ServerPlayer player, MenuProvider provider, net.minecraft.core.BlockPos pos) {
		openMenu(player, provider, buf -> buf.writeBlockPos(pos));
	}

	default void openMenu(ServerPlayer player, MenuProvider provider) {
		openMenu(player, provider, buf -> {
		});
	}

	/** NeoForge's AnimalTameEvent: true when a listener stops the taming (Fabric: never) */
	boolean onAnimalTame(net.minecraft.world.entity.Mob animal, net.minecraft.world.entity.player.Player tamer);

	/** NeoForge's crop growth events (Fabric: always grows, nothing posted) */
	boolean canCropGrow(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state, boolean def);

	void fireCropGrowPost(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state);

	/** Where the mod's config files go */
	default java.nio.file.Path configFile(String name) {
		return configDir().resolve(name);
	}

	/** NeoForge's {@code net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.creativeTabBuilder()} (a tab after the vanilla ones; Fabric: FabricItemGroup.builder()) */
	net.minecraft.world.item.CreativeModeTab.Builder creativeTabBuilder();

	/** A fake player of the level for commands run without an entity */
	ServerPlayer fakePlayer(ServerLevel level);

	interface MenuFactory<M extends AbstractContainerMenu> {
		M create(int id, Inventory inventory, @Nullable FriendlyByteBuf extraData);
	}
}
