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
import net.minecraft.world.entity.Entity;

// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class ModelplagueDoctorMask<T extends net.minecraft.client.renderer.entity.state.EntityRenderState> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_deep_void", "modelplague_doctor_mask"), "main");
	public final ModelPart Head;

	public ModelplagueDoctorMask(ModelPart root) {
		super(root);
		this.Head = root.getChild("Head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Head = partdefinition.addOrReplaceChild("Head",
				CubeListBuilder.create().texOffs(0, 27).addBox(-4.5F, -8.5F, -5.0F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 37).addBox(-1.5F, -4.5F, -9.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(20, 27)
						.addBox(-4.6F, -4.5F, -4.4F, 0.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(36, 25).addBox(-4.5F, -4.5F, 4.6F, 9.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(36, 15)
						.addBox(4.6F, -4.5F, -4.4F, 0.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Head_r1 = Head.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(14, 37).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, -7.5F, 0.2618F, 0.0F, 0.0F));
		PartDefinition Head_r2 = Head.addOrReplaceChild("Head_r2",
				CubeListBuilder.create().texOffs(0, 15).addBox(-4.5F, -4.0F, -4.5F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public void setupAnim(T state) {
		super.setupAnim(state);
		float limbSwing = net.sombrius.thedeepvoid.port.PortRender.limbSwing(state);
		float limbSwingAmount = net.sombrius.thedeepvoid.port.PortRender.limbSwingAmount(state);
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = net.sombrius.thedeepvoid.port.PortRender.netHeadYaw(state);
		float headPitch = net.sombrius.thedeepvoid.port.PortRender.headPitch(state);
		this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.Head.xRot = headPitch / (180F / (float) Math.PI);
	}
}
