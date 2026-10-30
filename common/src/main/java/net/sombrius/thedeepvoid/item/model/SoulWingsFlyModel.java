package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.SoulWingsFlyItem;
import software.bernie.geckolib.model.GeoModel;

public class SoulWingsFlyModel extends GeoModel<SoulWingsFlyItem> {
	@Override
	public ResourceLocation getAnimationResource(SoulWingsFlyItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/soulwings.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SoulWingsFlyItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/soulwings.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SoulWingsFlyItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/soul_wings.png");
	}
}
