package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import java.util.Comparator;
import java.util.List;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.BloodSpitEntity;
import net.sombrius.thedeepvoid.entity.BoneSawEntity;
import net.sombrius.thedeepvoid.entity.HivemindTentaclesEntity;
import net.sombrius.thedeepvoid.entity.MisanthropicHivemindEntity;
import net.sombrius.thedeepvoid.entity.SawThrowerEntity;
import net.sombrius.thedeepvoid.entity.SkullSmasherEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class HivemindTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && (entity instanceof MisanthropicHivemindEntity _datEntL3 && _datEntL3.getEntityData().get(MisanthropicHivemindEntity.DATA_attacking)) == false
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 0) {
			if (entity instanceof MisanthropicHivemindEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MisanthropicHivemindEntity.DATA_attackChance, (int) ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) + 1));
		}
		if ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) == 40) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1, false);
				}
			}
			HivemindFangsCrossProcedure.execute(world, entity);
		}
		if ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) == 120) {
			if (entity instanceof MisanthropicHivemindEntity) {
				((MisanthropicHivemindEntity) entity).setAnimation("animation.hivemind_projectile");
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1, false);
				}
			}
			if (entity instanceof MisanthropicHivemindEntity _datEntSetL)
				_datEntSetL.getEntityData().set(MisanthropicHivemindEntity.DATA_attacking, true);
			if (entity instanceof MisanthropicHivemindEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MisanthropicHivemindEntity.DATA_attackChance, 121);
			TheDeepVoidMod.queueServerWork(20, () -> {
				if (entity instanceof MisanthropicHivemindEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MisanthropicHivemindEntity.DATA_bloodSpit, 30);
			});
		}
		if ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) == 200) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1, false);
				}
			}
			HivemindFangsDiagonalProcedure.execute(world, entity);
		}
		if ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) == 300
				&& !(!world.getEntitiesOfClass(SawThrowerEntity.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50), e -> true).isEmpty())
				&& !(!world.getEntitiesOfClass(SkullSmasherEntity.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50), e -> true).isEmpty())) {
			if (entity instanceof MisanthropicHivemindEntity _datEntSetL)
				_datEntSetL.getEntityData().set(MisanthropicHivemindEntity.DATA_attacking, true);
			if (entity instanceof MisanthropicHivemindEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MisanthropicHivemindEntity.DATA_attackChance, 301);
			if (entity instanceof MisanthropicHivemindEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MisanthropicHivemindEntity.DATA_sucking, 140);
		} else if ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) == 300
				&& (!world.getEntitiesOfClass(SawThrowerEntity.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50), e -> true).isEmpty()
						|| !world.getEntitiesOfClass(SkullSmasherEntity.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50), e -> true).isEmpty())) {
			if (entity instanceof MisanthropicHivemindEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MisanthropicHivemindEntity.DATA_attackChance, 320);
		}
		if ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) == 320) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, 1, false);
				}
			}
			HivemindSurroundWithFangsProcedure.execute(world, entity);
			if ((entity instanceof MisanthropicHivemindEntity _datEntL42 && _datEntL42.getEntityData().get(MisanthropicHivemindEntity.DATA_digesting)) == true) {
				if (entity instanceof MisanthropicHivemindEntity _datEntSetL)
					_datEntSetL.getEntityData().set(MisanthropicHivemindEntity.DATA_digesting, false);
			}
		}
		if ((entity instanceof MisanthropicHivemindEntity _datEntI ? _datEntI.getEntityData().get(MisanthropicHivemindEntity.DATA_attackChance) : 0) >= 400) {
			if (entity instanceof MisanthropicHivemindEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MisanthropicHivemindEntity.DATA_attackChance, 0);
		}
		HivemindSpitBloodProcedure.execute(world, x, y, z, entity);
		HivemindSuckingProcedure.execute(world, x, y, z, entity);
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 2
				&& (entity instanceof MisanthropicHivemindEntity _datEntL48 && _datEntL48.getEntityData().get(MisanthropicHivemindEntity.DATA_summonedMinions)) == false
				&& (entity instanceof MisanthropicHivemindEntity _datEntL49 && _datEntL49.getEntityData().get(MisanthropicHivemindEntity.DATA_skipSummons)) == false) {
			if (entity instanceof MisanthropicHivemindEntity _datEntSetL)
				_datEntSetL.getEntityData().set(MisanthropicHivemindEntity.DATA_summonedMinions, true);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, (float) 1.2);
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream")), SoundSource.HOSTILE, 2, (float) 1.2, false);
				}
			}
			HivemindSummonMinionsProcedure.execute(world, entity);
		}
		if (!(!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 80, 80, 80), e -> true).isEmpty()) && DeepVoidConfigConfiguration.BOSSIDLES.get() == true) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.HIVE_BRAIN.get().spawn(_level, BlockPos.containing(x, y, z), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof SawThrowerEntity || entityiterator instanceof BoneSawEntity || entityiterator instanceof SkullSmasherEntity || entityiterator instanceof HivemindTentaclesEntity
							|| entityiterator instanceof BloodSpitEntity) {
						if (!entityiterator.level().isClientSide())
							entityiterator.discard();
					}
				}
			}
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"stopsound @a record the_deep_void:pitch_black");
			TheDeepVoidMod.queueServerWork(20, () -> {
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"stopsound @a record the_deep_void:pitch_black");
			});
			if (!entity.level().isClientSide())
				entity.discard();
		}
		WeaverBossMusicProcedure.execute(world, x, y, z, entity);
		if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50), e -> true).isEmpty()) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(50 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.WEAVER_CURSE.holder(), 5, 0, false, false));
					}
				}
			}
		}
		if ((!world.getEntitiesOfClass(SawThrowerEntity.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50), e -> true).isEmpty() || !world.getEntitiesOfClass(SkullSmasherEntity.class, AABB.ofSize(new Vec3(x, y, z), 50, 50, 50), e -> true).isEmpty())
				&& !(!world.getEntitiesOfClass(HivemindTentaclesEntity.class, AABB.ofSize(new Vec3(x, y, z), 4, 4, 4), e -> true).isEmpty())) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.HIVEMIND_TENTACLES.get().spawn(_level, BlockPos.containing(x, y, z), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		}
	}
}
