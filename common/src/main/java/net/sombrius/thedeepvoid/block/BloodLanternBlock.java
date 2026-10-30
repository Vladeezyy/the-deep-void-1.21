
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BloodLanternBlock extends Block {
	public BloodLanternBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.FIRE).sound(SoundType.FROGLIGHT).strength(0.3f).lightLevel(s -> 12).emissiveRendering(bs -> true));
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}
}
