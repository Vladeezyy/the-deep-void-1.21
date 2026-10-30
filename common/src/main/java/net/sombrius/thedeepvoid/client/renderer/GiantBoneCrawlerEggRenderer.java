
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.client.model.Modelgiant_crawler_egg;
import net.sombrius.thedeepvoid.entity.GiantBoneCrawlerEggEntity;

public class GiantBoneCrawlerEggRenderer extends MobRenderer<GiantBoneCrawlerEggEntity, LivingEntityRenderState, Modelgiant_crawler_egg<LivingEntityRenderState>> {
	public GiantBoneCrawlerEggRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgiant_crawler_egg(context.bakeLayer(Modelgiant_crawler_egg.LAYER_LOCATION)), 2.3f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/giant_crawler_egg.png");
	}
}
