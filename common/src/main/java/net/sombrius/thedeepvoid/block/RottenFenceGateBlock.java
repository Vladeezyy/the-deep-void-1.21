
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

public class RottenFenceGateBlock extends FenceGateBlock {
	public RottenFenceGateBlock() {
		super(WoodType.OAK, BlockBehaviour.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_GRAY).sound(SoundType.BASALT).strength(2.2f, 16f).dynamicShape().forceSolidOn());
	}
}
