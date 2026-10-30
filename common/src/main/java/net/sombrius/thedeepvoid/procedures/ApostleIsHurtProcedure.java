package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.ICancellableEvent;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.entity.ApostleBossEntity;

@EventBusSubscriber
public class ApostleIsHurtProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		execute(null, world, entity, immediatesourceentity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		if (entity instanceof ApostleBossEntity) {
			if (entity instanceof ApostleBossEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ApostleBossEntity.DATA_taunt, 300);
			if ((entity instanceof ApostleBossEntity _datEntL2 && _datEntL2.getEntityData().get(ApostleBossEntity.DATA_doingAttack)) == false && !(immediatesourceentity == sourceentity) && !(sourceentity == null) && !(sourceentity == entity)) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 7, 99, false, false));
				if (entity instanceof ApostleBossEntity) {
					((ApostleBossEntity) entity).setAnimation("animation.apostle_block");
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.anvil.place")), SoundSource.HOSTILE, 1, (float) 1.5);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.anvil.place")), SoundSource.HOSTILE, 1, (float) 1.5, false);
					}
				}
				if (!immediatesourceentity.level().isClientSide())
					immediatesourceentity.discard();
			}
		}
	}
}
