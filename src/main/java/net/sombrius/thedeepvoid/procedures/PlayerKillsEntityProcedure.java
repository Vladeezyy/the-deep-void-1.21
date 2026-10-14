package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerKillsEntityProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_HELMET.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_CHESTPLATE.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_LEGGINGS.get()
				&& (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIM_ROTTEN_BONE_BOOTS.get()) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = sourceentity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.RottenArmorAbility = 0;
				_vars.syncPlayerVariables(sourceentity);
			}
		}
		if (entity instanceof LivingEntity _livEnt8 && _livEnt8.hasEffect(TheDeepVoidModMobEffects.EXPERIENCED) && entity instanceof LivingEntity && sourceentity instanceof LivingEntity) {
			for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(),
					(int) (1 + Math.round((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.EXPERIENCED) ? _livEnt.getEffect(TheDeepVoidModMobEffects.EXPERIENCED).getAmplifier() : 0) / 2)),
					(int) (4 + Math.round((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.EXPERIENCED) ? _livEnt.getEffect(TheDeepVoidModMobEffects.EXPERIENCED).getAmplifier() : 0) / 2))); index0++) {
				if (world instanceof ServerLevel _level)
					_level.addFreshEntity(new ExperienceOrb(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
							Mth.nextInt(RandomSource.create(), 1,
									(int) Math.round((4 + (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.EXPERIENCED) ? _livEnt.getEffect(TheDeepVoidModMobEffects.EXPERIENCED).getAmplifier() : 0))
											* ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.EXPERIENCED) ? _livEnt.getEffect(TheDeepVoidModMobEffects.EXPERIENCED).getDuration() : 0) / 120)))));
			}
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.EXPERIENCED);
		}
	}
}
