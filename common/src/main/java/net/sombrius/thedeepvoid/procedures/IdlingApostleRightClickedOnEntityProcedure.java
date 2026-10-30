package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.IdlingApostleEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class IdlingApostleRightClickedOnEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.VOID_BLESSING.holder())) {
			if (entity instanceof IdlingApostleEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IdlingApostleEntity.DATA_apostleDiscuss, (int) ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) + 1));
			if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line1").getString())));
				if (entity instanceof IdlingApostleEntity _datEntSetL)
					_datEntSetL.getEntityData().set(IdlingApostleEntity.DATA_talking, true);
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line2").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line3").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line4").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 5) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line5").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 6) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line6").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 7) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line7").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 8) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line8").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 9) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line9").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 10) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line10").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 11) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line11").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 12) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line12").getString())));
			} else if ((entity instanceof IdlingApostleEntity _datEntI ? _datEntI.getEntityData().get(IdlingApostleEntity.DATA_apostleDiscuss) : 0) == 13) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal((Component.translatable("text.the_deep_void.apostle.blessing_line13").getString())));
				entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy"))), sourceentity), 1);
			}
		}
	}
}
