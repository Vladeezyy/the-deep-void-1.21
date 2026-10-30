package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.ManiacEntity;

import javax.annotation.Nullable;

public class ManiacModel extends GeoModel<ManiacEntity> {
	@Override
	public Identifier getAnimationResource(ManiacEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "maniac");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "maniac");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(ManiacEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
