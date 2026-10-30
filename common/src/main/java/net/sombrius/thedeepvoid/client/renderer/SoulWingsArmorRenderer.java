package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.item.SoulWingsItem;
import net.sombrius.thedeepvoid.item.model.SoulWingsModel;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class SoulWingsArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<SoulWingsItem, R> {
	public SoulWingsArmorRenderer() {
		super(new SoulWingsModel());
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
