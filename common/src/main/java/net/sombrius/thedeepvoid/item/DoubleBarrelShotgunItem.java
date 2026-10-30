
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.sombrius.thedeepvoid.procedures.DoubleBarrelShotgunRightClickProcedure;
import net.sombrius.thedeepvoid.item.renderer.DoubleBarrelShotgunItemRenderer;

import net.sombrius.thedeepvoid.port.neo.IClientItemExtensions;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.model.HumanoidModel;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

public class DoubleBarrelShotgunItem extends Item implements GeoItem, net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Item {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public String animationprocedure = "empty";
	public static ItemDisplayContext transformType;

	public DoubleBarrelShotgunItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).fireResistant().rarity(Rarity.COMMON));
	}

	@Override
	public boolean isPerspectiveAware() {
		return true;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private DoubleBarrelShotgunItemRenderer renderer;

			@Override
			public GeoItemRenderer<?> getGeoItemRenderer() {
				if (this.renderer == null)
					this.renderer = new DoubleBarrelShotgunItemRenderer();
				return this.renderer;
			}
		});
	}

	public IClientItemExtensions clientExtensions() {
		return new IClientItemExtensions() {
			private static final HumanoidModel.ArmPose DoubleBarrelShotgunPose = net.sombrius.thedeepvoid.port.PortArmPoses.pose("DOUBLE_BARREL_SHOTGUN");

			@Override
			public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
				if (!itemStack.isEmpty()) {
					if (entityLiving.getUsedItemHand() == hand) {
						return DoubleBarrelShotgunPose;
					}
				}
				return HumanoidModel.ArmPose.EMPTY;
			}

			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
				int i = arm == HumanoidArm.RIGHT ? 1 : -1;
				poseStack.translate(i * 0.56F, -0.52F, -0.72F);
				if (player.getUseItem() == itemInHand) {
					poseStack.translate(0.05, 0.05, 0.05);
				}
				return true;
			}
		};
	}

	public void getTransformType(ItemDisplayContext type) {
		this.transformType = type;
	}

	private PlayState idlePredicate(AnimationTest<DoubleBarrelShotgunItem> event) {
		if (net.sombrius.thedeepvoid.port.GeoAnims.firstPerson(event)) {
			if (this.animationprocedure.equals("empty")) {
				event.controller().setAnimation(RawAnimation.begin().thenLoop("animation.strife_idle"));
				return PlayState.CONTINUE;
			}
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<DoubleBarrelShotgunItem> event) {
		if (net.sombrius.thedeepvoid.port.GeoAnims.firstPerson(event)) {
			if (!this.animationprocedure.equals("empty") && net.sombrius.thedeepvoid.port.GeoAnims.stopped(event.controller()) || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
				if (!this.animationprocedure.equals(prevAnim))
					event.controller().reset();
				event.controller().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
				if (net.sombrius.thedeepvoid.port.GeoAnims.stopped(event.controller())) {
					this.animationprocedure = "empty";
					event.controller().reset();
				}
			} else if (this.animationprocedure.equals("empty")) {
				prevAnim = "empty";
				return PlayState.STOP;
			}
		}
		prevAnim = this.animationprocedure;
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		AnimationController<DoubleBarrelShotgunItem> procedureController = new AnimationController<>("procedureController", 0, this::procedurePredicate);
		data.add(procedureController);
		AnimationController<DoubleBarrelShotgunItem> idleController = new AnimationController<>("idleController", 0, this::idlePredicate);
		data.add(idleController);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		ItemStack itemstack = entity.getItemInHand(hand);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();

		DoubleBarrelShotgunRightClickProcedure.execute(world, entity, itemstack);
		return ar;
	}
}
