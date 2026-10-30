package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.Identifier;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AmbrosiaWithdrawalEffectExpiresProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "fb0a5016-7909-4a90-a092-c4cc69350155"));
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "c14bf296-7ab7-4d26-aa52-4ceae6e86e90"));
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "bdb91cf0-2749-4b02-89af-16ecea159b2c"));
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "384de875-3d99-4f37-821c-471252ee626d"));
		}
	}
}
