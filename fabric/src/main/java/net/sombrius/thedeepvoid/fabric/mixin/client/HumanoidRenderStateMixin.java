package net.sombrius.thedeepvoid.fabric.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

import net.sombrius.thedeepvoid.fabric.FabricArmPoses;

/** The mod's arm pose names of the frame (FabricArmPoses) */
@Mixin(HumanoidRenderState.class)
abstract class HumanoidRenderStateMixin implements FabricArmPoses.PoseState {
	@Unique
	private final String[] deepvoid$poses = new String[2];

	@Override
	public String[] deepvoid$poses() {
		return this.deepvoid$poses;
	}
}
