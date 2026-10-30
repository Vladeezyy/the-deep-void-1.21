package net.sombrius.thedeepvoid.fabric.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.Minecraft;

import net.sombrius.thedeepvoid.port.PortBlockHooks;

/** NeoForge's onDestroyedByPlayer on the client (the mod's blocks remove themselves for the player) */
@Mixin(MultiPlayerGameMode.class)
abstract class MultiPlayerGameModeMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@WrapOperation(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private boolean deepvoid$destroyedByPlayer(Level level, BlockPos pos, BlockState newState, int flags, Operation<Boolean> original) {
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof PortBlockHooks.DestroyedByPlayer block && this.minecraft.player != null)
			return block.onDestroyedByPlayer(state, level, pos, this.minecraft.player, false, level.getFluidState(pos));
		return original.call(level, pos, newState, flags);
	}
}
