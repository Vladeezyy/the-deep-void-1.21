package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.SmallFleshCubeEntity;

import javax.annotation.Nullable;

public class SmallFleshCubeModel extends GeoModel<SmallFleshCubeEntity> {
	@Override
	public Identifier getAnimationResource(SmallFleshCubeEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "smallfleshcube");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "smallfleshcube");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(SmallFleshCubeEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
