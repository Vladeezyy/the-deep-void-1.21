package net.sombrius.thedeepvoid.entity.model;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.GeoModel;

import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.GeoTextures;
import net.sombrius.thedeepvoid.entity.WatcherBlockadeEntity;

import javax.annotation.Nullable;

public class WatcherBlockadeModel extends GeoModel<WatcherBlockadeEntity> {
	@Override
	public Identifier getAnimationResource(WatcherBlockadeEntity entity) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "boneblockade");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "boneblockade");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return GeoTextures.get(renderState);
	}

	@Override
	public void addAdditionalStateData(WatcherBlockadeEntity entity, @Nullable Object relatedObject, GeoRenderState renderState) {
		GeoTextures.set(renderState, entity.getTexture());
	}
}
