package net.sombrius.thedeepvoid.port;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/** 1.21.2+ entity models see a render state; the MCreator models read their 1.20 animation values from it (client only) */
public final class PortRender {
	private PortRender() {
	}

	public static float limbSwing(EntityRenderState state) {
		return state instanceof LivingEntityRenderState living ? living.walkAnimationPos : 0;
	}

	public static float limbSwingAmount(EntityRenderState state) {
		return state instanceof LivingEntityRenderState living ? living.walkAnimationSpeed : 0;
	}

	public static float netHeadYaw(EntityRenderState state) {
		return state instanceof LivingEntityRenderState living ? living.yRot : 0;
	}

	public static float headPitch(EntityRenderState state) {
		return state instanceof LivingEntityRenderState living ? living.xRot : 0;
	}

	/** The render state of the mod's projectiles: their rotation of this frame */
	public static class RotatedState extends EntityRenderState {
		public float yRot;
		public float xRot;
	}

	/** The render state of renderers whose layers or scale come from procedures (computed when the state is extracted) */
	public static class LayerState extends net.minecraft.client.renderer.entity.state.LivingEntityRenderState {
		public final boolean[] layers = new boolean[4];
		public float scale = 1;
	}

	/**
	 * MCreator's extra model layer: the model again with another texture, when {@code visible} (a procedure's result
	 * stored in the render state) holds
	 */
	public static <S extends net.minecraft.client.renderer.entity.state.LivingEntityRenderState, M extends net.minecraft.client.model.EntityModel<S>> net.minecraft.client.renderer.entity.layers.RenderLayer<S, M> layer(
			net.minecraft.client.renderer.entity.RenderLayerParent<S, M> parent, M model, net.minecraft.resources.Identifier texture,
			java.util.function.Function<net.minecraft.resources.Identifier, net.minecraft.client.renderer.rendertype.RenderType> type, java.util.function.Predicate<S> visible, boolean hurtOverlay) {
		return new net.minecraft.client.renderer.entity.layers.RenderLayer<>(parent) {
			@Override
			public void submit(com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.SubmitNodeCollector collector, int light, S state, float yRot, float xRot) {
				if (visible.test(state))
					collector.submitModel(model, state, poseStack, type.apply(texture), light,
							hurtOverlay ? net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords(state, 0) : net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY, state.outlineColor, null);
			}
		};
	}
}
