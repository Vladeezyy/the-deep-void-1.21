package net.sombrius.thedeepvoid.procedures;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class DesolateSoilEntityWalksOnBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.DEEPSLATE || (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == TheDeepVoidModBlocks.ANCIENT_DEEPSLATE.get())
				&& (entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"))) {
			if (entity instanceof ServerPlayer _player) {
				AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("the_deep_void:feeling_watched"));
				if (_adv != null) {
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
		}
	}
}
