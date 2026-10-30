package net.sombrius.thedeepvoid.neoforge;

import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.core.component.DataComponentType;

import net.sombrius.thedeepvoid.port.neo.PortCaps;
import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

/** NeoForge entry point: common init, then NeoForge's events posted on {@link ModBus} */
@Mod(TheDeepVoidMod.MODID)
public final class TheDeepVoidNeoForge {
	public TheDeepVoidNeoForge(IEventBus modBus) {
		TheDeepVoidMod.init();
		ModBus.post(new net.sombrius.thedeepvoid.port.bus.FMLConstructModEvent());
		NeoForgePlatform.registerAll(modBus);

		modBus.addListener((RegisterEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterEvent(event.getRegistryKey(),
				new net.sombrius.thedeepvoid.port.bus.RegisterEvent.Sink() {
					@Override
					public <T> void register(net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<T>> registry, net.minecraft.resources.Identifier id, T value) {
						event.register(registry, id, () -> value);
					}
				})));
		modBus.addListener((FMLCommonSetupEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.FMLCommonSetupEvent()));
		modBus.addListener((EntityAttributeCreationEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.EntityAttributeCreationEvent(event::put)));
		modBus.addListener((RegisterSpawnPlacementsEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent(
				new net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent.Sink() {
					@Override
					public <T extends Mob> void register(EntityType<T> type, SpawnPlacementType placement, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate,
							net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent.Operation operation) {
						event.register(type, placement, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.valueOf(operation.name()));
					}
				})));
		modBus.addListener((BuildCreativeModeTabContentsEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.BuildCreativeModeTabContentsEvent(event.getTabKey(), event::accept)));
		modBus.addListener((ModifyDefaultComponentsEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent((item, patch) -> event.modify(item,
				builder -> patch.accept(new net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent.Builder() {
					@Override
					public <T> net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent.Builder set(DataComponentType<T> type, T value) {
						builder.set(type, value);
						return this;
					}

					@Override
					public net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent.Builder remove(DataComponentType<?> type) {
						builder.remove(type);
						return this;
					}
				})))));
		modBus.addListener(TheDeepVoidNeoForge::registerCapabilities);

		IEventBus bus = NeoForge.EVENT_BUS;
		bus.addListener((ServerTickEvent.Pre event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ServerTickEvent.Pre(event.getServer())));
		bus.addListener((ServerTickEvent.Post event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ServerTickEvent.Post(event.getServer())));
		bus.addListener((LevelTickEvent.Post event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.LevelTickEvent.Post(event.getLevel())));
		bus.addListener((LevelEvent.Load event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.LevelEvent.Load(event.getLevel())));
		bus.addListener((PlayerTickEvent.Post event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerTickEvent.Post(event.getEntity())));
		bus.addListener((EntityTickEvent.Pre event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.EntityTickEvent.Pre(event.getEntity())));
		bus.addListener((RegisterCommandsEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterCommandsEvent(event.getDispatcher(), event.getCommandSelection(), event.getBuildContext())));
		bus.addListener((RegisterBrewingRecipesEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterBrewingRecipesEvent(recipe -> event.getBuilder().addRecipe(new IBrewingRecipe() {
			@Override
			public boolean isInput(ItemStack input) {
				return recipe.isInput(input);
			}

			@Override
			public boolean isIngredient(ItemStack ingredient) {
				return recipe.isIngredient(ingredient);
			}

			@Override
			public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
				return recipe.getOutput(input, ingredient);
			}
		}))));
		bus.addListener((LivingIncomingDamageEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent(event.getEntity(), event.getSource(), event.getAmount());
			if (ModBus.post(ours))
				event.setCanceled(true);
			else if (ours.getAmount() != event.getAmount())
				event.setAmount(ours.getAmount());
		});
		bus.addListener((LivingDeathEvent event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.LivingDeathEvent(event.getEntity(), event.getSource())))
				event.setCanceled(true);
		});
		bus.addListener((LivingFallEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.LivingFallEvent(event.getEntity(), (float) event.getDistance(), event.getDamageMultiplier());
			if (ModBus.post(ours))
				event.setCanceled(true);
			event.setDistance(ours.getDistance());
			event.setDamageMultiplier(ours.getDamageMultiplier());
		});
		bus.addListener((LivingHealEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.LivingHealEvent(event.getEntity(), event.getAmount());
			if (ModBus.post(ours))
				event.setCanceled(true);
			event.setAmount(ours.getAmount());
		});
		bus.addListener((LivingChangeTargetEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.LivingChangeTargetEvent(event.getEntity(), event.getOriginalAboutToBeSetTarget());
			ours.setNewAboutToBeSetTarget(event.getNewAboutToBeSetTarget());
			if (ModBus.post(ours))
				event.setCanceled(true);
			event.setNewAboutToBeSetTarget(ours.getNewAboutToBeSetTarget());
		});
		bus.addListener((LivingShieldBlockEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.LivingShieldBlockEvent(event.getEntity(), event.getDamageSource(), event.getBlockedDamage(), event.getBlocked());
			ours.setShieldDamage(event.shieldDamage());
			ModBus.post(ours);
			event.setBlocked(!ours.isCanceled() && ours.getBlocked());
			event.setBlockedDamage(ours.getBlockedDamage());
			event.setShieldDamage(Math.round(ours.shieldDamage()));
		});
		bus.addListener((LivingExperienceDropEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.LivingExperienceDropEvent(event.getEntity(), event.getAttackingPlayer(), event.getDroppedExperience());
			if (ModBus.post(ours))
				event.setCanceled(true);
			event.setDroppedExperience(ours.getDroppedExperience());
		});
		bus.addListener((MobEffectEvent.Remove event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.MobEffectEvent.Remove(event.getEntity(), event.getEffectInstance())))
				event.setCanceled(true);
		});
		bus.addListener((MobEffectEvent.Expired event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.MobEffectEvent.Expired(event.getEntity(), event.getEffectInstance())));
		bus.addListener((LivingEntityUseItemEvent.Start event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.LivingEntityUseItemEvent.Start(event.getEntity(), event.getItem(), event.getDuration());
			if (ModBus.post(ours))
				event.setCanceled(true);
			event.setDuration(ours.getDuration());
		});
		bus.addListener((LivingEntityUseItemEvent.Stop event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.LivingEntityUseItemEvent.Stop(event.getEntity(), event.getItem(), event.getDuration())))
				event.setCanceled(true);
		});
		bus.addListener((LivingEntityUseItemEvent.Finish event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.LivingEntityUseItemEvent.Finish(event.getEntity(), event.getItem())));
		bus.addListener((PlayerEvent.PlayerLoggedInEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerEvent.PlayerLoggedInEvent(event.getEntity())));
		bus.addListener((PlayerEvent.PlayerRespawnEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerEvent.PlayerRespawnEvent(event.getEntity(), event.isEndConquered())));
		bus.addListener((PlayerEvent.PlayerChangedDimensionEvent event) -> ModBus
				.post(new net.sombrius.thedeepvoid.port.bus.PlayerEvent.PlayerChangedDimensionEvent(event.getEntity(), event.getFrom(), event.getTo())));
		bus.addListener((PlayerEvent.Clone event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerEvent.Clone(event.getEntity(), event.getOriginal(), event.isWasDeath())));
		bus.addListener((PlayerEvent.ItemCraftedEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerEvent.ItemCraftedEvent(event.getEntity(), event.getCrafting())));
		bus.addListener((PlayerEvent.ItemSmeltedEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerEvent.ItemSmeltedEvent(event.getEntity(), event.getSmelting())));
		bus.addListener((PlayerWakeUpEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerWakeUpEvent(event.getEntity())));
		bus.addListener((PlayerXpEvent.XpChange event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.PlayerXpEvent.XpChange(event.getEntity(), event.getAmount());
			if (ModBus.post(ours))
				event.setCanceled(true);
			event.setAmount(ours.getAmount());
		});
		bus.addListener((CanPlayerSleepEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.CanPlayerSleepEvent(event.getEntity(), event.getPos(), event.getProblem());
			ModBus.post(ours);
			event.setProblem(ours.getProblem());
		});
		bus.addListener((ItemFishedEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ItemFishedEvent(event.getEntity(), event.getDrops())));
		bus.addListener((ItemTossEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ItemTossEvent(event.getEntity(), event.getPlayer())));
		bus.addListener((ItemEntityPickupEvent.Pre event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ItemEntityPickupEvent.Pre(event.getPlayer(), event.getItemEntity())));
		bus.addListener((PlayerInteractEvent.RightClickBlock event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerInteractEvent.RightClickBlock(event.getEntity(), event.getHand(), event.getPos(), event.getFace())))
				event.setCanceled(true);
		});
		bus.addListener((PlayerInteractEvent.EntityInteract event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerInteractEvent.EntityInteract(event.getEntity(), event.getHand(), event.getTarget())))
				event.setCanceled(true);
		});
		bus.addListener((PlayerInteractEvent.LeftClickBlock event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.PlayerInteractEvent.LeftClickBlock(event.getEntity(), event.getPos(), event.getFace())))
				event.setCanceled(true);
		});
		bus.addListener((BlockEvent.BreakEvent event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.BlockEvent.BreakEvent(event.getLevel(), event.getPos(), event.getState(), event.getPlayer())))
				event.setCanceled(true);
		});
		bus.addListener((BlockEvent.EntityPlaceEvent event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.BlockEvent.EntityPlaceEvent(event.getLevel(), event.getPos(), event.getPlacedBlock(), event.getPlacedAgainst(), event.getEntity())))
				event.setCanceled(true);
		});
		bus.addListener((EntityTravelToDimensionEvent event) -> {
			if (ModBus.post(new net.sombrius.thedeepvoid.port.bus.EntityTravelToDimensionEvent(event.getEntity(), event.getDimension())))
				event.setCanceled(true);
		});
		bus.addListener((EntityJoinLevelEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.EntityJoinLevelEvent(event.getEntity(), event.getLevel())));
		bus.addListener((EntityLeaveLevelEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.EntityLeaveLevelEvent(event.getEntity(), event.getLevel())));
		bus.addListener((ServerChatEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.ServerChatEvent(event.getPlayer(), event.getRawText(), event.getMessage());
			if (ModBus.post(ours))
				event.setCanceled(true);
			event.setMessage(ours.getMessage());
		});
		// 1.21.2: clients get no recipes; the server sends the JEI recipe types (port.PortRecipeSync)
		bus.addListener((net.neoforged.neoforge.event.OnDatapackSyncEvent event) -> net.sombrius.thedeepvoid.port.PortRecipeSync.TYPES.forEach(type -> event.sendRecipes(type.get())));
		bus.addListener((FurnaceFuelBurnTimeEvent event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.FurnaceFuelBurnTimeEvent(event.getItemStack(), event.getBurnTime());
			if (ModBus.post(ours))
				event.setBurnTime(ours.getBurnTime());
		});
		bus.addListener((ItemAttributeModifierEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ItemAttributeModifierEvent(event.getItemStack(), event::addModifier)));
	}

	/** The mod's item handlers ({@link PortCaps}) as NeoForge capabilities, so hoppers and other mods see them as in 1.99.3.1 */
	private static void registerCapabilities(RegisterCapabilitiesEvent event) {
		ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterCapabilitiesEvent());
		PortCaps.blockProviders().forEach((type, provider) -> {
			@SuppressWarnings("unchecked")
			BlockEntityType<BlockEntity> beType = (BlockEntityType<BlockEntity>) type;
			// 21.9+: the transfer API; the mod's block entities are vanilla containers, wrapped as NeoForge does for its own
			event.registerBlockEntity(Capabilities.Item.BLOCK, beType, (be, side) -> be instanceof net.minecraft.world.WorldlyContainer worldly && side != null
					? new net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper(worldly, side)
					: be instanceof net.minecraft.world.Container container ? net.neoforged.neoforge.transfer.item.VanillaContainerWrapper.of(container) : null);
		});
	}
}
