package net.sombrius.thedeepvoid.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.Local;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Holder;

import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.MobEffectEvent;
import net.sombrius.thedeepvoid.port.bus.LivingShieldBlockEvent;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.port.bus.LivingHealEvent;
import net.sombrius.thedeepvoid.port.bus.LivingFallEvent;
import net.sombrius.thedeepvoid.port.bus.LivingExperienceDropEvent;
import net.sombrius.thedeepvoid.port.bus.LivingEntityUseItemEvent;
import net.sombrius.thedeepvoid.port.bus.LivingDeathEvent;
import net.sombrius.thedeepvoid.port.PortItemHooks;

/** NeoForge's living entity events, where NeoForge fires them */
@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
	@Shadow
	protected ItemStack useItem;
	@Shadow
	protected int useItemRemaining;
	@Shadow
	protected Player lastHurtByPlayer;

	@Shadow
	public abstract ItemStack getItemInHand(InteractionHand hand);

	@Shadow
	public abstract boolean isUsingItem();

	private LivingEntity deepvoid$self() {
		return (LivingEntity) (Object) this;
	}

	/** LivingIncomingDamageEvent: after vanilla's invulnerability and fire resistance checks */
	@Inject(method = "hurt", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;noActionTime:I", opcode = Opcodes.PUTFIELD), cancellable = true)
	private void deepvoid$incomingDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (ModBus.post(new LivingIncomingDamageEvent(deepvoid$self(), source, amount)))
			cir.setReturnValue(false);
	}

	/** LivingShieldBlockEvent: a listener may let the damage through */
	@WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isDamageSourceBlocked(Lnet/minecraft/world/damagesource/DamageSource;)Z"))
	private boolean deepvoid$shieldBlock(LivingEntity self, DamageSource source, Operation<Boolean> original, @Local(argsOnly = true) float amount) {
		boolean blocked = original.call(self, source);
		if (!blocked)
			return false;
		LivingShieldBlockEvent event = new LivingShieldBlockEvent(self, source, amount, true);
		ModBus.post(event);
		return event.getBlocked() && !event.isCanceled();
	}

	@Inject(method = "die", at = @At("HEAD"), cancellable = true)
	private void deepvoid$death(DamageSource source, CallbackInfo ci) {
		if (ModBus.post(new LivingDeathEvent(deepvoid$self(), source)))
			ci.cancel();
	}

	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	private void deepvoid$heal(float healAmount, CallbackInfo ci, @Local(argsOnly = true) LocalFloatRef amount) {
		LivingHealEvent event = new LivingHealEvent(deepvoid$self(), healAmount);
		if (ModBus.post(event) || event.getAmount() <= 0) {
			ci.cancel();
			return;
		}
		amount.set(event.getAmount());
	}

	@Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
	private void deepvoid$fall(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true, ordinal = 0) LocalFloatRef distance,
			@Local(argsOnly = true, ordinal = 1) LocalFloatRef damageMultiplier) {
		LivingFallEvent event = new LivingFallEvent(deepvoid$self(), fallDistance, multiplier);
		if (ModBus.post(event)) {
			cir.setReturnValue(false);
			return;
		}
		distance.set(event.getDistance());
		damageMultiplier.set(event.getDamageMultiplier());
	}

	/** MobEffectEvent.Remove: an effect removed before it ran out (canceling keeps it) */
	@Inject(method = "removeEffect", at = @At("HEAD"), cancellable = true)
	private void deepvoid$removeEffect(Holder<MobEffect> effect, CallbackInfoReturnable<Boolean> cir) {
		MobEffectInstance instance = deepvoid$self().getEffect(effect);
		if (instance != null && ModBus.post(new MobEffectEvent.Remove(deepvoid$self(), instance)))
			cir.setReturnValue(false);
	}

	@WrapOperation(method = "removeAllEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;onEffectRemoved(Lnet/minecraft/world/effect/MobEffectInstance;)V"))
	private void deepvoid$removeAllEffects(LivingEntity self, MobEffectInstance instance, Operation<Void> original) {
		ModBus.post(new MobEffectEvent.Remove(self, instance));
		original.call(self, instance);
	}

	/** MobEffectEvent.Expired: an effect ran out (server) */
	@WrapOperation(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;onEffectRemoved(Lnet/minecraft/world/effect/MobEffectInstance;)V"))
	private void deepvoid$effectExpired(LivingEntity self, MobEffectInstance instance, Operation<Void> original) {
		ModBus.post(new MobEffectEvent.Expired(self, instance));
		original.call(self, instance);
	}

	/** LivingEntityUseItemEvent.Start: canceling it stops the use, the duration can change */
	@Inject(method = "startUsingItem", at = @At("HEAD"), cancellable = true)
	private void deepvoid$startUsing(InteractionHand hand, CallbackInfo ci) {
		ItemStack stack = this.getItemInHand(hand);
		if (stack.isEmpty() || this.isUsingItem())
			return;
		LivingEntityUseItemEvent.Start event = new LivingEntityUseItemEvent.Start(deepvoid$self(), stack, stack.getUseDuration(deepvoid$self()));
		if (ModBus.post(event) || event.getDuration() < 0)
			ci.cancel();
		else
			this.deepvoid$useDuration = event.getDuration();
	}

	@org.spongepowered.asm.mixin.Unique
	private int deepvoid$useDuration = -1;

	@Inject(method = "startUsingItem", at = @At("TAIL"))
	private void deepvoid$startedUsing(InteractionHand hand, CallbackInfo ci) {
		if (this.deepvoid$useDuration >= 0 && this.isUsingItem())
			this.useItemRemaining = this.deepvoid$useDuration;
		this.deepvoid$useDuration = -1;
	}

	/** LivingEntityUseItemEvent.Stop: canceling it skips the item's release */
	@WrapOperation(method = "releaseUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;releaseUsing(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V"))
	private void deepvoid$stopUsing(ItemStack stack, Level level, LivingEntity entity, int remaining, Operation<Void> original) {
		if (!ModBus.post(new LivingEntityUseItemEvent.Stop(entity, stack, remaining)))
			original.call(stack, level, entity, remaining);
	}

	/** LivingEntityUseItemEvent.Finish, with a copy of the stack before it was used up */
	@Inject(method = "completeUsingItem", at = @At("HEAD"))
	private void deepvoid$finishUsing(CallbackInfo ci) {
		if (!this.useItem.isEmpty() && this.isUsingItem())
			ModBus.post(new LivingEntityUseItemEvent.Finish(deepvoid$self(), this.useItem.copy()));
	}

	@WrapOperation(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getExperienceReward(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;)I"))
	private int deepvoid$experienceDrop(LivingEntity self, ServerLevel level, Entity killer, Operation<Integer> original) {
		LivingExperienceDropEvent event = new LivingExperienceDropEvent(self, this.lastHurtByPlayer, original.call(self, level, killer));
		return ModBus.post(event) ? 0 : event.getDroppedExperience();
	}

	/** NeoForge's onEntitySwing: an item may handle the swing itself */
	@Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"), cancellable = true)
	private void deepvoid$swing(InteractionHand hand, boolean updateSelf, CallbackInfo ci) {
		ItemStack stack = this.getItemInHand(hand);
		if (!stack.isEmpty() && stack.getItem() instanceof PortItemHooks.EntitySwing swing && swing.onEntitySwing(stack, deepvoid$self()))
			ci.cancel();
	}
}
