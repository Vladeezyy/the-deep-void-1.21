package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class BlockOfBonePileOnBoneMealSuccessProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world.isEmptyBlock(BlockPos.containing(x, y + 1, z)) && (world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block")))) {
			world.setBlock(BlockPos.containing(x, y + 1, z), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x + 2, y + 1, z)) && (world.getBlockState(BlockPos.containing(x + 2, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x + 2, y + 1, z), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x + 1, y + 1, z)) && (world.getBlockState(BlockPos.containing(x + 1, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x + 1, y + 1, z), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x - 1, y + 1, z)) && (world.getBlockState(BlockPos.containing(x - 1, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x - 1, y + 1, z), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x - 2, y + 1, z)) && (world.getBlockState(BlockPos.containing(x - 2, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x - 2, y + 1, z), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x, y + 1, z + 2)) && (world.getBlockState(BlockPos.containing(x, y, z + 2))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x, y + 1, z + 2), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x, y + 1, z + 1)) && (world.getBlockState(BlockPos.containing(x, y, z + 1))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x, y + 1, z + 1), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x, y + 1, z - 1)) && (world.getBlockState(BlockPos.containing(x, y, z - 1))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x, y + 1, z - 1), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x, y + 1, z - 2)) && (world.getBlockState(BlockPos.containing(x, y, z - 2))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x, y + 1, z - 2), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x + 1, y + 1, z + 1)) && (world.getBlockState(BlockPos.containing(x + 1, y, z + 1))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x + 1, y + 1, z + 1), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x - 1, y + 1, z + 1)) && (world.getBlockState(BlockPos.containing(x - 1, y, z + 1))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x - 1, y + 1, z + 1), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x + 1, y + 1, z - 1)) && (world.getBlockState(BlockPos.containing(x + 1, y, z - 1))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x + 1, y + 1, z - 1), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
		if (world.isEmptyBlock(BlockPos.containing(x - 1, y + 1, z - 1)) && (world.getBlockState(BlockPos.containing(x - 1, y, z - 1))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:natural_bone_block"))) && Math.random() < 0.4) {
			world.setBlock(BlockPos.containing(x - 1, y + 1, z - 1), TheDeepVoidModBlocks.BONE_MARROW.get().defaultBlockState(), 3);
		}
	}
}
