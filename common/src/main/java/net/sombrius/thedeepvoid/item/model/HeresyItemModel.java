package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.HeresyItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class HeresyItemModel extends GeoModel<HeresyItem> {
	@Override
	public Identifier getAnimationResource(HeresyItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "heresyarms");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "heresyarms");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/heresynew.png");
	}
}
