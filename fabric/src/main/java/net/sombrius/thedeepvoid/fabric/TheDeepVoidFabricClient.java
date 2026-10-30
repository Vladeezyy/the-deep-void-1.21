package net.sombrius.thedeepvoid.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.Minecraft;

import software.bernie.geckolib.renderer.GeoArmorRenderer;

import net.sombrius.thedeepvoid.port.bus.RenderFrameEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterParticleProvidersEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterMenuScreensEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterKeyMappingsEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterDimensionSpecialEffectsEvent;
import net.sombrius.thedeepvoid.port.bus.ScreenEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.LevelTickEvent;
import net.sombrius.thedeepvoid.port.bus.ItemTooltipEvent;
import net.sombrius.thedeepvoid.port.bus.FMLClientSetupEvent;
import net.sombrius.thedeepvoid.port.bus.EntityRenderersEvent;
import net.sombrius.thedeepvoid.port.bus.EntityLeaveLevelEvent;
import net.sombrius.thedeepvoid.port.bus.EntityJoinLevelEvent;
import net.sombrius.thedeepvoid.port.bus.ClientTickEvent;
import net.sombrius.thedeepvoid.port.bus.ClientEventIndex;
import net.sombrius.thedeepvoid.port.neo.IClientItemExtensions;
import net.sombrius.thedeepvoid.port.neo.HasClientExtensions;
import net.sombrius.thedeepvoid.port.PortUtil;
import net.sombrius.thedeepvoid.port.PortClient;
import net.sombrius.thedeepvoid.port.PortArmPoses;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModFluids;

import java.lang.reflect.Method;

