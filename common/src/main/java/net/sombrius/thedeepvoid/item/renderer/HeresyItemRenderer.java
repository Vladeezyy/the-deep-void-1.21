package net.sombrius.thedeepvoid.item.renderer;

import net.sombrius.thedeepvoid.port.GeoArmsItemRenderer;
import net.sombrius.thedeepvoid.item.model.HeresyItemModel;
import net.sombrius.thedeepvoid.item.HeresyItem;

public class HeresyItemRenderer extends GeoArmsItemRenderer<HeresyItem> {
	public HeresyItemRenderer() {
		super(new HeresyItemModel());
	}
}
