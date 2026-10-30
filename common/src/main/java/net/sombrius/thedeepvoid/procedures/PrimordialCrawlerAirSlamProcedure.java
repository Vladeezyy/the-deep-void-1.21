package net.sombrius.thedeepvoid.procedures;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.PrimordialBoneCrawlerEntity;

public class PrimordialCrawlerAirSlamProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof PrimordialBoneCrawlerEntity) {
			((PrimordialBoneCrawlerEntity) entity).setAnimation("animation.primordialCrawler_fly");
		}
		TheDeepVoidMod.queueServerWork(18, () -> {
			if (entity instanceof PrimordialBoneCrawlerEntity _datEntSetL)
				_datEntSetL.getEntityData().set(PrimordialBoneCrawlerEntity.DATA_fly, true);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:primordial_crawler_flying")), SoundSource.HOSTILE, 2, 1);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:primordial_crawler_flying")), SoundSource.HOSTILE, 2, 1, false);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(54, () -> {
			if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("airX", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()));
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("airY", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY()));
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("airZ", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ()));
			}
		});
		TheDeepVoidMod.queueServerWork(68, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:mother_crawler_ambient")), SoundSource.HOSTILE, 2, (float) 0.4);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:mother_crawler_ambient")), SoundSource.HOSTILE, 2, (float) 0.4, false);
				}
			}
		});
		TheDeepVoidMod.queueServerWork(71, () -> {
			if (entity instanceof PrimordialBoneCrawlerEntity _datEntSetL)
				_datEntSetL.getEntityData().set(PrimordialBoneCrawlerEntity.DATA_airSlam, true);
			if (entity instanceof PrimordialBoneCrawlerEntity _datEntSetL)
				_datEntSetL.getEntityData().set(PrimordialBoneCrawlerEntity.DATA_fly, false);
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("airX", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("airY", 0)), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("airZ", 0))));
			entity.setDeltaMovement(
					new Vec3(((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("airX", 0) - entity.getX()) * 0.4), ((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("airY", 0) - entity.getY()) * 0.4), ((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("airZ", 0) - entity.getZ()) * 0.4)));
		});
	}
}
