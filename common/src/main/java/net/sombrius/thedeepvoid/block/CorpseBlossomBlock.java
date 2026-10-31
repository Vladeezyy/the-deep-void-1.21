
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sombrius.thedeepvoid.procedures.CorpseBlossomOnTickUpdateProcedure;

public class CorpseBlossomBlock extends FlowerBlock {
	public CorpseBlossomBlock() {
		super(MobEffects.SPEED, 100,
				net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.COLOR_PINK).randomTicks().sound(SoundType.SCULK_VEIN).instabreak().noCollision().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.POPPED));
	}


	@Override
	public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		CorpseBlossomOnTickUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
