package net.sombrius.thedeepvoid.procedures;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.PreserverEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class PreserverOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.PUS_FLOWER.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.PUS_SACK.get()) {
			if (entity instanceof PreserverEntity) {
				((PreserverEntity) entity).setAnimation("animation.preserver_attackBlock");
			}
			world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(TheDeepVoidModBlocks.PUSTULENT_FLESH_BLOCK.get().defaultBlockState()));
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		}
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == TheDeepVoidModBlocks.PUSTULENT_FLESH_BLOCK.get()) {
			if (entity instanceof PreserverEntity) {
				((PreserverEntity) entity).setAnimation("animation.preserver_attackBlock");
			}
			world.levelEvent(2001, BlockPos.containing(x, y - 1, z), Block.getId(TheDeepVoidModBlocks.PUSTULENT_FLESH_BLOCK.get().defaultBlockState()));
			{
				BlockPos _bp = BlockPos.containing(x, y - 1, z);
				BlockState _bs = TheDeepVoidModBlocks.FLESH_BLOCK.get().defaultBlockState();
				BlockState _bso = world.getBlockState(_bp);
				for (Property<?> _propertyOld : _bso.getProperties()) {
					Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
					if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
						try {
							_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
						} catch (Exception e) {
						}
				}
				world.setBlock(_bp, _bs, 3);
			}
		}
		if ((entity instanceof PreserverEntity _datEntL12 && _datEntL12.getEntityData().get(PreserverEntity.DATA_cling)) == true) {
			if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
				entity.setNoGravity(true);
				{
					Entity _ent = entity;
					_ent.teleportTo(
							((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).level()
									.clip(new ClipContext((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f),
											(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f).add((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getViewVector(1f).scale(0.5)),
											ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)))
									.getBlockPos().getX()),
							((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY() + 1.4),
							((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).level()
									.clip(new ClipContext((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f),
											(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f).add((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getViewVector(1f).scale(0.5)),
											ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)))
									.getBlockPos().getZ()));
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(
								((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).level()
										.clip(new ClipContext((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f),
												(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f).add((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getViewVector(1f).scale(0.5)),
												ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)))
										.getBlockPos().getX()),
								((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY() + 1.4),
								((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).level()
										.clip(new ClipContext((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f),
												(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f).add((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getViewVector(1f).scale(0.5)),
												ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)))
										.getBlockPos().getZ()),
								_ent.getYRot(), _ent.getXRot());
				}
				entity.lookAt(EntityAnchorArgument.Anchor.EYES,
						new Vec3(
								((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).level()
										.clip(new ClipContext((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f),
												(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f).add((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getViewVector(1f).scale((-0.5))),
												ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)))
										.getBlockPos().getX()),
								((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY()),
								((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).level()
										.clip(new ClipContext((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f),
												(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getEyePosition(1f).add((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getViewVector(1f).scale((-0.5))),
												ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)))
										.getBlockPos().getZ())));
				(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MOB_ATTACK), entity), 1);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, false, false));
			}
		}
	}
}
