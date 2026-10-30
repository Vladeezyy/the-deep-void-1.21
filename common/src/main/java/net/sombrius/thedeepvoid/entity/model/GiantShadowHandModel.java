package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.GiantShadowHandEntity;

import javax.annotation.Nullable;

public class GiantShadowHandModel extends GeoModel<GiantShadowHandEntity> {
	@Override
	public Identifier getAnimationResource(GiantShadowHandEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/giantshadowhand.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/giantshadowhand.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(GiantShadowHandEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
