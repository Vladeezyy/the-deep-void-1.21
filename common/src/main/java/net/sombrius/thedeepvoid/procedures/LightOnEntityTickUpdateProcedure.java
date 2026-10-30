package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;

public class LightOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world.getBlockState(BlockPos.containing(x, y, z)).getLightEmission() == 0 && world.getBlockState(BlockPos.containing(x, y - 1, z)).getLightEmission() == 0) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if ((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("brainFog", false) == true || y <= 0) && DeepVoidConfigConfiguration.DESTROYLIGHTSOURCES.get() == true) {
			if (Math.random() < 0.009 && !(world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:safe_lights")))
					&& !(!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).isEmpty())) {
				if (!entity.level().isClientSide())
					entity.discard();
				world.destroyBlock(BlockPos.containing(x, y, z), false);
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		}
	}
}
