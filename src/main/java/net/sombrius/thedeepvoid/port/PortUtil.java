package net.sombrius.thedeepvoid.port;

import net.neoforged.neoforge.server.ServerLifecycleHooks;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/** Helpers for 1.20 calls that have no direct 1.21 equivalent. */
public final class PortUtil {
	private PortUtil() {
	}

	/** 1.20 {@code stack.hurtAndBreak(amount, random, player, onBreak)} / {@code if (stack.hurt(...)) shrink}: 1.21 needs a ServerLevel. */
	public static void hurtAndBreak(ItemStack stack, int amount, @Nullable LivingEntity entity) {
		if (stack.isEmpty() || !stack.isDamageableItem())
			return;
		ServerLevel level = null;
		if (entity != null && entity.level() instanceof ServerLevel serverLevel)
			level = serverLevel;
		if (level == null) {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			if (server == null)
				return;
			level = server.overworld();
		}
		stack.hurtAndBreak(amount, level, entity, item -> {
		});
	}

	/** 1.20 {@code AbstractArrow.setKnockback}: 1.21 takes knockback from the weapon's enchantments only. */
	public static void setKnockback(AbstractArrow arrow, int knockback) {
		((ArrowKnockback) arrow).deepvoid$setKnockback(knockback);
	}

	public interface ArrowKnockback {
		void deepvoid$setKnockback(int knockback);

		int deepvoid$getKnockback();
	}
}
