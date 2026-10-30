package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.ViewportEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

@EventBusSubscriber(value = Dist.CLIENT)
public class PlayerInLiquidIchorProcedure {
	public static ViewportEvent.RenderFog provider = null;

	public static void setDistance(float start, float end) {
		provider.setNearPlaneDistance(start);
		provider.setFarPlaneDistance(end);
		if (!provider.isCanceled()) {
			provider.setCanceled(true);
		}
	}

	public static void setShape(ViewportEvent.FogShape shape) {
		provider.setFogShape(shape);
		if (!provider.isCanceled()) {
			provider.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void renderFog(ViewportEvent.RenderFog event) {
		provider = event;
		if (provider.getMode() == ViewportEvent.FogMode.FOG_TERRAIN) {
			ClientLevel level = Minecraft.getInstance().level;
			Entity entity = provider.getCamera().entity();
			if (level != null && entity != null) {
				Vec3 pos = entity.getPosition((float) provider.getPartialTick());
				execute(provider, level, entity);
			}
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if ((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()))).getBlock() == TheDeepVoidModBlocks.LIQUID_ICHOR.get()
				&& (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY() + 1.5, entity.getZ()))).getBlock() == TheDeepVoidModBlocks.LIQUID_ICHOR.get()
				|| (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()))).getBlock() == TheDeepVoidModBlocks.LIQUID_ICHOR.get() && entity.isSwimming()) {
			setShape(ViewportEvent.FogShape.SPHERE);
			setDistance(1, 3);
		} else if ((world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()))).getBlock() == TheDeepVoidModBlocks.LIQUID_VOID.get()) {
			setShape(ViewportEvent.FogShape.SPHERE);
			setDistance(0, 2);
		}
	}
}
