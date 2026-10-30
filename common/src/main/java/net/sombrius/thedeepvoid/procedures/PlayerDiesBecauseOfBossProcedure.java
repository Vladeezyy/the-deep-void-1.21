package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingDeathEvent;
import net.sombrius.thedeepvoid.entity.ApostleBossEntity;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.entity.FleshWormEntity;
import net.sombrius.thedeepvoid.entity.HiveWatcherEntity;
import net.sombrius.thedeepvoid.entity.MisanthropicHivemindEntity;
import net.sombrius.thedeepvoid.entity.PrimordialBoneCrawlerEntity;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class PlayerDiesBecauseOfBossProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player && TheDeepVoidModVariables.of(entity).noHit == true) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.noHit = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity instanceof MisanthropicHivemindEntity || entity instanceof HiveWatcherEntity || entity instanceof PrimordialBoneCrawlerEntity || entity instanceof ApostleBossEntity || entity instanceof WeaverOfSoulsBossEntity
				|| entity instanceof FalseHydraEntity || entity instanceof FleshWormEntity) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(80 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player) {
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entityiterator);
							_vars.MusicWait = 0;
							_vars.syncPlayerVariables(entityiterator);
						}
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entityiterator);
							_vars.MusicTick = -1;
							_vars.syncPlayerVariables(entityiterator);
						}
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entityiterator);
							_vars.MusicPlay = "";
							_vars.syncPlayerVariables(entityiterator);
						}
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entityiterator);
							_vars.MusicLock = false;
							_vars.syncPlayerVariables(entityiterator);
						}
						{
							TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entityiterator);
							_vars.MusicReplace = false;
							_vars.syncPlayerVariables(entityiterator);
						}
					}
				}
			}
		}
	}
}
