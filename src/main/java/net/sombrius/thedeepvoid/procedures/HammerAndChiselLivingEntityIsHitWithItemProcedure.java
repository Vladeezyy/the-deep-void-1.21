package net.sombrius.thedeepvoid.procedures;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class HammerAndChiselLivingEntityIsHitWithItemProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.DESTINED_TO_DIE)
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.HAMMER_AND_CHISEL.get()
				&& !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:chiseled")))) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.DESTINED_TO_DIE);
			if (sourceentity instanceof Player _player)
				_player.getCooldowns().addCooldown((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 1);
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("the_deep_void:chiseled"))), sourceentity),
					(float) (amount * 2));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:hammer_and_chisel_hit")), SoundSource.PLAYERS, 1,
							(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:hammer_and_chisel_hit")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
				}
			}
			entity.getPersistentData().putDouble("hammerMaxHealth", (entity.getPersistentData().getDouble("hammerMaxHealth") + Math.floor(amount / 2)));
			((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).removeModifier(UUID.fromString("4494e752-bd57-4276-b506-a958216b544b"));
			if (!(((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH)
					.hasModifier((new AttributeModifier(UUID.fromString("4494e752-bd57-4276-b506-a958216b544b"), "hammerMaxHealth", (entity.getPersistentData().getDouble("hammerMaxHealth") * (-1)), AttributeModifier.Operation.ADD_VALUE)))))
				((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).addTransientModifier(
						(new AttributeModifier(UUID.fromString("4494e752-bd57-4276-b506-a958216b544b"), "hammerMaxHealth", (entity.getPersistentData().getDouble("hammerMaxHealth") * (-1)), AttributeModifier.Operation.ADD_VALUE)));
		}
	}
}
