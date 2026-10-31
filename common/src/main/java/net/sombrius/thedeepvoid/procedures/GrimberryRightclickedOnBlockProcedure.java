package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.sombrius.thedeepvoid.port.bus.PlayerInteractEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class GrimberryRightclickedOnBlockProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getLevel().getBlockState(event.getPos()), event.getFace(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Direction direction, Entity entity) {
		execute(null, world, x, y, z, blockstate, direction, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockState blockstate, Direction direction, Entity entity) {
		if (direction == null || entity == null)
			return;
		if (((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIMBERRY.get()
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIMBERRY.get()) && blockstate.getBlock() == TheDeepVoidModBlocks.DESOLATE_SOIL.get()
				&& world.isEmptyBlock(BlockPos.containing(x, y + 1, z)) && direction == Direction.UP) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIMBERRY.get()) {
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
					(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				}
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, net.minecraft.world.item.component.SwingAnimation.DEFAULT, true);
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.GRIMBERRY.get()) {
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
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.OFF_HAND, net.minecraft.world.item.component.SwingAnimation.DEFAULT, true);
			}
			world.setBlock(BlockPos.containing(x, y + 1, z), TheDeepVoidModBlocks.DESOLATE_LICHEN.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.crop.plant")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("item.crop.plant")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
		}
	}
}
