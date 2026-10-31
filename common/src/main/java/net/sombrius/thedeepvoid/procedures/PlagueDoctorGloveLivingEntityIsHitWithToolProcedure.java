package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.neo.Capabilities;
import net.sombrius.thedeepvoid.port.neo.IItemHandlerModifiable;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class PlagueDoctorGloveLivingEntityIsHitWithToolProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (net.sombrius.thedeepvoid.port.neo.PortCaps.entity(sourceentity, null) instanceof IItemHandlerModifiable _modHandler) {
			for (int _idx = 0; _idx < _modHandler.getSlots(); _idx++) {
				ItemStack itemstackiterator = _modHandler.getStackInSlot(_idx).copy();
				if (itemstackiterator.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:needles")))) {
					if (itemstackiterator.getItem() == TheDeepVoidModItems.GLOWING_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1800, 0));
					} else if (itemstackiterator.getItem() == TheDeepVoidModItems.ROT_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROT.holder(), 200, 0));
					} else if (itemstackiterator.getItem() == TheDeepVoidModItems.HEXED_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.HEXED.holder(), 600, 0));
					} else if (itemstackiterator.getItem() == TheDeepVoidModItems.POISON_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
					} else if (itemstackiterator.getItem() == TheDeepVoidModItems.WITHER_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 0));
					} else if (itemstackiterator.getItem() == TheDeepVoidModItems.INSTANT_DAMAGE_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 5, 0));
					} else if (itemstackiterator.getItem() == TheDeepVoidModItems.WEAKNESS_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 0));
					} else if (itemstackiterator.getItem() == TheDeepVoidModItems.SLOWNESS_NEEDLE.get()) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 1200, 0));
					}
				}
			}
		}
		if (!(new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(sourceentity))) {
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.GLOWING_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.GLOWING_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.ROT_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.ROT_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.HEXED_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.HEXED_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.POISON_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.POISON_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.WITHER_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.WITHER_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.INSTANT_DAMAGE_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.INSTANT_DAMAGE_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.WEAKNESS_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.WEAKNESS_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
			if (sourceentity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(TheDeepVoidModItems.SLOWNESS_NEEDLE.get())) : false) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(TheDeepVoidModItems.SLOWNESS_NEEDLE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), false, 1, _player.inventoryMenu.getCraftSlots());
				}
			}
		}
	}
}
