package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.SleepingPrimordialCrawlerEntity;

import javax.annotation.Nullable;

public class SleepingPrimordialCrawlerModel extends GeoModel<SleepingPrimordialCrawlerEntity> {
	@Override
	public Identifier getAnimationResource(SleepingPrimordialCrawlerEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/primordialbonecrawler.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/primordialbonecrawler.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(SleepingPrimordialCrawlerEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
