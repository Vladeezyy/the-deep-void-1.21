package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.ViewportEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber(value = Dist.CLIENT)
public class TotalBlindnessFogProcedure {
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
				execute(provider, entity);
			}
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.TOTAL_BLINDNESS.holder())) {
			setDistance(0, 0);
			setShape(ViewportEvent.FogShape.SPHERE);
		}
	}
}
