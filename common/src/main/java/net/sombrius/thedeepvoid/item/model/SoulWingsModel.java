package net.sombrius.thedeepvoid.item.model;

import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.SoulWingsItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class SoulWingsModel extends GeoModel<SoulWingsItem> {
	@Override
	public Identifier getAnimationResource(SoulWingsItem object) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "soulwings");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "soulwings");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.fromNamespaceAndPath("the_deep_void", "textures/item/soul_wings.png");
	}
}
