package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.HiveFangsEntity;

import javax.annotation.Nullable;

public class HiveFangsModel extends GeoModel<HiveFangsEntity> {
	@Override
	public Identifier getAnimationResource(HiveFangsEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/hivefangs.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/hivefangs.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(HiveFangsEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
