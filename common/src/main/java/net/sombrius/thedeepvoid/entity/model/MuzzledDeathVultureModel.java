package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.MuzzledDeathVultureEntity;

import javax.annotation.Nullable;

public class MuzzledDeathVultureModel extends GeoModel<MuzzledDeathVultureEntity> {
	@Override
	public Identifier getAnimationResource(MuzzledDeathVultureEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "vulture");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "vulture");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(MuzzledDeathVultureEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
