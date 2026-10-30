
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class IchorLanternBlock extends Block {
	public IchorLanternBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.GOLD).sound(SoundType.FROGLIGHT).strength(0.3f).lightLevel(s -> 12).emissiveRendering((bs, br, bp) -> true));
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}
}
