package net.sombrius.thedeepvoid.item.renderer;

import net.sombrius.thedeepvoid.port.GeoArmsItemRenderer;
import net.sombrius.thedeepvoid.item.model.MisanthropyItemModel;
import net.sombrius.thedeepvoid.item.MisanthropyItem;

public class MisanthropyItemRenderer extends GeoArmsItemRenderer<MisanthropyItem> {
	public MisanthropyItemRenderer() {
		super(new MisanthropyItemModel());
	}
}
