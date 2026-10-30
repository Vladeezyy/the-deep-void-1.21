package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.PerilOffHandItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class PerilOffHandItemModel extends GeoModel<PerilOffHandItem> {
	@Override
	public Identifier getAnimationResource(PerilOffHandItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "perilarms");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "perilarms");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/peril.png");
	}
}
