package net.sombrius.thedeepvoid.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelonlooker<T extends net.minecraft.client.renderer.entity.state.EntityRenderState> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_deep_void", "modelonlooker"), "main");
	public final ModelPart left_leg;
	public final ModelPart right_leg;
	public final ModelPart body;
	public final ModelPart head;
	public final ModelPart left_arm;
	public final ModelPart right_arm;

	public Modelonlooker(ModelPart root) {
		super(root);
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(4.0F, -20.0F, 1.0F));
		PartDefinition cube_r1 = left_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(84, 0).addBox(-1.5F, -19.0F, -2.0F, 5.0F, 19.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, 14.0F, -8.0F, -0.48F, 0.0F, 0.0F));
		PartDefinition cube_r2 = left_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(60, 54).addBox(0.0F, 1.0F, -8.9F, 0.0F, 32.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 13.0F, -11.0F, 0.1745F, 0.0F, 0.0F));
		PartDefinition cube_r3 = left_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 83).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 32.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 12.0F, -8.0F, 0.1745F, 0.0F, 0.0F));
		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, -20.0F, 1.0F));
		PartDefinition cube_r4 = right_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, 1.0F, -8.9F, 0.0F, 32.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 13.0F, -11.0F, 0.1745F, 0.0F, 0.0F));
		PartDefinition cube_r5 = right_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(88, 24).addBox(-3.5F, -19.0F, -2.0F, 5.0F, 19.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, 14.0F, -8.0F, -0.48F, 0.0F, 0.0F));
		PartDefinition cube_r6 = right_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(32, 83).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 32.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 12.0F, -8.0F, 0.1745F, 0.0F, 0.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 3.0F));
		PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 54).addBox(-6.0F, -20.0F, -3.0F, 12.0F, 23.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -10.0F, -9.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 24).addBox(-6.0F, 2.0F, -9.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -43.0F, -4.0F));
		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm",
				CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 50.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(52, 54).addBox(0.0F, -2.0F, -6.0F, 0.0F, 50.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, -41.0F, -2.0F));
		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm",
				CubeListBuilder.create().texOffs(48, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 50.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(80, 42).addBox(0.0F, -2.0F, -6.0F, 0.0F, 50.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, -41.0F, -2.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	public void setupAnim(T state) {
		super.setupAnim(state);
		float limbSwing = net.sombrius.thedeepvoid.port.PortRender.limbSwing(state);
		float limbSwingAmount = net.sombrius.thedeepvoid.port.PortRender.limbSwingAmount(state);
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = net.sombrius.thedeepvoid.port.PortRender.netHeadYaw(state);
		float headPitch = net.sombrius.thedeepvoid.port.PortRender.headPitch(state);
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.left_leg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.right_leg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
	}
}
