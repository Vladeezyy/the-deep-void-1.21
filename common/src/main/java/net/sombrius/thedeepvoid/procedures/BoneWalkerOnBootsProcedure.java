package net.sombrius.thedeepvoid.procedures;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.port.PortUtil;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class BoneWalkerOnBootsProcedure {
	private static final ResourceLocation ID_17F680A8_8450_45E4_9EC0_26712E00F05C = ResourceLocation.fromNamespaceAndPath("the_deep_void", "17f680a8-8450-45e4-9ec0-26712e00f05c");
	private static final ResourceLocation ID_9618D7A9_FB3B_497D_8FAA_4E8AEE6459AE = ResourceLocation.fromNamespaceAndPath("the_deep_void", "9618d7a9-fb3b-497d-8faa-4e8aee6459ae");

	private static final TagKey<Block> BLOCK_TAG_FLESH_BLOCKS = net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, ResourceLocation.parse("the_deep_void:flesh_blocks"));
	private static final TagKey<Block> BLOCK_TAG_NATURAL_BONE_BLOCK = net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, ResourceLocation.parse("the_deep_void:natural_bone_block"));

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_SORROW) != 0
				|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_SORROW) != 0
				|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_SORROW) != 0
				|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_SORROW) != 0) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.DEAD_INSIDE.holder(), 20, 0, false, false));
		}
		if (PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD) != 0
				|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD) != 0
				|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD) != 0
				|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD) != 0) {
			if (!(entity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder(), 1200, 0, false, false));
			}
		}
		if (PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.BONE_WALKER) != 0
				&& (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BLOCK_TAG_NATURAL_BONE_BLOCK)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ID_9618D7A9_FB3B_497D_8FAA_4E8AEE6459AE) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
						.hasModifier(ID_9618D7A9_FB3B_497D_8FAA_4E8AEE6459AE)))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
							.addTransientModifier((new AttributeModifier(ID_9618D7A9_FB3B_497D_8FAA_4E8AEE6459AE,
									(PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.BONE_WALKER) * 0.105 + 1.18),
									AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)));
			}
			if (Math.random() < 0.01 && !(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity)) && !(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
					}
					return false;
				}
			}.checkGamemode(entity))) {
				TheDeepVoidMod.queueServerWork(5, () -> {
					if (entity.getX() != net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("boneWalkerPlayerX") || entity.getZ() != net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("boneWalkerPlayerZ")) {
						{
							ItemStack _ist = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY);
							PortUtil.hurtAndBreak(_ist, 1, null);
						}
					}
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("boneWalkerPlayerX", (entity.getX()));
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("boneWalkerPlayerZ", (entity.getZ()));
				});
			}
		} else if ((!(PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.BONE_WALKER) != 0)
				|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.BONE_WALKER) != 0
						&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BLOCK_TAG_NATURAL_BONE_BLOCK))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ID_9618D7A9_FB3B_497D_8FAA_4E8AEE6459AE) != null) {
			TheDeepVoidMod.queueServerWork(15, () -> {
				if ((!(PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.BONE_WALKER) != 0)
						|| PortUtil.enchantmentLevel((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY), TheDeepVoidModEnchantments.BONE_WALKER) != 0
								&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BLOCK_TAG_NATURAL_BONE_BLOCK))
						&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ID_9618D7A9_FB3B_497D_8FAA_4E8AEE6459AE) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(ID_9618D7A9_FB3B_497D_8FAA_4E8AEE6459AE);
				}
			});
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get()
				&& (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BLOCK_TAG_FLESH_BLOCKS)) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ID_17F680A8_8450_45E4_9EC0_26712E00F05C) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
						.hasModifier(ID_17F680A8_8450_45E4_9EC0_26712E00F05C)))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
							.addTransientModifier((new AttributeModifier(ID_17F680A8_8450_45E4_9EC0_26712E00F05C, 1.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)));
			}
		} else if ((!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get())
				|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get()
						&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BLOCK_TAG_FLESH_BLOCKS))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ID_17F680A8_8450_45E4_9EC0_26712E00F05C) != null) {
			TheDeepVoidMod.queueServerWork(15, () -> {
				if ((!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get())
						|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get()
								&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BLOCK_TAG_FLESH_BLOCKS))
						&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ID_17F680A8_8450_45E4_9EC0_26712E00F05C) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(ID_17F680A8_8450_45E4_9EC0_26712E00F05C);
				}
			});
		}
	}
}
