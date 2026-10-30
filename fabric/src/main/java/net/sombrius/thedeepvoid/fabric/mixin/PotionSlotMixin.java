package net.sombrius.thedeepvoid.fabric.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.ItemStack;

import net.sombrius.thedeepvoid.fabric.FabricBrewing;

/** The brewing stand's bottle slots take the inputs of the mod's recipes (NeoForge's isValidInput) */
@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$PotionSlot")
abstract class PotionSlotMixin {
	@Inject(method = "mayPlaceItem", at = @At("RETURN"), cancellable = true)
	private static void deepvoid$mayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue() && FabricBrewing.isInput(stack))
			cir.setReturnValue(true);
	}
}
