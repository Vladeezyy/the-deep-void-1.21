package net.sombrius.thedeepvoid.port;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

/**
 * GeckoLib 5 renderer of MCreator's first person weapons (GeckoLib 4 renderRecursively in each item renderer): the
 * RightArm / LeftArm bones are hidden and, in first person, show the player's left / right arm with its sleeve.
 * The arms come from an own baked player model (posed once), so the player renderer's model is not touched.
 */
public class GeoArmsItemRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
	private static final float PIXEL = 1f / 16f;
	private PlayerModel wideArms;
	private PlayerModel slimArms;

	public GeoArmsItemRenderer(GeoModel<T> model) {
		super(model);
	}

	@Override
	public RenderType getRenderType(GeoRenderState renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}

	@Override
	public void adjustModelBonesForRender(RenderPassInfo<GeoRenderState> renderPassInfo, BoneSnapshots snapshots) {
		snapshots.ifPresent("RightArm", bone -> bone.skipRender(true).skipChildrenRender(true));
		snapshots.ifPresent("LeftArm", bone -> bone.skipRender(true).skipChildrenRender(true));
	}

	@Override
	public void preRenderPass(RenderPassInfo<GeoRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
		super.preRenderPass(renderPassInfo, renderTasks);
		ItemDisplayContext context = renderPassInfo.getGeckolibData(DataTickets.ITEM_RENDER_PERSPECTIVE);
		LocalPlayer player = Minecraft.getInstance().player;
		if (context == null || !context.firstPerson() || player == null)
			return;
		PlayerModel model = arms(player.getSkin().model() == PlayerModelType.SLIM);
		model.leftSleeve.visible = player.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
		model.rightSleeve.visible = player.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
		RenderType type = RenderTypes.entityTranslucent(player.getSkin().body().texturePath());
		int color = player.isInvisible() ? ARGB.white(0.15f) : -1;
		int light = renderPassInfo.packedLight();
		// MCreator's models: the RightArm bone holds the player's left arm and the other way round
		submitArm(renderPassInfo, "RightArm", model.leftArm, -PIXEL, type, light, color);
		submitArm(renderPassInfo, "LeftArm", model.rightArm, PIXEL, type, light, color);
	}

	private static void submitArm(RenderPassInfo<GeoRenderState> renderPassInfo, String boneName, ModelPart arm, float x, RenderType type, int light, int color) {
		renderPassInfo.model().getBone(boneName).ifPresent(bone -> renderPassInfo.addPerBoneRender(bone, (info, b, tasks) -> {
			info.poseStack().translate(x, 2 * PIXEL, 0);
			tasks.submitModelPart(arm, info.poseStack(), type, light, OverlayTexture.NO_OVERLAY, null, color, null);
		}));
	}

	private PlayerModel arms(boolean slim) {
		PlayerModel model = slim ? this.slimArms : this.wideArms;
		if (model == null) {
			model = new PlayerModel(Minecraft.getInstance().getEntityModels().bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
			// the bone is posed at its pivot: the arm starts there, unrotated
			for (ModelPart arm : new ModelPart[]{model.leftArm, model.rightArm}) {
				arm.setPos(0, 0, 0);
				arm.setRotation(0, 0, 0);
			}
			if (slim)
				this.slimArms = model;
			else
				this.wideArms = model;
		}
		return model;
	}
}
