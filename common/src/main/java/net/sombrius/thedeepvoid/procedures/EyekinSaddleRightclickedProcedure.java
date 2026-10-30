package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.entity.RideableEyekinEntity;
import net.sombrius.thedeepvoid.entity.RideableFlyingEyekinEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class EyekinSaddleRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("deep_void:eyekinSummoned", false) == false && !(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.WEAVER_CURSE.holder()))) {
			{
				final String _tagName = "deep_void:eyekinSummoned";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 200);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = TheDeepVoidModEntities.RIDEABLE_EYEKIN.get().spawn(_level, BlockPos.containing(x, y, z), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof RideableEyekinEntity) {
						if (entityiterator instanceof TamableAnimal _toTame && entity instanceof Player _owner)
							_toTame.tame(_owner);
					}
				}
			}
		}
		TheDeepVoidMod.queueServerWork(5, () -> {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("deep_void:eyekinSummoned", false) == true
					&& (entity instanceof Player _plrCldRem14 ? _plrCldRem14.getCooldowns().getCooldownPercent(itemstack, 0f) * 100 : 0) <= 0) {
				if (!world.getEntitiesOfClass(RideableEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).isEmpty()) {
					if (((Entity) world.getEntitiesOfClass(RideableEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof TamableAnimal _tamIsTamedBy && entity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false) {
						if (!((Entity) world.getEntitiesOfClass(RideableEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).level().isClientSide())
							((Entity) world.getEntitiesOfClass(RideableEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).stream().sorted(new Object() {
								Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
									return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
								}
							}.compareDistOf(x, y, z)).findFirst().orElse(null)).discard();
						{
							final String _tagName = "deep_void:eyekinSummoned";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
						}
						if (entity instanceof Player _player)
							_player.getCooldowns().addCooldown(itemstack, 20);
					}
				}
				if (!world.getEntitiesOfClass(RideableFlyingEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).isEmpty()) {
					if (((Entity) world.getEntitiesOfClass(RideableFlyingEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null)) instanceof TamableAnimal _tamIsTamedBy && entity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false) {
						if (!((Entity) world.getEntitiesOfClass(RideableFlyingEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
							}
						}.compareDistOf(x, y, z)).findFirst().orElse(null)).level().isClientSide())
							((Entity) world.getEntitiesOfClass(RideableFlyingEyekinEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000, 1000, 1000), e -> true).stream().sorted(new Object() {
								Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
									return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
								}
							}.compareDistOf(x, y, z)).findFirst().orElse(null)).discard();
						{
							final String _tagName = "deep_void:eyekinSummoned";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
						}
						if (entity instanceof Player _player)
							_player.getCooldowns().addCooldown(itemstack, 20);
					}
				}
			}
		});
	}
}
