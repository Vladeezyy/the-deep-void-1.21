package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.DoubleBarrelShotgunItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DoubleBarrelShotgunItemModel extends GeoModel<DoubleBarrelShotgunItem> {
	@Override
	public Identifier getAnimationResource(DoubleBarrelShotgunItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "spitearms");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "spitearms");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/strife.png");
	}
}