/** Fabric client entry point: renderers, screens, packets, client extensions and the client events posted on {@link ModBus} */
public final class TheDeepVoidFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientEventIndex.register();
		PortUtil.clientRegistries = () -> Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.registryAccess();
		PortUtil.clientPlayer = () -> Minecraft.getInstance().player;
		PortClient.fluidRenderLayer = BlockRenderLayerMap.INSTANCE::putFluid;
		// Fabric has no enum extension: a gun pose is vanilla's ITEM pose, the transformer runs after the animation (mixin)
		PortArmPoses.poses = name -> {
			FabricArmPoses.lastPose = name;
			return HumanoidModel.ArmPose.ITEM;
		};
		FabricPlatform.CLIENTBOUND.forEach(TheDeepVoidFabricClient::receive);
		FabricPlatform.toServer = ClientPlayNetworking::send;

		ModBus.post(new EntityRenderersEvent.RegisterRenderers(new EntityRenderersEvent.RegisterRenderers.Sink() {
			@Override
			public <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> provider) {
				EntityRendererRegistry.register(type, provider);
			}
		}));
		ModBus.post(new EntityRenderersEvent.RegisterLayerDefinitions((location, definition) -> EntityModelLayerRegistry.registerModelLayer(location, definition::get)));
		ModBus.post(new RegisterParticleProvidersEvent(new RegisterParticleProvidersEvent.Sink() {
			@Override
			public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
				ParticleFactoryRegistry.getInstance().register(type, registration::create);
			}
		}));
		ModBus.post(new RegisterMenuScreensEvent(MenuScreens::register));
		ModBus.post(new RegisterKeyMappingsEvent(KeyBindingHelper::registerKeyBinding));
		ModBus.post(new RegisterDimensionSpecialEffectsEvent(DimensionRenderingRegistry::registerDimensionEffects));
		itemExtensions();
		fluids();
		ModBus.post(new FMLClientSetupEvent());

		ClientTickEvents.START_CLIENT_TICK.register(minecraft -> ModBus.post(new ClientTickEvent.Pre()));
		ClientTickEvents.END_CLIENT_TICK.register(minecraft -> ModBus.post(new ClientTickEvent.Post()));
		ClientTickEvents.END_WORLD_TICK.register(level -> ModBus.post(new LevelTickEvent.Post(level)));
		ScreenEvents.AFTER_INIT.register((minecraft, screen, width, height) -> ScreenEvents.afterRender(screen)
				.register((s, guiGraphics, mouseX, mouseY, partialTick) -> ModBus.post(new ScreenEvent.Render.Post(s, guiGraphics))));
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> ModBus.post(new ItemTooltipEvent(stack, Minecraft.getInstance().player, lines, type, context)));
		ClientEntityEvents.ENTITY_LOAD.register((entity, level) -> ModBus.post(new EntityJoinLevelEvent(entity, level)));
		ClientEntityEvents.ENTITY_UNLOAD.register((entity, level) -> ModBus.post(new EntityLeaveLevelEvent(entity, level)));
		WorldRenderEvents.END.register(context -> {
			if (ModBus.hasListeners(RenderFrameEvent.Post.class))
				ModBus.post(new RenderFrameEvent.Post());
		});
	}

	private static <T extends CustomPacketPayload> void receive(FabricPlatform.Clientbound<T> packet) {
		ClientPlayNetworking.registerGlobalReceiver(packet.type(), (payload, context) -> packet.handler().handle(payload,
				FabricPlatform.context(PacketFlow.CLIENTBOUND, context.player(), reason -> context.client().getConnection().getConnection().disconnect(reason))));
	}

	/** NeoForge's client item extensions: the item renderers and the armor models (the arm poses and hand transforms: mixins) */
	private static void itemExtensions() {
		for (var entry : TheDeepVoidModItems.REGISTRY.getEntries()) {
			Item item = entry.get();
			if (!(item instanceof HasClientExtensions.Item owner))
				continue;
			IClientItemExtensions extensions = owner.clientExtensions();
			if (overrides(extensions, "getCustomRenderer")) {
				BlockEntityWithoutLevelRenderer[] renderer = {null};
				BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, pose, buffers, light, overlay) -> {
					if (renderer[0] == null)
						renderer[0] = extensions.getCustomRenderer();
					renderer[0].renderByItem(stack, mode, pose, buffers, light, overlay);
				});
			}
			if (overrides(extensions, "getHumanoidArmorModel"))
				ArmorRenderer.register((pose, buffers, stack, entity, slot, light, contextModel) -> {
					HumanoidModel<?> model = extensions.getHumanoidArmorModel(entity, stack, slot, contextModel);
					@SuppressWarnings({"unchecked", "rawtypes"})
					HumanoidModel<LivingEntity> parent = (HumanoidModel) contextModel;
					@SuppressWarnings({"unchecked", "rawtypes"})
					HumanoidModel<LivingEntity> armor = (HumanoidModel) model;
					if (model instanceof GeoArmorRenderer<?> geo) {
						// GeckoLib's own armor path on Fabric: the renderer needs the buffer source of this frame
						geo.prepForRender(entity, stack, slot, contextModel, buffers, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true), 0, 0, 0, 0);
						parent.copyPropertiesTo(armor);
						geo.renderToBuffer(pose, null, light, OverlayTexture.NO_OVERLAY, -1);
						return;
					}
					parent.copyPropertiesTo(armor);
					ResourceLocation texture = armorTexture(stack, entity, slot);
					armor.renderToBuffer(pose, buffers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, -1);
				}, item);
		}
	}

	/** The item's NeoForge-only getArmorTexture, else its armor material's first layer */
	private static ResourceLocation armorTexture(ItemStack stack, LivingEntity entity, EquipmentSlot slot) {
		Item item = stack.getItem();
		ArmorMaterial.Layer layer = item instanceof net.minecraft.world.item.ArmorItem armor ? armor.getMaterial().value().layers().get(0) : null;
		try {
			Method method = item.getClass().getMethod("getArmorTexture", ItemStack.class, Entity.class, EquipmentSlot.class, ArmorMaterial.Layer.class, boolean.class);
			Object texture = method.invoke(item, stack, entity, slot, layer, slot == EquipmentSlot.LEGS);
			if (texture instanceof ResourceLocation location)
				return location;
		} catch (ReflectiveOperationException e) {
			// no own texture
		}
		return layer != null ? layer.texture(slot == EquipmentSlot.LEGS) : ResourceLocation.withDefaultNamespace("textures/misc/white.png");
	}

	static boolean overrides(Object extensions, String method) {
		for (Method m : extensions.getClass().getDeclaredMethods())
			if (m.getName().equals(method))
				return true;
		return false;
	}

	/** NeoForge's fluid type client extensions of the two fluids (still and flowing textures) */
	private static void fluids() {
		fluid(TheDeepVoidModFluids.LIQUID_VOID.get(), TheDeepVoidModFluids.FLOWING_LIQUID_VOID.get(), "the_deep_void:block/liquid_void", "the_deep_void:block/liquid_void");
		fluid(TheDeepVoidModFluids.LIQUID_ICHOR.get(), TheDeepVoidModFluids.FLOWING_LIQUID_ICHOR.get(), "the_deep_void:block/liquid_ichor_still", "the_deep_void:block/liquid_ichor_flow");
	}

	private static void fluid(Fluid still, Fluid flowing, String stillTexture, String flowingTexture) {
		FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, new SimpleFluidRenderHandler(ResourceLocation.parse(stillTexture), ResourceLocation.parse(flowingTexture)));
	}
}
