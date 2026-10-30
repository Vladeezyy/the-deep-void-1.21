package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.ChainedWeaverEntity;

import javax.annotation.Nullable;

public class ChainedWeaverModel extends GeoModel<ChainedWeaverEntity> {
	@Override
	public Identifier getAnimationResource(ChainedWeaverEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "chainedweaver");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "chainedweaver");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(ChainedWeaverEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
