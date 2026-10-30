package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.HivemindTentaclesEntity;

import javax.annotation.Nullable;

public class HivemindTentaclesModel extends GeoModel<HivemindTentaclesEntity> {
	@Override
	public Identifier getAnimationResource(HivemindTentaclesEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "flesh_tentacles");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "flesh_tentacles");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(HivemindTentaclesEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
