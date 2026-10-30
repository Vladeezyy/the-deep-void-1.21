package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.ChainedWeaverEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class ChainedWeaverRightClickedOnEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).putDouble("deep_void:discuss", (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) + 1));
		if (sourceentity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.VOID_BLESSING.holder())) {
			net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).putBoolean("deep_void:talkingVoidBlessing", true);
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getBooleanOr("deep_void:talkingVoidBlessing", false) == true) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line1").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
				if (entity instanceof ChainedWeaverEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ChainedWeaverEntity.DATA_talking, true);
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line2").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line3").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line4").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 5) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line5").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 6) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line6").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 7) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line7").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 8) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.blessing_line9").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) >= 8) {
				net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).putDouble("deep_void:discuss", 0);
				if (entity instanceof ChainedWeaverEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ChainedWeaverEntity.DATA_talking, false);
			}
		}
		if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getBooleanOr("deep_void:talkingVoidBlessing", false) == false) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.normal_line1").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
				if (entity instanceof ChainedWeaverEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ChainedWeaverEntity.DATA_talking, true);
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.normal_line2").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.normal_line3").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.normal_line4").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) == 5) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.normal_line14").getString())));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, (float) 0.9);
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient")), SoundSource.HOSTILE, 1, (float) 0.9, false);
					}
				}
			} else if (net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).getDoubleOr("deep_void:discuss", 0) >= 6) {
				net.sombrius.thedeepvoid.port.PortUtil.data(sourceentity).putDouble("deep_void:discuss", 0);
				if (entity instanceof ChainedWeaverEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ChainedWeaverEntity.DATA_talking, false);
			}
		}
	}
}
