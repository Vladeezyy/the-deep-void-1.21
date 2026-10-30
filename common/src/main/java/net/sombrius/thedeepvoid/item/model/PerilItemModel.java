package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.PerilItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class PerilItemModel extends GeoModel<PerilItem> {
	@Override
	public Identifier getAnimationResource(PerilItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/perilarms.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/perilarms.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/peril.png");
	}
}
