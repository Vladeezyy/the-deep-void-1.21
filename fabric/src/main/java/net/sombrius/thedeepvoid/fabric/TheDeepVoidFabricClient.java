package net.sombrius.thedeepvoid.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.Minecraft;


import net.sombrius.thedeepvoid.port.bus.RenderFrameEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterParticleProvidersEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterMenuScreensEvent;
import net.sombrius.thedeepvoid.port.bus.RegisterKeyMappingsEvent;
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
import net.sombrius.thedeepvoid.port.PortItemProperties;
import net.sombrius.thedeepvoid.port.PortRecipeSync;
import net.sombrius.thedeepvoid.fabric.mixin.client.RangeSelectItemModelPropertiesAccessor;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
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
		// 26.1: block and fluid layers come from the transparency of their textures
		PortClient.fluidRenderLayer = (fluid, layer) -> {
		};
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
		ModBus.post(new EntityRenderersEvent.RegisterLayerDefinitions((location, definition) -> ModelLayerRegistry.registerModelLayer(location, definition::get)));
		ModBus.post(new RegisterParticleProvidersEvent(new RegisterParticleProvidersEvent.Sink() {
			@Override
			public <T extends ParticleOptions> void register(ParticleType<T> type, net.minecraft.client.particle.ParticleResources.SpriteParticleRegistration<T> registration) {
				ParticleProviderRegistry.getInstance().register(type, registration::create);
			}
		}));
		ModBus.post(new RegisterMenuScreensEvent(MenuScreens::register));
		ModBus.post(new RegisterKeyMappingsEvent(KeyMappingHelper::registerKeyMapping));
		itemExtensions();
		fluids();
		ModBus.post(new FMLClientSetupEvent());
		// 1.21.4: MCreator's item properties are one range select property (port.PortItemProperties)
		RangeSelectItemModelPropertiesAccessor.idMapper().put(PortItemProperties.ID, PortItemProperties.MAP_CODEC);
		// 1.21.2: the JEI recipe types the server synced (port.PortRecipeSync)
		net.fabricmc.fabric.api.client.recipe.v1.sync.ClientRecipeSynchronizedEvent.EVENT.register((minecraft, recipes) -> {
			java.util.List<net.minecraft.world.item.crafting.RecipeHolder<?>> ours = new java.util.ArrayList<>();
			for (var type : PortRecipeSync.TYPES)
				ours.addAll(recipes.getAllOfType((net.minecraft.world.item.crafting.RecipeType) type.get()));
			PortRecipeSync.setRecipes(net.minecraft.world.item.crafting.RecipeMap.create(ours));
		});

		ClientTickEvents.START_CLIENT_TICK.register(minecraft -> ModBus.post(new ClientTickEvent.Pre()));
		ClientTickEvents.END_CLIENT_TICK.register(minecraft -> ModBus.post(new ClientTickEvent.Post()));
		ClientTickEvents.END_LEVEL_TICK.register(level -> ModBus.post(new LevelTickEvent.Post(level)));
		ScreenEvents.AFTER_INIT.register((minecraft, screen, width, height) -> ScreenEvents.afterExtract(screen)
				.register((s, guiGraphics, mouseX, mouseY, partialTick) -> ModBus.post(new ScreenEvent.Render.Post(s, guiGraphics))));
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> ModBus.post(new ItemTooltipEvent(stack, Minecraft.getInstance().player, lines, type, context)));
		ClientEntityEvents.ENTITY_LOAD.register((entity, level) -> ModBus.post(new EntityJoinLevelEvent(entity, level)));
		ClientEntityEvents.ENTITY_UNLOAD.register((entity, level) -> ModBus.post(new EntityLeaveLevelEvent(entity, level)));
		LevelRenderEvents.END_MAIN.register(context -> {
			if (ModBus.hasListeners(RenderFrameEvent.Post.class))
				ModBus.post(new RenderFrameEvent.Post());
		});
	}

	private static <T extends CustomPacketPayload> void receive(FabricPlatform.Clientbound<T> packet) {
		ClientPlayNetworking.registerGlobalReceiver(packet.type(), (payload, context) -> packet.handler().handle(payload,
				FabricPlatform.context(PacketFlow.CLIENTBOUND, context.player(), reason -> context.client().getConnection().getConnection().disconnect(reason))));
	}

	/**
	 * NeoForge's client item extensions: the armor models of the Blockbench armor (the arm poses and hand transforms:
	 * mixins). GeckoLib draws its items and armor itself (render providers).
	 */
	private static void itemExtensions() {
		for (var entry : TheDeepVoidModItems.REGISTRY.getEntries()) {
			Item item = entry.get();
			if (!(item instanceof HasClientExtensions.Item owner))
				continue;
			IClientItemExtensions extensions = owner.clientExtensions();
			if (overrides(extensions, "getHumanoidArmorModel"))
				ArmorRenderer.register((pose, collector, stack, state, slot, light, contextModel) -> {
					EquipmentClientInfo.LayerType type = slot == EquipmentSlot.LEGS ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS : EquipmentClientInfo.LayerType.HUMANOID;
					@SuppressWarnings("unchecked")
					HumanoidModel<HumanoidRenderState> model = (HumanoidModel<HumanoidRenderState>) extensions.getHumanoidArmorModel(stack, type, contextModel);
					Identifier texture = extensions.getArmorTexture(stack, type, null, MISSING_TEXTURE);
					ArmorRenderer.submitTransformCopyingModel(contextModel, state, model, state, true, collector, pose, RenderTypes.armorCutoutNoCull(texture), light, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
				}, item);
		}
	}

	private static final Identifier MISSING_TEXTURE = Identifier.withDefaultNamespace("textures/misc/white.png");

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
		FluidRenderingRegistry.register(still, flowing, new net.minecraft.client.renderer.block.FluidModel.Unbaked(new net.minecraft.client.resources.model.sprite.Material(Identifier.parse(stillTexture)),
				new net.minecraft.client.resources.model.sprite.Material(Identifier.parse(flowingTexture)), null, null));
	}
}
