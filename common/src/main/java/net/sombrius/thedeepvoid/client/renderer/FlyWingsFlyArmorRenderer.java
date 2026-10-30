package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.FlyWingsFlyItem;
import net.sombrius.thedeepvoid.item.model.FlyWingsFlyModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class FlyWingsFlyArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<FlyWingsFlyItem, R> {
	public FlyWingsFlyArmorRenderer() {
		super(new FlyWingsFlyModel());
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
