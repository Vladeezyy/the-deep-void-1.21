package net.sombrius.thedeepvoid.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;

import net.sombrius.thedeepvoid.port.PortBlockHooks;

/** NeoForge's onDestroyedByPlayer: the mod's blocks remove themselves for a player (server) */
@Mixin(ServerPlayerGameMode.class)
abstract class ServerPlayerGameModeMixin {
	@Shadow
	@Final
	protected ServerPlayer player;

	@WrapOperation(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
	private boolean deepvoid$destroyedByPlayer(net.minecraft.server.level.ServerLevel level, BlockPos pos, boolean isMoving, Operation<Boolean> original) {
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof PortBlockHooks.DestroyedByPlayer block)
			return block.onDestroyedByPlayer(state, level, pos, this.player, this.player.hasCorrectToolForDrops(state), level.getFluidState(pos));
		return original.call(level, pos, isMoving);
	}
}
