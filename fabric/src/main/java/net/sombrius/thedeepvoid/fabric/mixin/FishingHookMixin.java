package net.sombrius.thedeepvoid.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

import net.sombrius.thedeepvoid.port.bus.ItemFishedEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/** NeoForge's ItemFishedEvent: the catch of a reeled-in hook, before it spawns */
@Mixin(FishingHook.class)
abstract class FishingHookMixin {
	@Shadow
	public abstract Player getPlayerOwner();

	@ModifyExpressionValue(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
	private ObjectArrayList<ItemStack> deepvoid$fished(ObjectArrayList<ItemStack> drops) {
		Player player = this.getPlayerOwner();
		if (player != null)
			ModBus.post(new ItemFishedEvent(player, drops));
		return drops;
	}
}
