
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class RottenButtonBlock extends ButtonBlock {
	public RottenButtonBlock() {
		super(BlockSetType.OAK, 30, BlockBehaviour.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.BASALT).strength(2.2f, 16f).dynamicShape());
	}
}
