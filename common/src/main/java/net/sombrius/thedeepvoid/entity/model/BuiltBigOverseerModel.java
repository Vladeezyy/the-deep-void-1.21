package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.BuiltBigOverseerEntity;

import javax.annotation.Nullable;

public class BuiltBigOverseerModel extends GeoModel<BuiltBigOverseerEntity> {
	@Override
	public Identifier getAnimationResource(BuiltBigOverseerEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/overseer3blocks.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/overseer3blocks.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(BuiltBigOverseerEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
