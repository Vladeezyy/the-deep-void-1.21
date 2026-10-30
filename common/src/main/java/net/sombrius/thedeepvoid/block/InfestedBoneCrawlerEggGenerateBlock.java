
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sombrius.thedeepvoid.port.neo.DeferredSoundType;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.procedures.InfestedCrawlerEggWalkOnBlockProcedure;

public class InfestedBoneCrawlerEggGenerateBlock extends FlowerBlock implements net.sombrius.thedeepvoid.port.PortBlockHooks.DestroyedByPlayer {
	public InfestedBoneCrawlerEggGenerateBlock() {
		super(MobEffects.SPEED, 100,
				net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.GLOW_LICHEN).randomTicks()
						.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.turtle.egg_break")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.stone.step")),
								() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.stone.place")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.turtle.egg_crack")),
								() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.stone.fall"))))
						.strength(0f, 1f).noOcclusion().dynamicShape().offsetType(BlockBehaviour.OffsetType.NONE).pushReaction(PushReaction.DESTROY));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(pos);
		return box(3, 0, 3, 13, 16, 13).move(offset.x, offset.y, offset.z);
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData) {
		return new ItemStack(TheDeepVoidModBlocks.INFESTED_BONE_CRAWLER_EGG.get());
	}


	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = net.sombrius.thedeepvoid.port.PortUtil.destroyedByPlayer(world, pos, fluid);
		InfestedCrawlerEggWalkOnBlockProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
		return retval;
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState blockstate, Entity entity) {
		super.stepOn(world, pos, blockstate, entity);
		InfestedCrawlerEggWalkOnBlockProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
	}
}
