package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.item.SoulWingsItem;
import software.bernie.geckolib.model.GeoModel;

public class SoulWingsModel extends GeoModel<SoulWingsItem> {
	@Override
	public ResourceLocation getAnimationResource(SoulWingsItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/soulwings.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SoulWingsItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/soulwings.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SoulWingsItem object) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/item/soul_wings.png");
	}
}
