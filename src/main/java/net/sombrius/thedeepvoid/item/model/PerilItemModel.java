package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.PerilItem;
import software.bernie.geckolib.model.GeoModel;

public class PerilItemModel extends GeoModel<PerilItem> {
	@Override
	public ResourceLocation getAnimationResource(PerilItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/perilarms.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PerilItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/perilarms.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PerilItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/peril.png");
	}
}
