package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;

public class SoulHookHitsEntityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (world instanceof ServerLevel _serverLevel) {
			Entity entityinstance = TheDeepVoidModEntities.HOOK_END.get().create(_serverLevel, null, BlockPos.containing(x, y, z), EntitySpawnReason.MOB_SUMMONED, false, false);
			if (entityinstance != null) {
				entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
				net.sombrius.thedeepvoid.port.PortUtil.data(entityinstance).putString("owner", (sourceentity.getStringUUID()));
				net.sombrius.thedeepvoid.port.PortUtil.data(entityinstance).putBoolean("soul", true);
				if (entityinstance instanceof Mob _entity && entity instanceof LivingEntity _ent)
					_entity.setTarget(_ent);
				_serverLevel.addFreshEntity(entityinstance);
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.phantom.bite")), SoundSource.PLAYERS, 1, (float) 1.2);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.phantom.bite")), SoundSource.PLAYERS, 1, (float) 1.2, false);
			}
		}
	}
}
