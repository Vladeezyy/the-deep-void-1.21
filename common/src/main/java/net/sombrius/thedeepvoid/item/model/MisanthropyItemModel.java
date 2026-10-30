package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.MisanthropyItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MisanthropyItemModel extends GeoModel<MisanthropyItem> {
	@Override
	public Identifier getAnimationResource(MisanthropyItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "misanthropyarms");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "misanthropyarms");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/misanthropy.png");
	}
}
