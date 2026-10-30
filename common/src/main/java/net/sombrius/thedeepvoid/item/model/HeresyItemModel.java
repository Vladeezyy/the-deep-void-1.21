package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.HeresyItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class HeresyItemModel extends GeoModel<HeresyItem> {
	@Override
	public Identifier getAnimationResource(HeresyItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/heresyarms.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/heresyarms.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/heresynew.png");
	}
}
