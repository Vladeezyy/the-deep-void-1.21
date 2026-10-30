package net.sombrius.thedeepvoid.fabric;

import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.api.ModInitializer;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.sombrius.thedeepvoid.port.bus.ServerTickEvent;
import net.sombrius.thedeepvoid.port.bus.ServerChatEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterCommandsEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterCapabilitiesEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterBrewingRecipesEvent;
import net.sombrius.thedeepvoid.port.bus.PlayerWakeUpEvent;
import net.sombrius.thedeepvoid.port.bus.PlayerInteractEvent;
import net.sombrius.thedeepvoid.port.bus.PlayerEvent;
import net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.LevelTickEvent;
import net.sombrius.thedeepvoid.port.bus.LevelEvent;
import net.sombrius.thedeepvoid.port.bus.ItemAttributeModifierEvent;
import net.sombrius.thedeepvoid.port.bus.FurnaceFuelBurnTimeEvent;
import net.sombrius.thedeepvoid.port.bus.FMLConstructModEvent;
import net.sombrius.thedeepvoid.port.bus.FMLCommonSetupEvent;
import net.sombrius.thedeepvoid.port.bus.EntityLeaveLevelEvent;
import net.sombrius.thedeepvoid.port.bus.EntityJoinLevelEvent;
import net.sombrius.thedeepvoid.port.bus.EntityAttributeCreationEvent;
import net.sombrius.thedeepvoid.port.bus.CanPlayerSleepEvent;
import net.sombrius.thedeepvoid.port.bus.BuildCreativeModeTabContentsEvent;
import net.sombrius.thedeepvoid.port.bus.BlockEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

