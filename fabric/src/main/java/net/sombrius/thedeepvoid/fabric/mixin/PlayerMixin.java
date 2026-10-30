package net.sombrius.thedeepvoid.fabric.mixin;

import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.Local;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;

import net.sombrius.thedeepvoid.port.bus.PlayerXpEvent;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.ItemTossEvent;

/** NeoForge's PlayerTickEvent.Post (both sides), ItemTossEvent and PlayerXpEvent.XpChange */
@Mixin(Player.class)
abstract class PlayerMixin {
	@Inject(method = "tick", at = @At("TAIL"))
	private void deepvoid$tick(CallbackInfo ci) {
		ModBus.post(new PlayerTickEvent.Post((Player) (Object) this));
	}

	@Inject(method = "giveExperiencePoints", at = @At("HEAD"), cancellable = true)
	private void deepvoid$xpChange(int xpPoints, CallbackInfo ci, @Local(argsOnly = true) LocalIntRef amount) {
		PlayerXpEvent.XpChange event = new PlayerXpEvent.XpChange((Player) (Object) this, xpPoints);
		if (ModBus.post(event)) {
			ci.cancel();
			return;
		}
		amount.set(event.getAmount());
	}
}
