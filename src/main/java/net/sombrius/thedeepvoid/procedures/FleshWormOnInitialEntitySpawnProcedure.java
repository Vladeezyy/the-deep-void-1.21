package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.FleshWormEntity;
import net.sombrius.thedeepvoid.entity.ThumperEntityEntity;

public class FleshWormOnInitialEntitySpawnProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof FleshWormEntity) {
			((FleshWormEntity) entity).setAnimation("animation.fleshWorm_appear");
		}
		if (entity instanceof FleshWormEntity _datEntSetL)
			_datEntSetL.getEntityData().set(FleshWormEntity.DATA_spawning, true);
		{
			final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof ThumperEntityEntity) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), 99);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.wither.break_block")), SoundSource.BLOCKS, 1,
									(float) 0.8);
						} else {
							_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.wither.break_block")), SoundSource.BLOCKS, 1, (float) 0.8,
									false);
						}
					}
				}
			}
		}
		TheDeepVoidMod.queueServerWork(24, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_worm_scream")), SoundSource.HOSTILE, 10, (float) 0.8);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_worm_scream")), SoundSource.HOSTILE, 10, (float) 0.8, false);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(40, () -> {
			if (entity instanceof FleshWormEntity _datEntSetL)
				_datEntSetL.getEntityData().set(FleshWormEntity.DATA_spawning, false);
		});
	}
}
