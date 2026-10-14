package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.PerilOffHandItem;
import software.bernie.geckolib.model.GeoModel;

public class PerilOffHandItemModel extends GeoModel<PerilOffHandItem> {
	@Override
	public ResourceLocation getAnimationResource(PerilOffHandItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/perilarms.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PerilOffHandItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/perilarms.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PerilOffHandItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/peril.png");
	}
}
