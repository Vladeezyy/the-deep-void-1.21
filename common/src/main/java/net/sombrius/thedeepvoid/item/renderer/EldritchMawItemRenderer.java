package net.sombrius.thedeepvoid.item.renderer;

import net.sombrius.thedeepvoid.port.GeoArmsItemRenderer;
import net.sombrius.thedeepvoid.item.model.EldritchMawItemModel;
import net.sombrius.thedeepvoid.item.EldritchMawItem;

public class EldritchMawItemRenderer extends GeoArmsItemRenderer<EldritchMawItem> {
	public EldritchMawItemRenderer() {
		super(new EldritchMawItemModel());
	}
}
