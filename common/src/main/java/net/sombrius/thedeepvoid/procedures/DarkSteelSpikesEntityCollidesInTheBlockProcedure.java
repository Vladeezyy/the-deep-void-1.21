package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.DarkSteelSawEntity;
import net.sombrius.thedeepvoid.entity.MaskedHunterEntity;

public class DarkSteelSpikesEntityCollidesInTheBlockProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity && !(entity instanceof MaskedHunterEntity) && !(entity instanceof DarkSteelSawEntity)) {
			entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC)),
					(float) (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:trap_vulnerable"))) ? 10 : 3));
		}
	}
}
