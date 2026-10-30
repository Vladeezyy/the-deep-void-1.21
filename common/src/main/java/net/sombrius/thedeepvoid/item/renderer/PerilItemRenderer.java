package net.sombrius.thedeepvoid.item.renderer;

import net.sombrius.thedeepvoid.port.GeoArmsItemRenderer;
import net.sombrius.thedeepvoid.item.model.PerilItemModel;
import net.sombrius.thedeepvoid.item.PerilItem;

public class PerilItemRenderer extends GeoArmsItemRenderer<PerilItem> {
	public PerilItemRenderer() {
		super(new PerilItemModel());
	}
}
