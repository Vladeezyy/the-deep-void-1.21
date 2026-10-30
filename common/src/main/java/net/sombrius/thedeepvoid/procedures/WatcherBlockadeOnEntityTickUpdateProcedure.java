package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class WatcherBlockadeOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity)
			_entity.removeAllEffects();
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("despawn", 0) >= 300) {
			entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC)), 999);
		} else {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("despawn", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("despawn", 0) + 1));
		}
	}
}
