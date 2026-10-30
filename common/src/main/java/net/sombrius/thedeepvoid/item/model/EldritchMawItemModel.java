package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.EldritchMawItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class EldritchMawItemModel extends GeoModel<EldritchMawItem> {
	@Override
	public Identifier getAnimationResource(EldritchMawItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/eldritchmawarms.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/eldritchmawarms.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/eldritchmouth.png");
	}
}
