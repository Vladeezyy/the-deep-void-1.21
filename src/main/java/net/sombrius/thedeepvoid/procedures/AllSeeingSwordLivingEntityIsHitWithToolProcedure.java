package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class AllSeeingSwordLivingEntityIsHitWithToolProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getTarget(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ALL_SEEING_SWORD.get()) {
			if ((sourceentity instanceof Player _plrCldRem3 ? _plrCldRem3.getCooldowns().getCooldownPercent((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 0f) * 100 : 0) <= 0) {
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 600);
				{
					ItemStack _ist = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					_ist.hurtAndBreak(100, RandomSource.create(), null, () -> {
						_ist.shrink(1);
						_ist.setDamageValue(0);
					});
				}
				sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
						(float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / 4));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.PAINFUL, 200, 0));
				entity.getPersistentData().putString("deep_void:painfulSharer", (sourceentity.getDisplayName().getString()));
			}
		}
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SOUL_CLEAVER.get()) {
			if ((sourceentity instanceof Player _plrCldRem17 ? _plrCldRem17.getCooldowns().getCooldownPercent((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 0f) * 100 : 0) <= 0) {
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 520);
				{
					ItemStack _ist = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					_ist.hurtAndBreak(100, RandomSource.create(), null, () -> {
						_ist.shrink(1);
						_ist.setDamageValue(0);
					});
				}
				sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:void_energy"))), sourceentity),
						(float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / 5));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.PAINFUL, 300, 0));
				entity.getPersistentData().putString("deep_void:painfulSharer", (sourceentity.getDisplayName().getString()));
				entity.getPersistentData().putBoolean("deep_void:advancedPainful", true);
			}
		}
	}
}
