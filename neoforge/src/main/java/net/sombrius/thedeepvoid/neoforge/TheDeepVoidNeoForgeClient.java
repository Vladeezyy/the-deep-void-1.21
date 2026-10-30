package net.sombrius.thedeepvoid.neoforge;

import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.ClientEventIndex;
import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;
import net.sombrius.thedeepvoid.port.PortUtil;
import net.sombrius.thedeepvoid.port.PortClient;
import net.sombrius.thedeepvoid.port.PortArmPoses;
import net.sombrius.thedeepvoid.port.ArmPoses;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluidTypes;
import net.sombrius.thedeepvoid.fluid.types.LiquidVoidFluidType;
import net.sombrius.thedeepvoid.fluid.types.LiquidIchorFluidType;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import com.mojang.blaze3d.vertex.PoseStack;

/** NeoForge client entry point: the client events posted on {@link ModBus} */
@Mod(value = TheDeepVoidMod.MODID, dist = Dist.CLIENT)
public final class TheDeepVoidNeoForgeClient {
	public TheDeepVoidNeoForgeClient(IEventBus modBus) {
		ClientEventIndex.register();
		PortUtil.clientRegistries = () -> Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.registryAccess();
		PortUtil.clientPlayer = () -> Minecraft.getInstance().player;
		PortClient.fluidRenderLayer = ItemBlockRenderTypes::setRenderLayer;
		PortArmPoses.poses = name -> switch (name) {
			case "APOSTASY" -> ArmPoses.APOSTASY.getValue();
			case "DOUBLE_BARREL_SHOTGUN" -> ArmPoses.DOUBLE_BARREL_SHOTGUN.getValue();
			case "ELDRITCH_MAW" -> ArmPoses.ELDRITCH_MAW.getValue();
			case "HERESY" -> ArmPoses.HERESY.getValue();
			case "MISANTHROPY" -> ArmPoses.MISANTHROPY.getValue();
			case "PERIL" -> ArmPoses.PERIL.getValue();
			case "PERIL_OFF_HAND" -> ArmPoses.PERIL_OFF_HAND.getValue();
			default -> HumanoidModel.ArmPose.ITEM;
		};

		modBus.addListener((FMLClientSetupEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.FMLClientSetupEvent()));
		modBus.addListener((EntityRenderersEvent.RegisterRenderers event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.EntityRenderersEvent.RegisterRenderers(
				new net.sombrius.thedeepvoid.port.bus.EntityRenderersEvent.RegisterRenderers.Sink() {
					@Override
					public <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> provider) {
						event.registerEntityRenderer(type, provider);
					}
				})));
		modBus.addListener((EntityRenderersEvent.RegisterLayerDefinitions event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.EntityRenderersEvent.RegisterLayerDefinitions(event::registerLayerDefinition)));
		modBus.addListener((RegisterParticleProvidersEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterParticleProvidersEvent(
				new net.sombrius.thedeepvoid.port.bus.RegisterParticleProvidersEvent.Sink() {
					@Override
					public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
						event.registerSpriteSet(type, registration);
					}
				})));
		modBus.addListener((RegisterMenuScreensEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterMenuScreensEvent(event::register)));
		modBus.addListener((RegisterKeyMappingsEvent event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RegisterKeyMappingsEvent(event::register)));
		modBus.addListener(TheDeepVoidNeoForgeClient::registerClientExtensions);

		IEventBus bus = NeoForge.EVENT_BUS;
		bus.addListener((ClientTickEvent.Pre event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ClientTickEvent.Pre()));
		bus.addListener((ClientTickEvent.Post event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ClientTickEvent.Post()));
		bus.addListener((RenderGuiEvent.Pre event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.RenderGuiEvent.Pre(event.getGuiGraphics())));
		bus.addListener((ScreenEvent.Render.Post event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ScreenEvent.Render.Post(event.getScreen(), event.getGuiGraphics())));
		bus.addListener((RenderFrameEvent.Post event) -> {
			if (ModBus.hasListeners(net.sombrius.thedeepvoid.port.bus.RenderFrameEvent.Post.class))
				ModBus.post(new net.sombrius.thedeepvoid.port.bus.RenderFrameEvent.Post());
		});
		bus.addListener((ItemTooltipEvent event) -> ModBus
				.post(new net.sombrius.thedeepvoid.port.bus.ItemTooltipEvent(event.getItemStack(), event.getEntity(), event.getToolTip(), event.getFlags(), event.getContext())));
		bus.addListener((ViewportEvent.ComputeFogColor event) -> {
			var ours = new net.sombrius.thedeepvoid.port.bus.ViewportEvent.ComputeFogColor(event.getCamera(), event.getPartialTick(), event.getRed(), event.getGreen(), event.getBlue());
			ModBus.post(ours);
			event.setRed(ours.getRed());
			event.setGreen(ours.getGreen());
			event.setBlue(ours.getBlue());
		});
		bus.addListener((ViewportEvent.RenderFog event) -> ModBus.post(new net.sombrius.thedeepvoid.port.bus.ViewportEvent.RenderFog(event.getCamera(), event.getPartialTick(), event.getType(), event.getFogData())));
	}

	/** The mod's item and effect extensions (port.neo) as NeoForge's, and the fluid types' own */
	private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		for (var entry : TheDeepVoidModItems.REGISTRY.getEntries()) {
			if (entry.get() instanceof HasClientExtensions.Item item)
				event.registerItem(item(item.clientExtensions()), entry.get());
		}
		for (var entry : TheDeepVoidModMobEffects.REGISTRY.getEntries()) {
			if (entry.get() instanceof HasClientExtensions.Effect effect)
				event.registerMobEffect(effect(effect.clientExtensions()), entry.get());
		}
		event.registerFluidType(((LiquidVoidFluidType) TheDeepVoidModFluidTypes.LIQUID_VOID_TYPE.get()).clientExtensions(), TheDeepVoidModFluidTypes.LIQUID_VOID_TYPE.get());
		event.registerFluidType(((LiquidIchorFluidType) TheDeepVoidModFluidTypes.LIQUID_ICHOR_TYPE.get()).clientExtensions(), TheDeepVoidModFluidTypes.LIQUID_ICHOR_TYPE.get());
	}

	private static IClientItemExtensions item(net.sombrius.thedeepvoid.port.neo.IClientItemExtensions ours) {
		return new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				BlockEntityWithoutLevelRenderer renderer = ours.getCustomRenderer();
				return renderer != null ? renderer : IClientItemExtensions.super.getCustomRenderer();
			}

			@Override
			public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
				return ours.getArmPose(entity, hand, stack);
			}

			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
				return ours.applyForgeHandTransform(poseStack, player, arm, itemInHand, partialTick, equipProcess, swingProcess);
			}

			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
				return ours.getHumanoidArmorModel(entity, stack, slot, original);
			}
		};
	}

	private static IClientMobEffectExtensions effect(net.sombrius.thedeepvoid.port.neo.IClientMobEffectExtensions ours) {
		return new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance instance) {
				return ours.isVisibleInInventory(instance);
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance instance) {
				return ours.isVisibleInGui(instance);
			}

			@Override
			public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
				return ours.renderInventoryText(instance, screen, guiGraphics, x, y, blitOffset);
			}
		};
	}
}
