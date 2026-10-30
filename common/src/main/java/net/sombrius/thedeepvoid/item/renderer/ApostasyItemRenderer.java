package net.sombrius.thedeepvoid.item.renderer;

import net.sombrius.thedeepvoid.port.GeoArmsItemRenderer;
import net.sombrius.thedeepvoid.item.model.ApostasyItemModel;
import net.sombrius.thedeepvoid.item.ApostasyItem;

public class ApostasyItemRenderer extends GeoArmsItemRenderer<ApostasyItem> {
	public ApostasyItemRenderer() {
		super(new ApostasyItemModel());
	}
}
