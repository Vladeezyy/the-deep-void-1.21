package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

public class SmithingTemplateDamageRightClickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.SWORDS)
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof TridentItem
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.PICKAXES)
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.AXES)
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.SHOVELS)
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.ItemTags.HOES)
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, Identifier.parse("the_deep_void:weapons")))) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 5);
			if (!(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().id()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity))) {
				itemstack.shrink(1);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.smithing_table.use")), SoundSource.NEUTRAL, (float) 0.4, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.smithing_table.use")), SoundSource.NEUTRAL, (float) 0.4, 1, false);
				}
			}
			{
				final String _tagName = "darksteelSpeed";
				final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelSpeed", 0)
							+ ((LivingEntity) entity).getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED).getValue() * 0.25);
				CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
			}
			{
				final String _tagName = "darksteelDamage";
				final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("darksteelDamage", 0) + 1);
				CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
			}
			{
				final String _tagName = "modification";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
			}
		}
	}
}
