
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BoneMarrowBlock extends FlowerBlock {
	public BoneMarrowBlock() {
		super(MobEffects.BLINDNESS, 100,
				net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.COLOR_GRAY).sound(SoundType.BONE_BLOCK).strength(0.2f, 0.5f).noCollision().replaceable().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.POPPED));
	}

}
