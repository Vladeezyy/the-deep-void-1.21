package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.ICancellableEvent;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class PainfulSharerAttackedProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.PAINFUL.holder())) {
					if ((net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).getStringOr("deep_void:painfulSharer", "")).equals(entity.getDisplayName().getString())) {
						if ((entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.PAINFUL.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.PAINFUL.holder()).getAmplifier() : 0) > 1) {
							if (event instanceof ICancellableEvent _cancellable) {
								_cancellable.setCanceled(true);
							}
							entityiterator.hurt(
									new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy"))), sourceentity),
									(float) (amount + (entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.PAINFUL.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.PAINFUL.holder()).getAmplifier() : 0)));
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("particle.soul_escape")), SoundSource.HOSTILE,
											1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.4));
								} else {
									_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("particle.soul_escape")), SoundSource.HOSTILE, 1,
											(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.4), false);
								}
							}
						} else {
							if (event instanceof ICancellableEvent _cancellable) {
								_cancellable.setCanceled(true);
							}
							entityiterator.hurt(
									new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy"))), sourceentity),
									(float) amount);
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("particle.soul_escape")), SoundSource.HOSTILE,
											1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.4));
								} else {
									_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("particle.soul_escape")), SoundSource.HOSTILE, 1,
											(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.4), false);
								}
							}
						}
					}
				}
			}
		}
	}
}
