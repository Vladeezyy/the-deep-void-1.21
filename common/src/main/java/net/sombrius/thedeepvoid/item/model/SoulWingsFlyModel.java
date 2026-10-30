package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.SoulWingsFlyItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class SoulWingsFlyModel extends GeoModel<SoulWingsFlyItem> {
	@Override
	public Identifier getAnimationResource(SoulWingsFlyItem object) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "animations/soulwings.animation.json");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "geo/soulwings.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/soul_wings.png");
	}
}
