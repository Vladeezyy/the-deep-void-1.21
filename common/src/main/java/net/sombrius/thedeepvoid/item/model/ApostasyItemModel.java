package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.ApostasyItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ApostasyItemModel extends GeoModel<ApostasyItem> {
	@Override
	public Identifier getAnimationResource(ApostasyItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "apostasyarms");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "apostasyarms");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/apostasynew.png");
	}
}
