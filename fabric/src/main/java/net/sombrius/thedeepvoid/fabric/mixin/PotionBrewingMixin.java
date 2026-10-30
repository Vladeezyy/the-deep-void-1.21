package net.sombrius.thedeepvoid.fabric.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.ItemStack;

import net.sombrius.thedeepvoid.fabric.FabricBrewing;

/** NeoForge's brewing recipe registry: the mod's code recipes after vanilla's mixes */
@Mixin(PotionBrewing.class)
abstract class PotionBrewingMixin {
	@Inject(method = "isIngredient", at = @At("RETURN"), cancellable = true)
	private void deepvoid$isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue() && FabricBrewing.isIngredient(stack))
			cir.setReturnValue(true);
	}

	/** {@code hasMix(input bottle, ingredient)} */
	@Inject(method = "hasMix", at = @At("RETURN"), cancellable = true)
	private void deepvoid$hasMix(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue() && !FabricBrewing.output(input, ingredient).isEmpty())
			cir.setReturnValue(true);
	}

	/** {@code mix(ingredient, input bottle)}: vanilla returns the bottle itself when it has no mix */
	@Inject(method = "mix", at = @At("RETURN"), cancellable = true)
	private void deepvoid$mix(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
		if (cir.getReturnValue() == input) {
			ItemStack output = FabricBrewing.output(input, ingredient);
			if (!output.isEmpty())
				cir.setReturnValue(output);
		}
	}
}
