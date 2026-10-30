
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AshBrickWallBlock extends WallBlock {
	public AshBrickWallBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().sound(SoundType.SOUL_SOIL).strength(1.2f, 0f).dynamicShape().forceSolidOn());
	}
}
