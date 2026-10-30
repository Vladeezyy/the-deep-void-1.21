package net.sombrius.thedeepvoid.fabric.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.BlockEvent;

/** NeoForge's BlockEvent.EntityPlaceEvent for blocks placed from an item; canceling it puts the old block and the item back */
@Mixin(BlockItem.class)
abstract class BlockItemMixin {
	@Unique
	private BlockState deepvoid$replaced;

	@Inject(method = "place", at = @At("HEAD"))
	private void deepvoid$beforePlace(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
		this.deepvoid$replaced = context.getLevel().getBlockState(context.getClickedPos());
	}

	@Inject(method = "place", at = @At("RETURN"), cancellable = true)
	private void deepvoid$placed(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
		BlockState replaced = this.deepvoid$replaced;
		this.deepvoid$replaced = null;
		Level level = context.getLevel();
		if (level.isClientSide() || !cir.getReturnValue().consumesAction() || replaced == null)
			return;
		BlockPos pos = context.getClickedPos();
		BlockState against = level.getBlockState(pos.relative(context.getClickedFace().getOpposite()));
		if (ModBus.post(new BlockEvent.EntityPlaceEvent(level, pos, level.getBlockState(pos), against, context.getPlayer()))) {
			level.setBlock(pos, replaced, 3);
			if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild)
				context.getItemInHand().grow(1);
			cir.setReturnValue(InteractionResult.FAIL);
		}
	}
}
