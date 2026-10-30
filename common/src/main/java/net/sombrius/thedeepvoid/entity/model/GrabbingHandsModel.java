package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.GrabbingHandsEntity;

import javax.annotation.Nullable;

public class GrabbingHandsModel extends GeoModel<GrabbingHandsEntity> {
	@Override
	public Identifier getAnimationResource(GrabbingHandsEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "grabbinghands");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "grabbinghands");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(GrabbingHandsEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
