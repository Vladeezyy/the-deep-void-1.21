package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.BabyBoneCrawlerEntity;

import javax.annotation.Nullable;

public class BabyBoneCrawlerModel extends GeoModel<BabyBoneCrawlerEntity> {
	@Override
	public Identifier getAnimationResource(BabyBoneCrawlerEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/babycrawlerremodel.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/babycrawlerremodel.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(BabyBoneCrawlerEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
