
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class GooGrowthBlock extends DoublePlantBlock {
	public GooGrowthBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.GLOW_LICHEN).sound(SoundType.SLIME_BLOCK).instabreak().noCollision().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.POPPED));
	}

}
