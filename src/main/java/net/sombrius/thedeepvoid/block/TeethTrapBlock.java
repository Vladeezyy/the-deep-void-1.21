
package net.sombrius.thedeepvoid.block;

import net.sombrius.thedeepvoid.procedures.TeethTrapEntityWalksOnTheBlockProcedure;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.common.util.ForgeSoundType;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class TeethTrapBlock extends Block {
	public TeethTrapBlock() {
		super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM)
				.sound(new ForgeSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.slime_block.break")), () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_step")),
						() -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_place")), () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.slime_block.hit")),
						() -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.wet_grass.fall"))))
				.strength(1f, 4f).friction(0.7f));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState blockstate, Entity entity) {
		super.stepOn(world, pos, blockstate, entity);
		TeethTrapEntityWalksOnTheBlockProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
	}
}
