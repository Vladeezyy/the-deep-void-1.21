
package net.sombrius.thedeepvoid.block;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sombrius.thedeepvoid.block.entity.DeepslateSarcophagusBlockBlockEntity;

public class DeepslateSarcophagusBlockBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public DeepslateSarcophagusBlockBlock() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.blockProps().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_BRICKS).strength(4f, 8f).requiresCorrectToolForDrops().noOcclusion().pushReaction(PushReaction.POPPED)
				.isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
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
			default -> Shapes.or(box(0, 0, -16, 16, 2, 16), box(0, 14, -16, 16, 16, 16), box(1, 2, -15, 15, 14, 15));
			case NORTH -> Shapes.or(box(0, 0, 0, 16, 2, 32), box(0, 14, 0, 16, 16, 32), box(1, 2, 1, 15, 14, 31));
			case EAST -> Shapes.or(box(-16, 0, 0, 16, 2, 16), box(-16, 14, 0, 16, 16, 16), box(-15, 2, 1, 15, 14, 15));
			case WEST -> Shapes.or(box(0, 0, 0, 32, 2, 16), box(0, 14, 0, 32, 16, 16), box(1, 2, 1, 31, 14, 15));
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
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
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DeepslateSarcophagusBlockBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean isMoving) {
		Containers.updateNeighboursAfterDestroy(state, world, pos);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos, net.minecraft.core.Direction direction) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof DeepslateSarcophagusBlockBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}
