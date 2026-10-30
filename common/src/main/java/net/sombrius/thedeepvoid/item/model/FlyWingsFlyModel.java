package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.FlyWingsFlyItem;
import software.bernie.geckolib.model.GeoModel;

public class FlyWingsFlyModel extends GeoModel<FlyWingsFlyItem> {
	@Override
	public ResourceLocation getAnimationResource(FlyWingsFlyItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/flywings.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FlyWingsFlyItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/flywings.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FlyWingsFlyItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/flywings.png");
	}
}
