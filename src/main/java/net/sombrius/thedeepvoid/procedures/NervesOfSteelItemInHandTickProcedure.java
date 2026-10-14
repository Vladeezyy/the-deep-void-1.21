package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class NervesOfSteelItemInHandTickProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
			if (entity.getPersistentData().getBoolean("nervesBowLoading") == true) {
				if ((entity instanceof LivingEntity _entUseItem7 ? _entUseItem7.getUseItem() : ItemStack.EMPTY).getItem() == itemstack.getItem() && entity.getPersistentData().getDouble("nervesBowPulling") < 60) {
					entity.getPersistentData().putDouble("nervesBowPulling", (entity.getPersistentData().getDouble("nervesBowPulling") + 1));
					if (entity.getPersistentData().getDouble("nervesBowPulling") == 35) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.arrow.hit_player")), SoundSource.PLAYERS, (float) 0.4,
										(float) 1.2);
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.arrow.hit_player")), SoundSource.PLAYERS, (float) 0.4, (float) 1.2, false);
							}
						}
					}
					if (entity.getPersistentData().getDouble("nervesBowPulling") >= 35 && entity.getPersistentData().getDouble("nervesBowPulling") <= 37) {
						{
							final String _tagName = "glow";
							final double _tagValue = 1;
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
						}
					} else {
						{
							final String _tagName = "glow";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
				}
			} else {
				entity.getPersistentData().putBoolean("nervesBowLoading", false);
				entity.getPersistentData().putDouble("nervesBowPulling", 0);
			}
		}
	}
}
