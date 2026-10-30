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
}
