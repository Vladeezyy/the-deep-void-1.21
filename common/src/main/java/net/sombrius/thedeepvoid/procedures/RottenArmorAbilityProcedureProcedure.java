package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
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
		if (TheDeepVoidModVariables.of(entity).RottenArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).RottenArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.RottenArmorAbility = TheDeepVoidModVariables.of(entity).RottenArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).VoidCloakAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).VoidCloakAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.VoidCloakAbility = TheDeepVoidModVariables.of(entity).VoidCloakAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).flesh_roar != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).flesh_roar / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.flesh_roar = TheDeepVoidModVariables.of(entity).flesh_roar - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).allseeingArmor != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).allseeingArmor / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.allseeingArmor = TheDeepVoidModVariables.of(entity).allseeingArmor - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).weaverBoots != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).weaverBoots / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.weaverBoots = TheDeepVoidModVariables.of(entity).weaverBoots - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).voidriumTeleport != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).voidriumTeleport / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.voidriumTeleport = TheDeepVoidModVariables.of(entity).voidriumTeleport - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).hunterArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).hunterArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.hunterArmorAbility = TheDeepVoidModVariables.of(entity).hunterArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).vultureArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).vultureArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.vultureArmorAbility = TheDeepVoidModVariables.of(entity).vultureArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).gunslingerArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).gunslingerArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.gunslingerArmorAbility = TheDeepVoidModVariables.of(entity).gunslingerArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (TheDeepVoidModVariables.of(entity).sentientArmorAbility != 0) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("##.#").format(TheDeepVoidModVariables.of(entity).sentientArmorAbility / 20))), true);
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.sentientArmorAbility = TheDeepVoidModVariables.of(entity).sentientArmorAbility - 1;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