/** Fabric entry point: common init, Fabric API events (and the mixins in .mixin) posted on {@link ModBus} */
public final class TheDeepVoidFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		TheDeepVoidMod.init();
		ModBus.post(new FMLConstructModEvent());
		FabricPlatform.registerAll();
		// 1.21.2: clients get no recipes; the JEI recipe types are synced by their serializers (port.PortRecipeSync)
		for (var type : net.sombrius.thedeepvoid.init.TheDeepVoidModRecipeTypes.SERIALIZERS.getEntries())
			net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization.synchronizeRecipeSerializer(type.get());
		ModBus.post(new EntityAttributeCreationEvent(FabricDefaultAttributeRegistry::register));
		ModBus.post(new RegisterSpawnPlacementsEvent(new RegisterSpawnPlacementsEvent.Sink() {
			@Override
			public <T extends Mob> void register(net.minecraft.world.entity.EntityType<T> type, net.minecraft.world.entity.SpawnPlacementType placement,
					net.minecraft.world.level.levelgen.Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate, RegisterSpawnPlacementsEvent.Operation operation) {
				// the mod only replaces (its own types, which have no placement yet)
				SpawnPlacements.register(type, placement, heightmap, predicate);
			}
		}));
		ModBus.post(new RegisterBrewingRecipesEvent(FabricBrewing.RECIPES::add));
		ModBus.post(new RegisterCapabilitiesEvent());
		FabricBiomeModifiers.register();
		// NeoForge's tags for its poison damage, the soils (canSustainPlant) and the bookshelf (getEnchantPowerBonus)
		net.fabricmc.fabric.api.resource.ResourceManagerHelper.registerBuiltinResourcePack(net.minecraft.resources.Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, "neoforge_compat"),
				net.fabricmc.loader.api.FabricLoader.getInstance().getModContainer(TheDeepVoidMod.MODID).orElseThrow(), net.minecraft.network.chat.Component.literal("The Deep Void: NeoForge data"),
				net.fabricmc.fabric.api.resource.ResourcePackActivationType.ALWAYS_ENABLED);
		ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tab, entries) -> BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab)
				.ifPresent(key -> ModBus.post(new BuildCreativeModeTabContentsEvent(key, entries::accept))));
		DefaultItemComponentEvents.MODIFY.register(context -> {
			ModBus.post(new ModifyDefaultComponentsEvent((item, patch) -> context.modify(item.asItem(), builder -> patch.accept(new ModifyDefaultComponentsEvent.Builder() {
				@Override
				public <T> ModifyDefaultComponentsEvent.Builder set(DataComponentType<T> type, T value) {
					builder.set(type, value);
					return this;
				}

				@Override
				public ModifyDefaultComponentsEvent.Builder remove(DataComponentType<?> type) {
					builder.set(type, null);
					return this;
				}
			}))));
		});
		fuelAndFlammability();
		ModBus.post(new FMLCommonSetupEvent());

		ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricPlatform.server = server);
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> FabricPlatform.server = null);
		ServerTickEvents.START_SERVER_TICK.register(server -> ModBus.post(new ServerTickEvent.Pre(server)));
		ServerTickEvents.END_SERVER_TICK.register(server -> ModBus.post(new ServerTickEvent.Post(server)));
		ServerTickEvents.END_WORLD_TICK.register(level -> ModBus.post(new LevelTickEvent.Post(level)));
		ServerWorldEvents.LOAD.register((server, level) -> ModBus.post(new LevelEvent.Load(level)));
		CommandRegistrationCallback.EVENT.register((dispatcher, context, selection) -> ModBus.post(new RegisterCommandsEvent(dispatcher, selection, context)));
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> ModBus.post(new PlayerEvent.PlayerLoggedInEvent(handler.getPlayer())));
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> ModBus.post(new PlayerEvent.Clone(newPlayer, oldPlayer, !alive)));
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> ModBus.post(new PlayerEvent.PlayerRespawnEvent(newPlayer, alive)));
		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(
				(player, origin, destination) -> ModBus.post(new PlayerEvent.PlayerChangedDimensionEvent(player, origin.dimension(), destination.dimension())));
		EntitySleepEvents.STOP_SLEEPING.register((entity, pos) -> {
			if (entity instanceof Player player)
				ModBus.post(new PlayerWakeUpEvent(player));
		});
		EntitySleepEvents.ALLOW_SLEEPING.register((player, pos) -> {
			CanPlayerSleepEvent event = new CanPlayerSleepEvent(player, pos, null);
			ModBus.post(event);
			return event.getProblem();
		});
		UseBlockCallback.EVENT.register((player, level, hand, hit) -> ModBus.post(new PlayerInteractEvent.RightClickBlock(player, hand, hit.getBlockPos(), hit.getDirection()))
				? InteractionResult.FAIL
				: InteractionResult.PASS);
		UseEntityCallback.EVENT.register((player, level, hand, entity, hit) -> ModBus.post(new PlayerInteractEvent.EntityInteract(player, hand, entity)) ? InteractionResult.FAIL : InteractionResult.PASS);
		AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> ModBus.post(new PlayerInteractEvent.LeftClickBlock(player, pos, direction))
				? InteractionResult.FAIL
				: InteractionResult.PASS);
		PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, blockEntity) -> !ModBus.post(new BlockEvent.BreakEvent(level, pos, state, player)));
		ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> !ModBus.post(new ServerChatEvent(sender, message.signedContent(), message.decoratedContent())));
		ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> ModBus.post(new EntityJoinLevelEvent(entity, level)));
		ServerEntityEvents.ENTITY_UNLOAD.register((entity, level) -> ModBus.post(new EntityLeaveLevelEvent(entity, level)));
	}

	/** NeoForge's FurnaceFuelBurnTimeEvent and IBlockExtension#getFlammability for the mod's items and blocks */
	private static void fuelAndFlammability() {
		// 1.21.2: fuel values are built with the registries (once per data load)
		FuelRegistryEvents.BUILD.register((builder, context) -> {
			for (var entry : TheDeepVoidModItems.REGISTRY.getEntries()) {
				FurnaceFuelBurnTimeEvent event = new FurnaceFuelBurnTimeEvent(new ItemStack(entry.get()), -1);
				if (ModBus.post(event))
					builder.add(entry.get(), event.getBurnTime());
			}
		});
		for (var entry : TheDeepVoidModBlocks.REGISTRY.getEntries()) {
			Block block = entry.get();
			int burn = blockInt(block, "getFlammability");
			int fire = blockInt(block, "getFireSpreadSpeed");
			if (burn > 0 || fire > 0)
				FlammableBlockRegistry.getDefaultInstance().add(block, burn, fire);
		}
	}

	/** A NeoForge-only block method of the mod's block (reflection, once at init), 0 if the block has none */
	private static int blockInt(Block block, String name) {
		try {
			return (int) block.getClass().getMethod(name, net.minecraft.world.level.block.state.BlockState.class, net.minecraft.world.level.BlockGetter.class, BlockPos.class, Direction.class)
					.invoke(block, block.defaultBlockState(), EmptyBlockGetter.INSTANCE, BlockPos.ZERO, Direction.UP);
		} catch (NoSuchMethodException e) {
			return 0;
		} catch (ReflectiveOperationException e) {
			TheDeepVoidMod.LOGGER.warn("{} of {}", name, BuiltInRegistries.BLOCK.getKey(block), e);
			return 0;
		}
	}
}
