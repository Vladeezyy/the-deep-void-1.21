
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class VoidRoseBlock extends FlowerBlock {
	public VoidRoseBlock() {
		super(MobEffects.SPEED, 100, net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.TERRACOTTA_BLACK).sound(SoundType.TWISTING_VINES).instabreak().noCollision().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY));
	}

}
