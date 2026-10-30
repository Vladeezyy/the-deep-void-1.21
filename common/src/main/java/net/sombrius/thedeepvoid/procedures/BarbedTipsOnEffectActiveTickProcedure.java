package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class BarbedTipsOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, Entity entity, double amplifier) {
		if (entity == null)
			return;
		entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.THORNS)), (float) (0.5 * (amplifier + 1)));
	}
}
