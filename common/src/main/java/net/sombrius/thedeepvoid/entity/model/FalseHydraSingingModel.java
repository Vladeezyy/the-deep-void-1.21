package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.FalseHydraSingingEntity;

import javax.annotation.Nullable;

public class FalseHydraSingingModel extends GeoModel<FalseHydraSingingEntity> {
	@Override
	public Identifier getAnimationResource(FalseHydraSingingEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/falsehydra.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/falsehydra.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(FalseHydraSingingEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
