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

// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelbig_shade_arrow<T extends net.minecraft.client.renderer.entity.state.EntityRenderState> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_deep_void", "modelbig_shade_arrow"), "main");
	public final ModelPart main;

	public Modelbig_shade_arrow(ModelPart root) {
		super(root);
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main",
				CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -24.0F, 0.0F, 15.0F, 28.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, -15).addBox(0.0F, -24.0F, -7.5F, 0.0F, 28.0F, 15.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
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
