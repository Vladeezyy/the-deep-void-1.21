package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.BoneCrawlerEntity;

import javax.annotation.Nullable;

public class BoneCrawlerModel extends GeoModel<BoneCrawlerEntity> {
	@Override
	public Identifier getAnimationResource(BoneCrawlerEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "bonecrawlerremodel");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "bonecrawlerremodel");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(BoneCrawlerEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
