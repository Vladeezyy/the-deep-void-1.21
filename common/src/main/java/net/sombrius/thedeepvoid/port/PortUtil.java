package net.sombrius.thedeepvoid.port;

import net.minecraft.world.item.equipment.ArmorType;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Holder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.port.platform.Platform;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.Map;

/** Helpers for 1.20 calls that have no direct 1.21 equivalent. */
public final class PortUtil {
	private PortUtil() {
	}

	/** The client level's registries (set by the client entry point; null on a dedicated server) */
	public static Supplier<RegistryAccess> clientRegistries = () -> null;

	/** The client player (set by the client entry point; null on a dedicated server) */
	public static Supplier<Entity> clientPlayer = () -> null;

	/** NeoForge's poison damage type ({@code neoforge:poison}; the Fabric jar ships the same damage type) */
	public static final ResourceKey<DamageType> POISON_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath("neoforge", "poison"));

	/** NeoForge's {@code getPersistentData()}: the entity's own saved tag, on both loaders */
	public static CompoundTag data(Entity entity) {
		return Platform.INSTANCE.persistentData(entity);
	}

	/** NeoForge's {@code getPersistentData()} of a block entity */
	public static CompoundTag data(net.minecraft.world.level.block.entity.BlockEntity blockEntity) {
		return Platform.INSTANCE.persistentData(blockEntity);
	}

	/** The server's registries, else the client's (NeoForge's {@code CommonHooks.resolveLookup}) */
	@Nullable
	public static <T> HolderLookup.RegistryLookup<T> resolveLookup(ResourceKey<? extends Registry<T>> key) {
		MinecraftServer server = Platform.INSTANCE.currentServer();
		if (server != null)
			return server.registryAccess().lookup(key).orElse(null);
		RegistryAccess client = clientRegistries.get();
		return client == null ? null : client.lookup(key).orElse(null);
	}

	/** 1.20's {@code net.sombrius.thedeepvoid.port.PortUtil.wearing(entity, stack)}: the stack is worn in an armor slot */
	public static boolean wearing(Entity entity, ItemStack stack) {
		if (entity instanceof LivingEntity living)
			for (net.minecraft.world.entity.EquipmentSlot slot : net.minecraft.world.entity.EquipmentSlot.values())
				if (slot.isArmor() && living.getItemBySlot(slot) == stack)
					return true;
		return false;
	}

	/** The armor type of an armor stack (1.20's {@code ArmorItem.getType()}), null if it is no humanoid armor */
	@Nullable
	public static net.minecraft.world.item.equipment.ArmorType armorType(ItemStack stack) {
		net.minecraft.world.item.equipment.Equippable equippable = stack.get(net.minecraft.core.component.DataComponents.EQUIPPABLE);
		if (equippable == null)
			return null;
		for (net.minecraft.world.item.equipment.ArmorType type : net.minecraft.world.item.equipment.ArmorType.values())
			if (type.getSlot() == equippable.slot())
				return type;
		return null;
	}

	/** NeoForge's default {@code onDestroyedByPlayer}: removes the block (server) or sets the fluid (client) */
	public static boolean destroyedByPlayer(Level level, BlockPos pos, FluidState fluid) {
		if (level.isClientSide())
			return level.setBlock(pos, fluid.createLegacyBlock(), 11);
		return level.removeBlock(pos, false);
	}

	/** NeoForge's {@code net.sombrius.thedeepvoid.port.PortUtil.swordAttributes(Tier, float, float)} (vanilla takes an int damage) */
	public static ItemAttributeModifiers swordAttributes(Tier tier, float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
	}

	/** 1.20 {@code stack.hurtAndBreak(amount, random, player, onBreak)} / {@code if (stack.hurt(...)) shrink}: 1.21 needs a ServerLevel. */
	public static void hurtAndBreak(ItemStack stack, int amount, @Nullable LivingEntity entity) {
		if (stack.isEmpty() || !stack.isDamageableItem())
			return;
		ServerLevel level = null;
		if (entity != null && entity.level() instanceof ServerLevel serverLevel)
			level = serverLevel;
		if (level == null) {
			MinecraftServer server = Platform.INSTANCE.currentServer();
			if (server == null)
				return;
			level = server.overworld();
		}
		stack.hurtAndBreak(amount, level, entity instanceof net.minecraft.server.level.ServerPlayer player ? player : null, item -> {
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
		HolderLookup.RegistryLookup<Enchantment> lookup = resolveLookup(Registries.ENCHANTMENT);
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
		return holder == null ? 0 : net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(holder, stack);
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
