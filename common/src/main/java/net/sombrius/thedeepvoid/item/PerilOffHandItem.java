
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.neo.IClientItemExtensions;
import net.sombrius.thedeepvoid.item.renderer.PerilOffHandItemRenderer;
import net.sombrius.thedeepvoid.procedures.PerilOffHandItemInInventoryTickProcedure;
import net.sombrius.thedeepvoid.procedures.PerilOffHandRightclickedProcedure;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PerilOffHandItem extends Item implements GeoItem, net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Item {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public String animationprocedure = "empty";
	public static ItemDisplayContext transformType;

	public PerilOffHandItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).fireResistant().rarity(Rarity.COMMON));
	}

	@Override
	public boolean isPerspectiveAware() {
		return true;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private PerilOffHandItemRenderer renderer;

			@Override
			public GeoItemRenderer<?> getGeoItemRenderer() {
				if (this.renderer == null)
					this.renderer = new PerilOffHandItemRenderer();
				return this.renderer;
			}
		});
	}

	public IClientItemExtensions clientExtensions() {
		return new IClientItemExtensions() {
			private static final HumanoidModel.ArmPose PerilOffHandPose = net.sombrius.thedeepvoid.port.PortArmPoses.pose("PERIL_OFF_HAND");

			@Override
			public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
				if (!itemStack.isEmpty()) {
					if (entityLiving.getUsedItemHand() == hand) {
						return PerilOffHandPose;
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

	private PlayState idlePredicate(AnimationTest<PerilOffHandItem> event) {
		if (net.sombrius.thedeepvoid.port.GeoAnims.firstPerson(event)) {
			if (this.animationprocedure.equals("empty")) {
				event.controller().setAnimation(RawAnimation.begin().thenLoop("animation.peril_idle"));
				return PlayState.CONTINUE;
			}
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<PerilOffHandItem> event) {
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
		AnimationController<PerilOffHandItem> procedureController = new AnimationController<>("procedureController", 0, this::procedurePredicate);
		data.add(procedureController);
		AnimationController<PerilOffHandItem> idleController = new AnimationController<>("idleController", 0, this::idlePredicate);
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

		PerilOffHandRightclickedProcedure.execute(world, entity, itemstack);
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		PerilOffHandItemInInventoryTickProcedure.execute(entity, itemstack);
	}
}
