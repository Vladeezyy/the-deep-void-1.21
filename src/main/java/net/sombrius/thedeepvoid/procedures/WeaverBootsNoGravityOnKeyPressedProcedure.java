package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class WeaverBootsNoGravityOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.DEBILITATED))) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.WEAVER_BOOTS_BOOTS.get()) {
				if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).noGravity == true) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.noGravity = false;
						_vars.syncPlayerVariables(entity);
					}
				} else {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.noGravity = true;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
		}
	}
}
