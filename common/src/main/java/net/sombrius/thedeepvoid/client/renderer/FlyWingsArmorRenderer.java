package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.FlyWingsItem;
import net.sombrius.thedeepvoid.item.model.FlyWingsModel;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class FlyWingsArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<FlyWingsItem, R> {
	public FlyWingsArmorRenderer() {
		super(new FlyWingsModel());
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
