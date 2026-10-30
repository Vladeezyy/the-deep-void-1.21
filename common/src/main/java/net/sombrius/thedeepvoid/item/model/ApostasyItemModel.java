package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.ApostasyItem;
import software.bernie.geckolib.model.GeoModel;

public class ApostasyItemModel extends GeoModel<ApostasyItem> {
	@Override
	public ResourceLocation getAnimationResource(ApostasyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/apostasyarms.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ApostasyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/apostasyarms.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ApostasyItem animatable) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/apostasynew.png");
	}
}
