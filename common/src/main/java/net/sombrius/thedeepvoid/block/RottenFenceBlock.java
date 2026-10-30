
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class RottenFenceBlock extends FenceBlock {
	public RottenFenceBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_GRAY).sound(SoundType.BASALT).strength(2.2f, 16f).dynamicShape().forceSolidOn());
	}
}
