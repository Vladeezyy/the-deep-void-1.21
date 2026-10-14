package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.EldritchMawItem;
import software.bernie.geckolib.model.GeoModel;

public class EldritchMawItemModel extends GeoModel<EldritchMawItem> {
	@Override
	public ResourceLocation getAnimationResource(EldritchMawItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/eldritchmawarms.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(EldritchMawItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/eldritchmawarms.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EldritchMawItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/eldritchmouth.png");
	}
}
