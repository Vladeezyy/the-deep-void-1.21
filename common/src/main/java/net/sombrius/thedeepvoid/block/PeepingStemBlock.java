
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sombrius.thedeepvoid.port.neo.DeferredSoundType;

public class PeepingStemBlock extends FlowerBlock {
	public PeepingStemBlock() {
		super(MobEffects.SPEED, 100,
				net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().mapColor(MapColor.NETHER)
						.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_step")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty")),
								() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_place")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty")),
								() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty"))))
						.instabreak().lightLevel(s -> 4).noCollision().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.POPPED));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(pos);
		return box(0, 0, 0, 16, 16, 16).move(offset.x, offset.y, offset.z);
	}

}
