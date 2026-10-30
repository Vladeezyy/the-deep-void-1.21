package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.LurkerEntity;

import javax.annotation.Nullable;

public class LurkerModel extends GeoModel<LurkerEntity> {
	@Override
	public Identifier getAnimationResource(LurkerEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/lurker.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/lurker.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(LurkerEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
