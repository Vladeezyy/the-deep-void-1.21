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

// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modeldark_throw_knife<T extends net.minecraft.client.renderer.entity.state.EntityRenderState> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_deep_void", "modeldark_throw_knife"), "main");
	public final ModelPart main;

	public Modeldark_throw_knife(ModelPart root) {
		super(root);
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main",
				CubeListBuilder.create().texOffs(6, 7).addBox(1.7574F, -1.0F, -3.7574F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(7, 0).addBox(-2.2426F, -1.0F, 0.2426F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-0.2426F, -1.0F, -1.7574F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(4, 10).addBox(-1.2426F, -1.0F, -1.7574F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(5, 4)
						.addBox(-4.2426F, -1.0F, 2.2426F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(11, 3).addBox(-2.2426F, -1.0F, 2.2426F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 8)
						.addBox(-3.2426F, -1.0F, 0.2426F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 13).addBox(-4.2426F, -1.0F, 1.2426F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
						.addBox(-5.2426F, -1.0F, 2.2426F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(9, 12).addBox(-2.2426F, -1.0F, -0.7574F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 6)
						.addBox(-0.2426F, -1.0F, 1.2426F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(-2.2426F, -1.0F, 3.2426F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(8, 10)
						.addBox(-4.2426F, -1.0F, 4.2426F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 13).addBox(1.7574F, -1.0F, -1.7574F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(13, 0)
						.addBox(0.7574F, -1.0F, -2.7574F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 21.0F, -1.0F, -1.5708F, 0.0F, 2.3562F));
		return LayerDefinition.create(meshdefinition, 32, 32);
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
