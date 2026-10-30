package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.RideableFlyingEyekinEntity;

import javax.annotation.Nullable;

public class RideableFlyingEyekinModel extends GeoModel<RideableFlyingEyekinEntity> {
	@Override
	public Identifier getAnimationResource(RideableFlyingEyekinEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "eye");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "eye");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(RideableFlyingEyekinEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
