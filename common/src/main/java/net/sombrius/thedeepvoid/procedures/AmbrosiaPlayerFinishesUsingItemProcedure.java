package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class AmbrosiaPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(new ItemStack(TheDeepVoidModItems.AMBROSIA.get()), 320);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.GOLDEN_BLOOD.holder(), 240, 1));
		if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder()))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder(), 1200, 0, true, true));
		} else {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder());
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder(), 1800, 0, true, true));
		}
	}
}
