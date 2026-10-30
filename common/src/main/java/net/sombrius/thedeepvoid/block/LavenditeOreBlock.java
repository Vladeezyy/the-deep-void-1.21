
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.sombrius.thedeepvoid.port.neo.DeferredSoundType;
import net.sombrius.thedeepvoid.procedures.LavenditeOreBlockDestroyedByPlayerProcedure;

public class LavenditeOreBlock extends Block implements net.sombrius.thedeepvoid.port.PortBlockHooks.DestroyedByPlayer {
	public LavenditeOreBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.DEEPSLATE)
				.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.amethyst_block.break")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.amethyst_block.step")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.amethyst_block.place")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.deepslate.hit")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.deepslate.fall"))))
				.strength(2f, 4f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}

	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = net.sombrius.thedeepvoid.port.PortUtil.destroyedByPlayer(world, pos, fluid);
		LavenditeOreBlockDestroyedByPlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
		return retval;
	}
}
