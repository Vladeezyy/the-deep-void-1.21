package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.GoreLurkerEntity;
import software.bernie.geckolib.model.GeoModel;

public class GoreLurkerModel extends GeoModel<GoreLurkerEntity> {
	@Override
	public ResourceLocation getAnimationResource(GoreLurkerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/harvestmen.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GoreLurkerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/harvestmen.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GoreLurkerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
