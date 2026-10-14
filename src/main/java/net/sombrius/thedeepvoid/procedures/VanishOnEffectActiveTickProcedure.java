package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VanishOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(MobEffects.INVISIBILITY)) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(24 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:boss")))) {
						if ((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity) {
							if (entityiterator instanceof Mob) {
								try {
									((Mob) entityiterator).setTarget(null);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
}
