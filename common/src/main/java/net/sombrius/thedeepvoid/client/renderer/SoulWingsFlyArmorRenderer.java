package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.SoulWingsFlyItem;
import net.sombrius.thedeepvoid.item.model.SoulWingsFlyModel;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class SoulWingsFlyArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<SoulWingsFlyItem, R> {
	public SoulWingsFlyArmorRenderer() {
		super(new SoulWingsFlyModel());
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
