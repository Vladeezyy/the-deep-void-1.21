package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * A flowing fluid built from properties (NeoForge's {@code BaseFlowingFluid}, the calls the mod uses). The fluid type
 * is an opaque supplier: NeoForge's {@code FluidType} on NeoForge (the NeoForge module's mixin returns it from
 * {@code getFluidType()}), null on Fabric.
 */
public abstract class BaseFlowingFluid extends FlowingFluid {
	private final Supplier<?> fluidType;
	private final Supplier<? extends Fluid> flowing;
	private final Supplier<? extends Fluid> still;
	@Nullable
	private final Supplier<? extends Item> bucket;
	@Nullable
	private final Supplier<? extends LiquidBlock> block;
	private final int slopeFindDistance;
	private final int levelDecreasePerBlock;
	private final float explosionResistance;
	private final int tickRate;

	protected BaseFlowingFluid(Properties properties) {
		this.fluidType = properties.fluidType;
		this.still = properties.still;
		this.flowing = properties.flowing;
		this.bucket = properties.bucket;
		this.block = properties.block;
		this.slopeFindDistance = properties.slopeFindDistance;
		this.levelDecreasePerBlock = properties.levelDecreasePerBlock;
		this.explosionResistance = properties.explosionResistance;
		this.tickRate = properties.tickRate;
	}

	/** NeoForge's FluidType of this fluid (an {@code Object} here) */
	public Object fluidTypeObject() {
		return this.fluidType.get();
	}

	@Override
	public Fluid getFlowing() {
		return this.flowing.get();
	}

	@Override
	public Fluid getSource() {
		return this.still.get();
	}

	/** NeoForge asks the fluid type instead; Fabric asks the loader what the mod's fluid type says */
	@Override
	protected boolean canConvertToSource(ServerLevel level) {
		return net.sombrius.thedeepvoid.port.platform.Platform.INSTANCE.fluidConvertsToSource(this);
	}

	@Override
	protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
		Block.dropResources(state, level, pos, blockEntity);
	}

	@Override
	protected int getSlopeFindDistance(LevelReader level) {
		return this.slopeFindDistance;
	}

	@Override
	protected int getDropOff(LevelReader level) {
		return this.levelDecreasePerBlock;
	}

	@Override
	public Item getBucket() {
		return this.bucket != null ? this.bucket.get() : Items.AIR;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
		return direction == Direction.DOWN && !isSame(fluid);
	}

	@Override
	public int getTickDelay(LevelReader level) {
		return this.tickRate;
	}

	@Override
	protected float getExplosionResistance() {
		return this.explosionResistance;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState state) {
		if (this.block != null)
			return this.block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
		return Blocks.AIR.defaultBlockState();
	}

	@Override
	public boolean isSame(Fluid fluid) {
		return fluid == this.still.get() || fluid == this.flowing.get();
	}

	public static class Properties {
		private final Supplier<?> fluidType;
		private final Supplier<? extends Fluid> still;
		private final Supplier<? extends Fluid> flowing;
		@Nullable
		private Supplier<? extends Item> bucket;
		@Nullable
		private Supplier<? extends LiquidBlock> block;
		private int slopeFindDistance = 4;
		private int levelDecreasePerBlock = 1;
		private float explosionResistance = 1;
		private int tickRate = 5;

		public Properties(Supplier<?> fluidType, Supplier<? extends Fluid> still, Supplier<? extends Fluid> flowing) {
			this.fluidType = fluidType;
			this.still = still;
			this.flowing = flowing;
		}

		public Properties bucket(Supplier<? extends Item> bucket) {
			this.bucket = bucket;
			return this;
		}

		public Properties block(Supplier<? extends LiquidBlock> block) {
			this.block = block;
			return this;
		}

		public Properties slopeFindDistance(int slopeFindDistance) {
			this.slopeFindDistance = slopeFindDistance;
			return this;
		}

		public Properties levelDecreasePerBlock(int levelDecreasePerBlock) {
			this.levelDecreasePerBlock = levelDecreasePerBlock;
			return this;
		}

		public Properties explosionResistance(float explosionResistance) {
			this.explosionResistance = explosionResistance;
			return this;
		}

		public Properties tickRate(int tickRate) {
			this.tickRate = tickRate;
			return this;
		}
	}
}
