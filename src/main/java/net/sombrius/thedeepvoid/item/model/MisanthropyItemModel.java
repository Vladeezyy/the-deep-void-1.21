package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.MisanthropyItem;
import software.bernie.geckolib.model.GeoModel;

public class MisanthropyItemModel extends GeoModel<MisanthropyItem> {
	@Override
	public ResourceLocation getAnimationResource(MisanthropyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/misanthropyarms.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MisanthropyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/misanthropyarms.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MisanthropyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/misanthropy.png");
	}
}
