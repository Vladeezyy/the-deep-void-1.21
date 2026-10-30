package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.DarkSteelSawEntity;

import javax.annotation.Nullable;

public class DarkSteelSawModel extends GeoModel<DarkSteelSawEntity> {
	@Override
	public Identifier getAnimationResource(DarkSteelSawEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "darksteelsaw");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "darksteelsaw");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(DarkSteelSawEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
