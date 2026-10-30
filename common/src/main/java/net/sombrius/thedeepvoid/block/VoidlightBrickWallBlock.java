
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class VoidlightBrickWallBlock extends WallBlock {
	public VoidlightBrickWallBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 2f).lightLevel(s -> 6).dynamicShape().forceSolidOn());
	}
}
