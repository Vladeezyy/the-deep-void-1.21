package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class VanishEffectStartedappliedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player || entity instanceof ServerPlayer) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getArmorValue() : 0) > 0) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmorPoint = entity instanceof LivingEntity _livEnt ? _livEnt.getArmorValue() : 0;
					_vars.syncPlayerVariables(entity);
				}
			} else {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmorPoint = 0;
					_vars.syncPlayerVariables(entity);
				}
			}
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor3.getItem() == ItemStack.EMPTY.getItem()) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor3 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).shrink(1);
			} else if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& !(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor3.getItem() == ItemStack.EMPTY.getItem())) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor3);
					entityToSpawn.setPickUpDelay(20);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor3 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).shrink(1);
			}
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor2.getItem() == ItemStack.EMPTY.getItem()) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor2 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).shrink(1);
			} else if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& !(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor2.getItem() == ItemStack.EMPTY.getItem())) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor2);
					entityToSpawn.setPickUpDelay(20);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor2 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).shrink(1);
			}
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor1.getItem() == ItemStack.EMPTY.getItem()) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor1 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).shrink(1);
			} else if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& !(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor1.getItem() == ItemStack.EMPTY.getItem())) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor1);
					entityToSpawn.setPickUpDelay(20);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor1 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).shrink(1);
			}
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor0.getItem() == ItemStack.EMPTY.getItem()) {
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor0 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).shrink(1);
			} else if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())
					&& !(entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor0.getItem() == ItemStack.EMPTY.getItem())) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmor0);
					entityToSpawn.setPickUpDelay(20);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
					_vars.saveArmor0 = ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy());
					_vars.syncPlayerVariables(entity);
				}
				(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).shrink(1);
			}
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR)
					.hasModifier((new AttributeModifier(UUID.fromString("0a6a7610-463a-4982-8c09-23fbbb6a4da7"), "vanishArmorPoints", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmorPoint, AttributeModifier.Operation.ADD_VALUE)))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).addTransientModifier(
						(new AttributeModifier(UUID.fromString("0a6a7610-463a-4982-8c09-23fbbb6a4da7"), "vanishArmorPoints", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmorPoint, AttributeModifier.Operation.ADD_VALUE)));
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS).hasModifier(
					(new AttributeModifier(UUID.fromString("ce3747e9-226d-4519-840d-0a19834844da"), "vanishArmorToughness", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmorToughness, AttributeModifier.Operation.ADD_VALUE)))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS).addTransientModifier(
						(new AttributeModifier(UUID.fromString("ce3747e9-226d-4519-840d-0a19834844da"), "vanishArmorToughness", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveArmorToughness, AttributeModifier.Operation.ADD_VALUE)));
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE).hasModifier(
					(new AttributeModifier(UUID.fromString("dc4b4a93-4a76-44f9-8ecf-9dad1cbe411e"), "vanishKnockback", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveKnockbackResistance, AttributeModifier.Operation.ADD_VALUE)))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(
						(new AttributeModifier(UUID.fromString("dc4b4a93-4a76-44f9-8ecf-9dad1cbe411e"), "vanishKnockback", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveKnockbackResistance, AttributeModifier.Operation.ADD_VALUE)));
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.STEP_HEIGHT)
					.hasModifier((new AttributeModifier(UUID.fromString("13df1bf1-3b0d-4965-b15f-6403e84af6cf"), "vanishStep", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveStepHeight, AttributeModifier.Operation.ADD_VALUE)))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.STEP_HEIGHT).addTransientModifier(
						(new AttributeModifier(UUID.fromString("13df1bf1-3b0d-4965-b15f-6403e84af6cf"), "vanishStep", entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).saveStepHeight, AttributeModifier.Operation.ADD_VALUE)));
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.POOF, x, (y + 0.5), z, 40, 0.1, 0.1, 0.1, 0.1);
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(15 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator == entity)) {
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
					if ((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity) {
						if (entityiterator instanceof Mob) {
							try {
								((Mob) entityiterator).setTarget(null);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.shulker_bullet.hit")), SoundSource.PLAYERS, 1, (float) 0.8);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.shulker_bullet.hit")), SoundSource.PLAYERS, 1, (float) 0.8, false);
			}
		}
	}
}
