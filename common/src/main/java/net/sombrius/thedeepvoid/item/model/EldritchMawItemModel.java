package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.EldritchMawItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class EldritchMawItemModel extends GeoModel<EldritchMawItem> {
	@Override
	public Identifier getAnimationResource(EldritchMawItem animatable) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "eldritchmawarms");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "eldritchmawarms");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/eldritchmouth.png");
	}
}
