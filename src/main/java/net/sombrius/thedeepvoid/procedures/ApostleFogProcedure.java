package net.sombrius.thedeepvoid.procedures;

import com.mojang.blaze3d.shaders.FogShape;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.entity.ApostleBossEntity;

@EventBusSubscriber(value = Dist.CLIENT)
public class ApostleFogProcedure {
	public static ViewportEvent.RenderFog provider = null;

	public static void setDistance(float start, float end) {
		provider.setNearPlaneDistance(start);
		provider.setFarPlaneDistance(end);
		if (!provider.isCanceled()) {
			provider.setCanceled(true);
		}
	}

	public static void setShape(FogShape shape) {
		provider.setFogShape(shape);
		if (!provider.isCanceled()) {
			provider.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void renderFog(ViewportEvent.RenderFog event) {
		provider = event;
		if (provider.getMode() == FogRenderer.FogMode.FOG_TERRAIN) {
			ClientLevel level = Minecraft.getInstance().level;
			Entity entity = provider.getCamera().getEntity();
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
		if (!world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 40, 40, 40), e -> true).isEmpty()) {
			if ((((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 40, 40, 40), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)) instanceof LivingEntity _livEnt
					? _livEnt.getHealth()
					: -1) <= (((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)) instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / (double) DeepVoidConfigConfiguration.APOSTLESECONDPHASE.get()) {
				setDistance(3, 18);
				setShape(FogShape.SPHERE);
				if ((((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 40, 40, 40), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)) instanceof ApostleBossEntity _datEntL21 && _datEntL21.getEntityData().get(ApostleBossEntity.DATA_fog)) == false) {
					if (((Entity) world.getEntitiesOfClass(ApostleBossEntity.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 40, 40, 40), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)) instanceof ApostleBossEntity _datEntSetL)
						_datEntSetL.getEntityData().set(ApostleBossEntity.DATA_fog, true);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:fog_appear")), SoundSource.HOSTILE, 4, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:fog_appear")), SoundSource.HOSTILE, 4, 1, false);
						}
					}
				}
			}
		}
	}
}
