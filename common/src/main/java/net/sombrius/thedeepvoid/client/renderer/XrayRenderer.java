
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.client.model.Modelxray;
import net.sombrius.thedeepvoid.entity.XrayEntity;

public class XrayRenderer extends MobRenderer<XrayEntity, LivingEntityRenderState, Modelxray<LivingEntityRenderState>> {
	public XrayRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelxray(context.bakeLayer(Modelxray.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/xray.png");
	}
}
