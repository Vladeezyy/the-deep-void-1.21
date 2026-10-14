package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class PlayerHasSoulWingsTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SOUL_WINGS_CHESTPLATE.get()
				|| (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.SOUL_WINGS_FLY_CHESTPLATE.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("flying") == true) {
				if (entity.isShiftKeyDown()) {
					entity.setDeltaMovement(new Vec3(0, 0.4, 0));
				} else {
					entity.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180)) * 0.5), 0.1, (Math.cos(Math.toRadians(entity.getYRot())) * 0.5)));
				}
				if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).wingFlapSound >= 14) {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.wingFlapSound = 0;
						_vars.syncPlayerVariables(entity);
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:eyekin_wing_flap")), SoundSource.PLAYERS, 1, (float) 1.1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:eyekin_wing_flap")), SoundSource.PLAYERS, 1, (float) 1.1, false);
						}
					}
				} else {
					{
						TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
						_vars.wingFlapSound = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).wingFlapSound + 1;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
		}
	}
}
