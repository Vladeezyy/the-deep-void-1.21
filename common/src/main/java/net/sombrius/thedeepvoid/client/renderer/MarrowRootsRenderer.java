
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.client.model.ModelmarrowRoots;
import net.sombrius.thedeepvoid.entity.MarrowRootsEntity;

public class MarrowRootsRenderer extends MobRenderer<MarrowRootsEntity, LivingEntityRenderState, ModelmarrowRoots<LivingEntityRenderState>> {
	public MarrowRootsRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelmarrowRoots(context.bakeLayer(ModelmarrowRoots.LAYER_LOCATION)), 0f);
	}

	@Override
	protected void scale(LivingEntityRenderState state, PoseStack poseStack) {
		poseStack.scale(2f, 2f, 2f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/marrowroots.png");
	}
}
