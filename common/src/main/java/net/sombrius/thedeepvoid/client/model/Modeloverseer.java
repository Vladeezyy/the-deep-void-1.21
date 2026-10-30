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

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modeloverseer<T extends net.minecraft.client.renderer.entity.state.EntityRenderState> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_deep_void", "modeloverseer"), "main");
	public final ModelPart pillar;
	public final ModelPart left_leg1;
	public final ModelPart left_leg2;
	public final ModelPart right_leg1;
	public final ModelPart right_leg2;

	public Modeloverseer(ModelPart root) {
		super(root);
		this.pillar = root.getChild("pillar");
		this.left_leg1 = root.getChild("left_leg1");
		this.left_leg2 = root.getChild("left_leg2");
		this.right_leg1 = root.getChild("right_leg1");
		this.right_leg2 = root.getChild("right_leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition pillar = partdefinition.addOrReplaceChild("pillar", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition cube_r1 = pillar.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(69, 18).addBox(-8.0F, 0.0F, -1.0F, 16.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, -0.0873F, 0.0F, 0.0F));
		PartDefinition cube_r2 = pillar.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(69, -7).addBox(0.0F, 0.0F, -8.0F, 0.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));
		PartDefinition cube_r3 = pillar.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(69, -16).addBox(0.0F, 0.0F, -8.0F, 0.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
		PartDefinition cube_r4 = pillar.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -14.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 0.5236F, 0.0F, 0.0F));
		PartDefinition left_leg1 = partdefinition.addOrReplaceChild("left_leg1", CubeListBuilder.create(), PartPose.offset(5.0F, 10.0F, -1.0F));
		PartDefinition cube_r5 = left_leg1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 66).addBox(0.0F, -16.0F, -1.0F, 26.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, 6.0F, 1.0F, 0.0F, 1.0036F, 0.0F));
		PartDefinition left_leg2 = partdefinition.addOrReplaceChild("left_leg2", CubeListBuilder.create(), PartPose.offset(5.0F, 10.0F, 8.0F));
		PartDefinition cube_r6 = left_leg2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 66).addBox(0.0F, -16.0F, 1.0F, 26.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, 6.0F, -1.0F, 0.0F, -1.0036F, 0.0F));
		PartDefinition right_leg1 = partdefinition.addOrReplaceChild("right_leg1", CubeListBuilder.create(), PartPose.offset(-5.0F, 10.0F, -1.0F));
		PartDefinition cube_r7 = right_leg1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 66).mirror().addBox(-26.0F, -16.0F, -1.0F, 26.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-1.0F, 6.0F, 1.0F, 0.0F, -1.0036F, 0.0F));
		PartDefinition right_leg2 = partdefinition.addOrReplaceChild("right_leg2", CubeListBuilder.create(), PartPose.offset(-5.0F, 10.0F, 8.0F));
		PartDefinition cube_r8 = right_leg2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 66).mirror().addBox(-26.0F, -16.0F, 1.0F, 26.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-1.0F, 6.0F, -1.0F, 0.0F, 1.0036F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	public void setupAnim(T state) {
		super.setupAnim(state);
		float limbSwing = net.sombrius.thedeepvoid.port.PortRender.limbSwing(state);
		float limbSwingAmount = net.sombrius.thedeepvoid.port.PortRender.limbSwingAmount(state);
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = net.sombrius.thedeepvoid.port.PortRender.netHeadYaw(state);
		float headPitch = net.sombrius.thedeepvoid.port.PortRender.headPitch(state);
		this.right_leg1.zRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.right_leg2.zRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.left_leg1.zRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.left_leg2.zRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
	}
}
