package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class VanishEffectExpiresProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "0a6a7610-463a-4982-8c09-23fbbb6a4da7")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "0a6a7610-463a-4982-8c09-23fbbb6a4da7"));
		}
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "ce3747e9-226d-4519-840d-0a19834844da")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "ce3747e9-226d-4519-840d-0a19834844da"));
		}
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "dc4b4a93-4a76-44f9-8ecf-9dad1cbe411e")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "dc4b4a93-4a76-44f9-8ecf-9dad1cbe411e"));
		}
		if (((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.STEP_HEIGHT).getModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "13df1bf1-3b0d-4965-b15f-6403e84af6cf")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.STEP_HEIGHT).removeModifier(ResourceLocation.fromNamespaceAndPath("the_deep_void", "13df1bf1-3b0d-4965-b15f-6403e84af6cf"));
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()) {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(3, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor3);
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.HEAD, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor3);
				}
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor3 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		} else {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor3);
				entityToSpawn.setPickUpDelay(20);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor3 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()) {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(2, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor2);
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.CHEST, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor2);
				}
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor2 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		} else {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor2);
				entityToSpawn.setPickUpDelay(20);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor2 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()) {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(1, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor1);
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.LEGS, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor1);
				}
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor1 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		} else {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor1);
				entityToSpawn.setPickUpDelay(20);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor1 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		}
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()) {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(0, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor0);
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.FEET, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor0);
				}
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor0 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		} else {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor0);
				entityToSpawn.setPickUpDelay(20);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.saveArmor0 = ItemStack.EMPTY;
				_vars.syncPlayerVariables(entity);
			}
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
			_vars.saveArmorToughness = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
			_vars.saveKnockbackResistance = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
			_vars.saveStepHeight = 0;
			_vars.syncPlayerVariables(entity);
		}
	}
}
