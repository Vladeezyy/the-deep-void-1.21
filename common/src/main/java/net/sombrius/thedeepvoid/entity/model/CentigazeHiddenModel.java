package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.CentigazeHiddenEntity;

import javax.annotation.Nullable;

public class CentigazeHiddenModel extends GeoModel<CentigazeHiddenEntity> {
	@Override
	public Identifier getAnimationResource(CentigazeHiddenEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "eyecentipede");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "eyecentipede");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(CentigazeHiddenEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
