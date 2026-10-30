package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.FlyWingsItem;
import software.bernie.geckolib.model.GeoModel;

public class FlyWingsModel extends GeoModel<FlyWingsItem> {
	@Override
	public ResourceLocation getAnimationResource(FlyWingsItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/flywings.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FlyWingsItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/flywings.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FlyWingsItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/flywings.png");
	}
}
