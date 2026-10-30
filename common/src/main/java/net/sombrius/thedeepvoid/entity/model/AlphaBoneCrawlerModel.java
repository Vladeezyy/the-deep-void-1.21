package net.sombrius.thedeepvoid.entity.model;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.AlphaBoneCrawlerEntity;

import javax.annotation.Nullable;

public class AlphaBoneCrawlerModel extends GeoModel<AlphaBoneCrawlerEntity> {
	@Override
	public Identifier getAnimationResource(AlphaBoneCrawlerEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "alphacrawlerremodel");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "alphacrawlerremodel");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(AlphaBoneCrawlerEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
