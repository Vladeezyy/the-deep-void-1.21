package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.GoreExpectoratorEntity;

import javax.annotation.Nullable;

public class GoreExpectoratorModel extends GeoModel<GoreExpectoratorEntity> {
	@Override
	public Identifier getAnimationResource(GoreExpectoratorEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "gore_spitter");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "gore_spitter");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(GoreExpectoratorEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
