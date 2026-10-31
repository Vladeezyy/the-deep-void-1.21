package net.sombrius.thedeepvoid.procedures;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.BabyBoneCrawlerEntity;
import net.sombrius.thedeepvoid.entity.MaggotEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

public class MaggotThisEntityKillsAnotherOneProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity instanceof BabyBoneCrawlerEntity) {
			if (sourceentity instanceof MaggotEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MaggotEntity.DATA_killed, (int) ((sourceentity instanceof MaggotEntity _datEntI ? _datEntI.getEntityData().get(MaggotEntity.DATA_killed) : 0) + 1));
			if ((sourceentity instanceof MaggotEntity _datEntI ? _datEntI.getEntityData().get(MaggotEntity.DATA_killed) : 0) >= 2) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:fleshy_explosion")), SoundSource.HOSTILE, 1,
								(float) Mth.nextDouble(RandomSource.create(), 1.2, 1.4));
					} else {
						_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:fleshy_explosion")), SoundSource.HOSTILE, 1,
								(float) Mth.nextDouble(RandomSource.create(), 1.2, 1.4), false);
					}
				}
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = TheDeepVoidModEntities.BLACK_FLY.get().spawn(_level, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), EntitySpawnReason.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(
							new CommandSourceStack(CommandSource.NULL, new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ())), Vec2.ZERO, _level, LevelBasedPermissionSet.OWNER, Component.literal(""), _level.getServer()).withSuppressedOutput(),
							"/loot spawn ~ ~ ~ loot the_deep_void:entities/maggot_grow");
				if (!sourceentity.level().isClientSide())
					sourceentity.discard();
			}
		}
	}
}
