package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class HuntersHelmetTickEventProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity.isSprinting()) {
			if (TheDeepVoidModVariables.of(entity).armorToggle == false && itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("cooldownMask", 0) <= 0) {
				{
					final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof LivingEntity && !(entityiterator == entity) && !(entityiterator == (entity.getVehicle()))
								&& !(entityiterator instanceof TamableAnimal _tamIsTamedBy && entity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false)
								&& !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:hunter_helmet_unaffected")))) {
							entityiterator.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("the_deep_void:void_energy"))), entity),
									5);
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse("the_deep_void:boss")))) {
								entityiterator.setDeltaMovement(new Vec3((Math.sin(Math.toRadians(entity.getYRot() + 180)) * 1), 0.2, (Math.cos(Math.toRadians(entity.getYRot())) * 1)));
							}
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 10, 4, false, false));
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 20, 2, false, false));
							{
								final String _tagName = "playsound";
								final boolean _tagValue = true;
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
							}
							{
								final String _tagName = "cooldownMask";
								final double _tagValue = 40;
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
							}
						}
					}
				}
				if (entity instanceof LivingEntity _livEnt25 && _livEnt25.hasEffect(MobEffects.SLOWNESS)) {
					entity.setSprinting(false);
					if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("playsound", false) == true) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.anvil.place")), SoundSource.PLAYERS, 1, (float) 1.1);
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.anvil.place")), SoundSource.PLAYERS, 1, (float) 1.1, false);
							}
						}
						{
							final String _tagName = "playsound";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
				}
			}
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "7b6cd0e5-5944-438f-8d85-b74e6b98f8d8")) != null)) {
				if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).hasModifier(Identifier.fromNamespaceAndPath("the_deep_void", "7b6cd0e5-5944-438f-8d85-b74e6b98f8d8"))))
					((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).addTransientModifier((new AttributeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "7b6cd0e5-5944-438f-8d85-b74e6b98f8d8"),
							(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getValue() * 0.2 * 2), AttributeModifier.Operation.ADD_VALUE)));
			}
		} else if (!entity.isSprinting() && ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).getModifier(Identifier.fromNamespaceAndPath("the_deep_void", "7b6cd0e5-5944-438f-8d85-b74e6b98f8d8")) != null) {
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED).removeModifier(Identifier.fromNamespaceAndPath("the_deep_void", "7b6cd0e5-5944-438f-8d85-b74e6b98f8d8"));
		}
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("cooldownMask", 0) > 0) {
			{
				final String _tagName = "cooldownMask";
				final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("cooldownMask", 0) - 1);
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
			}
		}
	}
}
