package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sombrius.thedeepvoid.entity.BeholderEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BeholderModel extends GeoModel<BeholderEntity> {
	@Override
	public ResourceLocation getAnimationResource(BeholderEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/beholder.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BeholderEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/beholder.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BeholderEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(BeholderEntity animatable, long instanceId, AnimationState animationState) {
		GeoBone head = getAnimationProcessor().getBone("eye");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
