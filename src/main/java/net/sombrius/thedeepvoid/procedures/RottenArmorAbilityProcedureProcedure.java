package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class RottenArmorAbilityProcedureProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).RottenArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).RottenArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.RottenArmorAbility = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).RottenArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).VoidCloakAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).VoidCloakAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.VoidCloakAbility = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).VoidCloakAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).flesh_roar != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).flesh_roar / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.flesh_roar = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).flesh_roar - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).allseeingArmor != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).allseeingArmor / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.allseeingArmor = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).allseeingArmor - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).weaverBoots != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).weaverBoots / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.weaverBoots = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).weaverBoots - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).voidriumTeleport != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).voidriumTeleport / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.voidriumTeleport = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).voidriumTeleport - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).hunterArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).hunterArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.hunterArmorAbility = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).hunterArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).vultureArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).vultureArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.vultureArmorAbility = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).vultureArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).gunslingerArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).gunslingerArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.gunslingerArmorAbility = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).gunslingerArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).sentientArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).sentientArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.sentientArmorAbility = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).sentientArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
