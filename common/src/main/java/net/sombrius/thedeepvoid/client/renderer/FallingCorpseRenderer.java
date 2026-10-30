
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.client.model.Modelfalling_corpse;
import net.sombrius.thedeepvoid.entity.FallingCorpseEntity;

public class FallingCorpseRenderer extends MobRenderer<FallingCorpseEntity, LivingEntityRenderState, Modelfalling_corpse<LivingEntityRenderState>> {
	public FallingCorpseRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelfalling_corpse(context.bakeLayer(Modelfalling_corpse.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/falling_corpse_texture.png");
	}
}
