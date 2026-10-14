package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.sombrius.thedeepvoid.entity.DeathMawEntity;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerOrStalkerDiesProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity instanceof Player && sourceentity instanceof StalkerEntity) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.StalkerSpawn = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.StalkerCountFinish = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.StalkerCount = 0;
				_vars.syncPlayerVariables(entity);
			}
			if (!sourceentity.level().isClientSide())
				sourceentity.discard();
		}
		if (entity instanceof Player && sourceentity instanceof DeathMawEntity) {
			if ((sourceentity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity) {
				if (!sourceentity.level().isClientSide())
					sourceentity.discard();
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = TheDeepVoidModEntities.DEATH_MAW_HIDDEN.get().spawn(_level, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			}
		}
	}
}
