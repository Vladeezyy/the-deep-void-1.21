package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LivingDeathEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class PainfulEntityIsKilledProcedure {
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
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.PAINFUL.holder()) && !(entityiterator instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(TheDeepVoidModMobEffects.PAINFUL.holder()))
						&& entityiterator instanceof LivingEntity) {
					if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("deep_void:advancedPainful", false) == true) {
						if (!(entityiterator.getDisplayName().getString()).equals(net.sombrius.thedeepvoid.port.PortUtil.data(entity).getStringOr("deep_void:painfulSharer", ""))) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.PAINFUL.holder(), 480, 0));
							net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).putBoolean("deep_void:advancedPainful", true);
							net.sombrius.thedeepvoid.port.PortUtil.data(entityiterator).putString("deep_void:painfulSharer", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getStringOr("deep_void:painfulSharer", "")));
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.respawn_anchor.set_spawn")), SoundSource.HOSTILE, 1,
											(float) 0.9);
								} else {
									_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.respawn_anchor.set_spawn")), SoundSource.HOSTILE, 1, (float) 0.9, false);
								}
							}
						}
					}
				}
			}
		}
	}
}
