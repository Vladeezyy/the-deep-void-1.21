
package net.sombrius.thedeepvoid.block;

import net.minecraft.world.level.LevelReader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sombrius.thedeepvoid.port.neo.DeferredSoundType;

public class GooCoverBlockBlock extends Block implements SimpleWaterloggedBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final EnumProperty<AttachFace> FACE = FaceAttachedHorizontalDirectionalBlock.FACE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public GooCoverBlockBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.GLOW_LICHEN)
				.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.break")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_step")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:flesh_block_step")), () -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.hit")),
						() -> BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.slime_block.fall"))))
				.strength(0.1f, 0.5f).noCollision().speedFactor(0.8f).jumpFactor(0.9f).noOcclusion().pushReaction(PushReaction.POPPED).isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL).setValue(WATERLOGGED, false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			default -> switch (state.getValue(FACE)) {
				case FLOOR -> box(0, 0, 0, 16, 1, 16);
				case WALL -> box(0, 0, 0, 16, 16, 1);
				case CEILING -> box(0, 15, 0, 16, 16, 16);
			};
			case NORTH -> switch (state.getValue(FACE)) {
				case FLOOR -> box(0, 0, 0, 16, 1, 16);
				case WALL -> box(0, 0, 15, 16, 16, 16);
				case CEILING -> box(0, 15, 0, 16, 16, 16);
			};
			case EAST -> switch (state.getValue(FACE)) {
				case FLOOR -> box(0, 0, 0, 16, 1, 16);
				case WALL -> box(0, 0, 0, 1, 16, 16);
				case CEILING -> box(0, 15, 0, 16, 16, 16);
			};
			case WEST -> switch (state.getValue(FACE)) {
				case FLOOR -> box(0, 0, 0, 16, 1, 16);
				case WALL -> box(15, 0, 0, 16, 16, 16);
				case CEILING -> box(0, 15, 0, 16, 16, 16);
			};
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, FACE, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		if (context.getClickedFace().getAxis() == Direction.Axis.Y)
			return this.defaultBlockState().setValue(FACE, context.getClickedFace().getOpposite() == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, flag);
		return this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, context.getClickedFace()).setValue(WATERLOGGED, flag);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state, LevelReader world, net.minecraft.world.level.ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, net.minecraft.util.RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			tickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return super.updateShape(state, world, tickAccess, currentPos, facing, facingPos, facingState, random);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return context.getItemInHand().getItem() != this.asItem();
	}
}
