package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.sombrius.thedeepvoid.entity.ChainedWeaverEntity;

@EventBusSubscriber
public class ChainedWeaverEntityIsHurtProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
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
		if (entity instanceof ChainedWeaverEntity) {
			if (sourceentity instanceof Player) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
				if ((entity instanceof ChainedWeaverEntity _datEntL2 && _datEntL2.getEntityData().get(ChainedWeaverEntity.DATA_free)) == false) {
					if (entity instanceof ChainedWeaverEntity _datEntSetI)
						_datEntSetI.getEntityData().set(ChainedWeaverEntity.DATA_chainBreak, (int) ((entity instanceof ChainedWeaverEntity _datEntI ? _datEntI.getEntityData().get(ChainedWeaverEntity.DATA_chainBreak) : 0) + 1));
					world.levelEvent(2001, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), Block.getId(Blocks.CHAIN.defaultBlockState()));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.chain.break")), SoundSource.HOSTILE, 2, (float) 0.8);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.chain.break")), SoundSource.HOSTILE, 2, (float) 0.8, false);
						}
					}
					if ((entity instanceof ChainedWeaverEntity _datEntI ? _datEntI.getEntityData().get(ChainedWeaverEntity.DATA_chainBreak) : 0) >= 9) {
						if (entity instanceof ChainedWeaverEntity _datEntSetL)
							_datEntSetL.getEntityData().set(ChainedWeaverEntity.DATA_free, true);
					}
					if ((entity instanceof ChainedWeaverEntity _datEntI ? _datEntI.getEntityData().get(ChainedWeaverEntity.DATA_chainBreak) : 0) == 2) {
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.hurt1").getString())), false);
					}
					if ((entity instanceof ChainedWeaverEntity _datEntI ? _datEntI.getEntityData().get(ChainedWeaverEntity.DATA_chainBreak) : 0) == 5) {
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.hurt2").getString())), false);
					}
					if ((entity instanceof ChainedWeaverEntity _datEntI ? _datEntI.getEntityData().get(ChainedWeaverEntity.DATA_chainBreak) : 0) == 7) {
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.hurt3").getString())), false);
					}
					if ((entity instanceof ChainedWeaverEntity _datEntI ? _datEntI.getEntityData().get(ChainedWeaverEntity.DATA_chainBreak) : 0) == 9) {
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("text.the_deep_void.weaver_of_souls.hurt4").getString())), false);
					}
				}
			}
		}
	}
}
