
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.sombrius.thedeepvoid.port.neo.DeferredSoundType;

public class MeltedGooSlabBlock extends SlabBlock {
	public MeltedGooSlabBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps()
				.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.break")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_step")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_step")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.hit")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.fall"))))
				.strength(0.8f, 1f).dynamicShape());
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 4;
	}
}
