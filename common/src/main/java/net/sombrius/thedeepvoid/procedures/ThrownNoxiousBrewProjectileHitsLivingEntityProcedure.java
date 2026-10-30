package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.bus.ICancellableEvent;

import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.LivingIncomingDamageEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.neo.ItemHandlerHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.ThrownNoxiousBrewEntity;
import net.sombrius.thedeepvoid.entity.UndertakerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class ThrownNoxiousBrewProjectileHitsLivingEntityProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, immediatesourceentity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		if (immediatesourceentity instanceof ThrownNoxiousBrewEntity) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.shulker_bullet.hit")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.shulker_bullet.hit")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			if (entity instanceof UndertakerEntity) {
				if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(MobEffects.SLOWNESS))) {
					if (entity instanceof Mob) {
						try {
							((Mob) entity).setTarget(null);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if ((entity instanceof UndertakerEntity _datEntL5 && _datEntL5.getEntityData().get(UndertakerEntity.DATA_empty)) == true) {
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(
									Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.receive_brew_empty").getString())), false);
						if (sourceentity instanceof Player _player) {
							ItemStack _setstack = new ItemStack(TheDeepVoidModItems.DARK_STEEL_COIN.get()).copy();
							_setstack.setCount(Mth.nextInt(RandomSource.create(), 4, 8));
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
					} else {
						if (world instanceof ServerLevel _serverLevel) {
							Entity entityinstance = TheDeepVoidModEntities.UNDERTAKER.get().create(_serverLevel, null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), EntitySpawnReason.MOB_SUMMONED, false, false);
							if (entityinstance != null) {
								entityinstance.setYRot(world.getRandom().nextFloat() * 360.0F);
								if (entityinstance instanceof UndertakerEntity _datEntSetL)
									_datEntSetL.getEntityData().set(UndertakerEntity.DATA_talkedTo, true);
								if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("conversionQut", 0) == 0) {
									net.sombrius.thedeepvoid.port.PortUtil.data(entityinstance).putBoolean("noConversion", true);
								}
								entityinstance.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((sourceentity.getX()), (sourceentity.getY() + 1.5), (sourceentity.getZ())));
								_serverLevel.addFreshEntity(entityinstance);
							}
						}
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.receive_brew").getString())),
									false);
						if (!entity.level().isClientSide())
							entity.discard();
					}
				}
			} else {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 0));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
			}
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
		}
	}
}
