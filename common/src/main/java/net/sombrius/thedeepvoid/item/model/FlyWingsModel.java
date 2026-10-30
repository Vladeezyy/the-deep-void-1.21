package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.FlyWingsItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class FlyWingsModel extends GeoModel<FlyWingsItem> {
	@Override
	public Identifier getAnimationResource(FlyWingsItem object) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "flywings");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "flywings");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/flywings.png");
	}
}
