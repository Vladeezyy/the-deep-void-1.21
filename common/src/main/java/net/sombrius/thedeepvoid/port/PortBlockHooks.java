package net.sombrius.thedeepvoid.port;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

/** NeoForge block hooks the mod's blocks implement; NeoForge calls them itself, the Fabric module through mixins */
public final class PortBlockHooks {
	private PortBlockHooks() {
	}

	/** NeoForge's {@code onDestroyedByPlayer}: removes the block for a player (and runs the mod's procedure) */
	public interface DestroyedByPlayer {
		boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid);
	}
}
