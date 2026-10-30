package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class ChainGauntletLivingEntityIsHitWithToolProcedure {
	public static void execute(Entity entity, Entity sourceentity, ItemStack itemstack) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof Player _plrCldRem1 ? _plrCldRem1.getCooldowns().getCooldownPercent(itemstack, 0f) * 100 : 0) <= 0
				&& !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:boss")))) {
			if (sourceentity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 140);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.CHAINED.holder(), 60, 0));
		}
	}
}
