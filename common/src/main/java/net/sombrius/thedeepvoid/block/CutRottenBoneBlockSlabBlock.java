
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class CutRottenBoneBlockSlabBlock extends SlabBlock {
	public CutRottenBoneBlockSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).sound(SoundType.BONE_BLOCK).strength(2.2f, 16f).requiresCorrectToolForDrops().dynamicShape());
	}
}
