package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.FalseHydraArmEntity;

import javax.annotation.Nullable;

public class FalseHydraArmModel extends GeoModel<FalseHydraArmEntity> {
	@Override
	public Identifier getAnimationResource(FalseHydraArmEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "falsehydraarm");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "falsehydraarm");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(FalseHydraArmEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
