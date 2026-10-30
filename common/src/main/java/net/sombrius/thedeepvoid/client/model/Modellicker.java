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

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modellicker<T extends net.minecraft.client.renderer.entity.state.EntityRenderState> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_deep_void", "modellicker"), "main");
	public final ModelPart main;

	public Modellicker(ModelPart root) {
		super(root);
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main",
				CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -3.0F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(14, 22).addBox(4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(21, 0)
						.addBox(-3.0F, 0.0F, 4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 19).addBox(1.0F, 0.0F, 4.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(24, 7)
						.addBox(-3.0F, 0.0F, -7.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 25).addBox(-7.0F, 0.0F, -4.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(11, 29)
						.addBox(-5.0F, 0.0F, 4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(27, 26).addBox(4.0F, 0.0F, -3.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(15, 11)
						.addBox(-6.0F, 0.0F, -6.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(30, 13).addBox(-7.0F, 0.0F, 4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 30)
						.addBox(6.0F, 0.0F, 4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(21, 29).addBox(6.0F, 0.0F, -6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(26, 22)
						.addBox(7.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(15, 14).addBox(1.0F, 0.0F, -8.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
						.addBox(-8.0F, 0.0F, -1.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 8.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public void setupAnim(T state) {
		super.setupAnim(state);
		float limbSwing = net.sombrius.thedeepvoid.port.PortRender.limbSwing(state);
		float limbSwingAmount = net.sombrius.thedeepvoid.port.PortRender.limbSwingAmount(state);
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = net.sombrius.thedeepvoid.port.PortRender.netHeadYaw(state);
		float headPitch = net.sombrius.thedeepvoid.port.PortRender.headPitch(state);
	}
}
