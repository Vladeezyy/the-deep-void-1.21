package net.sombrius.thedeepvoid.port;

import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforge.common.CommonHooks;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Holder;

import javax.annotation.Nullable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.Map;

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

	/** 1.20 {@code if (stack.hurt(amount, random, player)) ...}: damages the stack and tells whether it broke (1.21 already shrank it). */
	public static boolean hurtAndBreakReports(ItemStack stack, int amount, @Nullable LivingEntity entity) {
		int before = stack.getCount();
		hurtAndBreak(stack, amount, entity);
		return stack.getCount() < before;
	}

	/** 1.20 {@code AbstractArrow.setKnockback}: 1.21 takes knockback from the weapon's enchantments only. */
	public static void setKnockback(AbstractArrow arrow, int knockback) {
		((ArrowKnockback) arrow).deepvoid$setKnockback(knockback);
	}

	/** 1.20 {@code LivingEntity#getExperienceReward()}: 1.21 needs the server level and the killer. */
	public static int experienceReward(LivingEntity entity) {
		return entity.level() instanceof ServerLevel serverLevel ? entity.getExperienceReward(serverLevel, entity.getLastAttacker()) : 0;
	}

	/** 1.21 enchantments live in a datapack registry: look them up on whichever side we are. */
	@Nullable
	/** The holders of the last two enchantment lookups (the server's and the client's in single player). */
	private static volatile EnchantmentCache cacheA, cacheB;

	private record EnchantmentCache(HolderLookup.RegistryLookup<Enchantment> lookup, Map<ResourceKey<Enchantment>, Optional<Holder.Reference<Enchantment>>> holders) {
	}

	public static Holder<Enchantment> enchantment(ResourceKey<Enchantment> key) {
		HolderLookup.RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
		if (lookup == null)
			return null;
		EnchantmentCache cache = cacheA;
		if (cache == null || cache.lookup() != lookup) {
			cache = cacheB;
			if (cache == null || cache.lookup() != lookup) {
				cache = new EnchantmentCache(lookup, new ConcurrentHashMap<>());
				cacheB = cacheA;
			}
			cacheA = cache;
		}
		return cache.holders().computeIfAbsent(key, lookup::get).orElse(null);
	}

	/** Called by the tick handlers for every armor slot: an empty slot has no enchantments, no lookup and no event needed. */
	public static int enchantmentLevel(ItemStack stack, ResourceKey<Enchantment> key) {
		if (stack.isEmpty())
			return 0;
		Holder<Enchantment> holder = enchantment(key);
		return holder == null ? 0 : stack.getEnchantmentLevel(holder);
	}

	public static void enchant(ItemStack stack, ResourceKey<Enchantment> key, int level) {
		Holder<Enchantment> holder = enchantment(key);
		if (holder != null)
			stack.enchant(holder, level);
	}

	/** 1.20 MobEffect#removeAttributeModifiers hook (effect expired or removed); called from {@link PortEvents}. */
	public interface EffectRemovedListener {
		void onEffectRemoved(LivingEntity entity, int amplifier);
	}

	public interface ArrowKnockback {
		void deepvoid$setKnockback(int knockback);

		int deepvoid$getKnockback();
	}
}
