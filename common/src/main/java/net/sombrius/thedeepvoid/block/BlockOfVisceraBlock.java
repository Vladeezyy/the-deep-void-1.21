
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.sombrius.thedeepvoid.port.neo.DeferredSoundType;

public class BlockOfVisceraBlock extends Block {
	public BlockOfVisceraBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps()
				.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.break")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_step")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_place")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.hit")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.wet_grass.fall"))))
				.strength(1f, 4f).friction(0.7f).speedFactor(0.9f));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}
