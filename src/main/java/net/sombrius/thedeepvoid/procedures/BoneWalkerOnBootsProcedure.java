package net.sombrius.thedeepvoid.procedures;

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
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class BoneWalkerOnBootsProcedure {
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
		if (EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_SORROW.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)) != 0
				|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_SORROW.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)) != 0
				|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_SORROW.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)) != 0
				|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_SORROW.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.DEAD_INSIDE, 20, 0, false, false));
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)) != 0
				|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)) != 0
				|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)) != 0
				|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.CURSE_OF_GODS_BLOOD.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0) {
			if (!(entity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL, 1200, 0, false, false));
			}
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.BONE_WALKER.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0
				&& (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BlockTags.create(ResourceLocation.parse("the_deep_void:natural_bone_block")))) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "9618d7a9-fb3b-497d-8faa-4e8aee6459ae")) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
						.hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "9618d7a9-fb3b-497d-8faa-4e8aee6459ae"))))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
							.addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "9618d7a9-fb3b-497d-8faa-4e8aee6459ae"),
									((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getEnchantmentLevel(TheDeepVoidModEnchantments.BONE_WALKER.get()) * 0.105 + 1.18),
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
					if (entity.getX() != entity.getPersistentData().getDouble("boneWalkerPlayerX") || entity.getZ() != entity.getPersistentData().getDouble("boneWalkerPlayerZ")) {
						{
							ItemStack _ist = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY);
							PortUtil.hurtAndBreak(_ist, 1, null);
						}
					}
					entity.getPersistentData().putDouble("boneWalkerPlayerX", (entity.getX()));
					entity.getPersistentData().putDouble("boneWalkerPlayerZ", (entity.getZ()));
				});
			}
		} else if ((!(EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.BONE_WALKER.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0)
				|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.BONE_WALKER.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0
						&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BlockTags.create(ResourceLocation.parse("the_deep_void:natural_bone_block"))))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "9618d7a9-fb3b-497d-8faa-4e8aee6459ae")) != null) {
			TheDeepVoidMod.queueServerWork(15, () -> {
				if ((!(EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.BONE_WALKER.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0)
						|| EnchantmentHelper.getItemEnchantmentLevel(TheDeepVoidModEnchantments.BONE_WALKER.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0
								&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BlockTags.create(ResourceLocation.parse("the_deep_void:natural_bone_block"))))
						&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "9618d7a9-fb3b-497d-8faa-4e8aee6459ae")) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "9618d7a9-fb3b-497d-8faa-4e8aee6459ae"));
				}
			});
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get()
				&& (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BlockTags.create(ResourceLocation.parse("the_deep_void:flesh_blocks")))) {
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "17f680a8-8450-45e4-9ec0-26712e00f05c")) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
						.hasModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "17f680a8-8450-45e4-9ec0-26712e00f05c"))))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)
							.addTransientModifier((new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "17f680a8-8450-45e4-9ec0-26712e00f05c"), 1.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)));
			}
		} else if ((!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get())
				|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get()
						&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BlockTags.create(ResourceLocation.parse("the_deep_void:flesh_blocks"))))
				&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "17f680a8-8450-45e4-9ec0-26712e00f05c")) != null) {
			TheDeepVoidMod.queueServerWork(15, () -> {
				if ((!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get())
						|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.ICHOR_STRIDER_BOOTS.get()
								&& !(world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()))).is(BlockTags.create(ResourceLocation.parse("the_deep_void:flesh_blocks"))))
						&& ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "17f680a8-8450-45e4-9ec0-26712e00f05c")) != null) {
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "17f680a8-8450-45e4-9ec0-26712e00f05c"));
				}
			});
		}
	}
}
