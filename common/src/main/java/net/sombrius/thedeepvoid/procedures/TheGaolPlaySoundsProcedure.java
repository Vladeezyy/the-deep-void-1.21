package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.PlayerTickEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class TheGaolPlaySoundsProcedure {
	private static final ResourceLocation BIOME_MISTED_REMNANTS = ResourceLocation.parse("the_deep_void:misted_remnants");
	private static final ResourceKey<Level> DIM_THE_PIT = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:the_pit"));

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity.level().dimension()) == DIM_THE_PIT && !(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(TheDeepVoidModMobEffects.PIT_SHAKE.holder()))
				&& Math.random() < 0.0005) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.PIT_SHAKE.holder(), 100, 0, false, false));
		}
		if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_MISTED_REMNANTS)
				&& TheDeepVoidModVariables.of(entity).remnantsFog > 40) {
			{
				double _setval = Math.round(TheDeepVoidModVariables.of(entity).remnantsFog / 1.1);
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.remnantsFog = _setval;
				_vars.syncPlayerVariables(entity);
			}
		} else if (!world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_MISTED_REMNANTS)
				&& TheDeepVoidModVariables.of(entity).remnantsFog <= 40) {
			{
				double _setval = 400;
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.remnantsFog = _setval;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_MISTED_REMNANTS)
				&& TheDeepVoidModVariables.of(entity).remnantsColor < 189) {
			{
				double _setval = TheDeepVoidModVariables.of(entity).remnantsColor + 5;
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.remnantsColor = _setval;
				_vars.syncPlayerVariables(entity);
			}
		} else if (!world.getBiome(BlockPos.containing(x, y, z)).is(BIOME_MISTED_REMNANTS)
				&& TheDeepVoidModVariables.of(entity).remnantsColor > 24) {
			{
				double _setval = 24;
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.remnantsColor = _setval;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
