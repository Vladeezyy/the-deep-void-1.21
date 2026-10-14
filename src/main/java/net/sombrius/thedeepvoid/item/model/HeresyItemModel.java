package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.HeresyItem;
import software.bernie.geckolib.model.GeoModel;

public class HeresyItemModel extends GeoModel<HeresyItem> {
	@Override
	public ResourceLocation getAnimationResource(HeresyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/heresyarms.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HeresyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/heresyarms.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HeresyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/heresynew.png");
	}
}
