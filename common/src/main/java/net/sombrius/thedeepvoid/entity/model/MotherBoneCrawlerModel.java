package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.MotherBoneCrawlerEntity;

import javax.annotation.Nullable;

public class MotherBoneCrawlerModel extends GeoModel<MotherBoneCrawlerEntity> {
	@Override
	public Identifier getAnimationResource(MotherBoneCrawlerEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "mothercrawlerremodel");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "mothercrawlerremodel");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(MotherBoneCrawlerEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
