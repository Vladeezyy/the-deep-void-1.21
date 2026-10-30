package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.ApostleCloneEntity;

import javax.annotation.Nullable;

public class ApostleCloneModel extends GeoModel<ApostleCloneEntity> {
	@Override
	public Identifier getAnimationResource(ApostleCloneEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "apostleofcatastrophe");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "apostleofcatastrophe");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(ApostleCloneEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
