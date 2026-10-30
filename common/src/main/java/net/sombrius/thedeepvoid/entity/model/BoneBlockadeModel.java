package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BoneBlockadeEntity;
import software.bernie.geckolib.model.GeoModel;

public class BoneBlockadeModel extends GeoModel<BoneBlockadeEntity> {
	@Override
	public ResourceLocation getAnimationResource(BoneBlockadeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/boneblockade.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BoneBlockadeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/boneblockade.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BoneBlockadeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
