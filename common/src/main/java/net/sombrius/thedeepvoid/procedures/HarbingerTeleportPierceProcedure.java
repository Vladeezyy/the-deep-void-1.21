package net.sombrius.thedeepvoid.procedures;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.HarbingerOfCataclysmEntity;

public class HarbingerTeleportPierceProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof HarbingerOfCataclysmEntity) {
			((HarbingerOfCataclysmEntity) entity).setAnimation("empty");
		}
		if (entity instanceof HarbingerOfCataclysmEntity) {
			((HarbingerOfCataclysmEntity) entity).setAnimation("animation.harbinger_teleportPierce");
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 16, 99, false, false));
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("dashX", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()));
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("dashZ", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ()));
		}
		TheDeepVoidMod.queueServerWork(8, () -> {
			if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("dashX")), ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY() + 1.5), (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("dashZ"))));
			}
			if (entity instanceof HarbingerOfCataclysmEntity _datEntSetL)
				_datEntSetL.getEntityData().set(HarbingerOfCataclysmEntity.DATA_piercing, true);
			entity.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180)) * 1.6), 0.1, (Math.cos(Math.toRadians(entity.getYRot())) * 1.6)));
		});
		TheDeepVoidMod.queueServerWork(12, () -> {
			if (entity instanceof HarbingerOfCataclysmEntity _datEntSetL)
				_datEntSetL.getEntityData().set(HarbingerOfCataclysmEntity.DATA_piercing, false);
		});
		TheDeepVoidMod.queueServerWork(16, () -> {
			if (Math.random() < 0.2 && (entity instanceof HarbingerOfCataclysmEntity _datEntI ? _datEntI.getEntityData().get(HarbingerOfCataclysmEntity.DATA_repeat) : 0) < 4) {
				if (entity instanceof HarbingerOfCataclysmEntity _datEntSetI)
					_datEntSetI.getEntityData().set(HarbingerOfCataclysmEntity.DATA_repeat, (int) ((entity instanceof HarbingerOfCataclysmEntity _datEntI ? _datEntI.getEntityData().get(HarbingerOfCataclysmEntity.DATA_repeat) : 0) + 1));
				HarbingerTeleportProcedure.execute(world, entity);
			} else {
				if (entity instanceof HarbingerOfCataclysmEntity _datEntSetL)
					_datEntSetL.getEntityData().set(HarbingerOfCataclysmEntity.DATA_attacking, false);
			}
		});
	}
}
