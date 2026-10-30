package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.PenitentEntity;

import javax.annotation.Nullable;

public class PenitentModel extends GeoModel<PenitentEntity> {
	@Override
	public Identifier getAnimationResource(PenitentEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/penitent.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/penitent.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(PenitentEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
