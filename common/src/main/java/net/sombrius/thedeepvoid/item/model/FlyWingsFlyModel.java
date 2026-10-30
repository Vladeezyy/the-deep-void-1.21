package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.FlyWingsFlyItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class FlyWingsFlyModel extends GeoModel<FlyWingsFlyItem> {
	@Override
	public Identifier getAnimationResource(FlyWingsFlyItem object) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "flywings");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "flywings");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/flywings.png");
	}
}
