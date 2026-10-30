package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingChangeTargetEvent;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;

@EventBusSubscriber
public class WeaverSetsItsAttackTargetProcedure {
	@SubscribeEvent
	public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
		execute(event, event.getEntity().level(), event.getOriginalAboutToBeSetTarget(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof WeaverOfSoulsBossEntity) {
			if (entity instanceof Player) {
				if ((sourceentity instanceof WeaverOfSoulsBossEntity _datEntL2 && _datEntL2.getEntityData().get(WeaverOfSoulsBossEntity.DATA_screamPlayed)) == false) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_scream_loud")), SoundSource.HOSTILE,
									2, 1);
						} else {
							_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_scream_loud")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
					if (sourceentity instanceof WeaverOfSoulsBossEntity _datEntSetL)
						_datEntSetL.getEntityData().set(WeaverOfSoulsBossEntity.DATA_screamPlayed, true);
					if (sourceentity instanceof WeaverOfSoulsBossEntity _datEntSetI)
						_datEntSetI.getEntityData().set(WeaverOfSoulsBossEntity.DATA_scream, 140);
				}
			}
		}
	}
}
