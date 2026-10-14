package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class EntityDiesBlossomingProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.BLOSSOM) && !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("the_deep_void:boss")))) {
			if ((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()))).getBlock() == Blocks.AIR && world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ())).canOcclude()) {
				world.setBlock(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), TheDeepVoidModBlocks.CORPSE_BLOSSOM.get().defaultBlockState(), 3);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.chorus_flower.grow")), SoundSource.BLOCKS, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.8, 1));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.chorus_flower.grow")), SoundSource.BLOCKS, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.8, 1), false);
					}
				}
			}
		}
	}
}
