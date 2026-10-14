package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.MisanthropicHivemindEntity;
import software.bernie.geckolib.model.GeoModel;

public class MisanthropicHivemindModel extends GeoModel<MisanthropicHivemindEntity> {
	@Override
	public ResourceLocation getAnimationResource(MisanthropicHivemindEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/hivemindrework.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MisanthropicHivemindEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/hivemindrework.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MisanthropicHivemindEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
