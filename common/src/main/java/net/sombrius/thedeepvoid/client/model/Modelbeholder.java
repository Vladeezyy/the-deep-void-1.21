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

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelbeholder<T extends net.minecraft.client.renderer.entity.state.EntityRenderState> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_deep_void", "modelbeholder"), "main");
	public final ModelPart stem;
	public final ModelPart eye;

	public Modelbeholder(ModelPart root) {
		super(root);
		this.stem = root.getChild("stem");
		this.eye = root.getChild("eye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition stem = partdefinition.addOrReplaceChild("stem",
				CubeListBuilder.create().texOffs(24, 14).addBox(-1.0F, -17.0F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(8, 26).addBox(-1.5F, -18.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition eye = partdefinition.addOrReplaceChild("eye",
				CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -11.0F, -12.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 14).addBox(-3.5F, -11.0F, -5.0F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 7.0F, 0.0F));
		PartDefinition cube_r1 = eye.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public void setupAnim(T state) {
		super.setupAnim(state);
		float limbSwing = net.sombrius.thedeepvoid.port.PortRender.limbSwing(state);
		float limbSwingAmount = net.sombrius.thedeepvoid.port.PortRender.limbSwingAmount(state);
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = net.sombrius.thedeepvoid.port.PortRender.netHeadYaw(state);
		float headPitch = net.sombrius.thedeepvoid.port.PortRender.headPitch(state);
		this.eye.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.eye.xRot = headPitch / (180F / (float) Math.PI);
	}
}
