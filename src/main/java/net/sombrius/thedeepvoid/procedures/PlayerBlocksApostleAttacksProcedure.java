package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.sombrius.thedeepvoid.entity.ApostleBossEntity;
import net.sombrius.thedeepvoid.entity.ExecutionerEntity;
import net.sombrius.thedeepvoid.entity.FalseHydraArmEntity;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.entity.MadCultistEntity;

@EventBusSubscriber
public class PlayerBlocksApostleAttacksProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity instanceof Player) {
			if ((sourceentity instanceof ApostleBossEntity || sourceentity instanceof ExecutionerEntity || sourceentity instanceof MadCultistEntity || sourceentity instanceof FalseHydraEntity || sourceentity instanceof FalseHydraArmEntity)
					&& (entity instanceof LivingEntity _entUseItem6 ? _entUseItem6.getUseItem() : ItemStack.EMPTY).has(DataComponents.FOOD)) {
				if (entity instanceof LivingEntity _entity)
					_entity.stopUsingItem();
			}
		}
	}
}
