package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.PlayerInteractEvent;
import net.sombrius.thedeepvoid.entity.BuiltBigOverseerEntity;
import net.sombrius.thedeepvoid.entity.BuiltOverseerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class OverseerSummonProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.SEER.get() && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == TheDeepVoidModBlocks.MONOLITHIC_STONE.get()
				&& !((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == TheDeepVoidModBlocks.MONOLITHIC_STONE.get())) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.FORGOTTEN_TOKEN.get()) {
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
				world.destroyBlock(BlockPos.containing(x, y, z), false);
				world.destroyBlock(BlockPos.containing(x, y - 1, z), false);
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = TheDeepVoidModEntities.BUILT_OVERSEER.get().spawn(_level, BlockPos.containing(x, y - 1, z), EntitySpawnReason.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				if (!world.getEntitiesOfClass(BuiltOverseerEntity.class, AABB.ofSize(new Vec3(x, (y - 1), z), 2, 2, 2), e -> true).isEmpty()) {
					if (((Entity) world.getEntitiesOfClass(BuiltOverseerEntity.class, AABB.ofSize(new Vec3(x, (y - 1), z), 2, 2, 2), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, (y - 1), z)).findFirst().orElse(null)) instanceof TamableAnimal _toTame && entity instanceof Player _owner)
						_toTame.tame(_owner);
				}
				if ((entity instanceof ServerPlayer _plr17 && _plr17.level() instanceof ServerLevel
						&& _plr17.getAdvancements().getOrStartProgress(_plr17.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:its_alive"))).isDone()) == false) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:its_alive"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
		}
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.SEER.get() && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == TheDeepVoidModBlocks.MONOLITHIC_STONE.get()
				&& (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == TheDeepVoidModBlocks.MONOLITHIC_STONE.get()) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == TheDeepVoidModItems.FORGOTTEN_TOKEN.get()) {
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
				world.destroyBlock(BlockPos.containing(x, y, z), false);
				world.destroyBlock(BlockPos.containing(x, y - 1, z), false);
				world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = TheDeepVoidModEntities.BUILT_BIG_OVERSEER.get().spawn(_level, BlockPos.containing(x, y - 1, z), EntitySpawnReason.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				if (!world.getEntitiesOfClass(BuiltBigOverseerEntity.class, AABB.ofSize(new Vec3(x, (y - 1), z), 2, 2, 2), e -> true).isEmpty()) {
					if (((Entity) world.getEntitiesOfClass(BuiltBigOverseerEntity.class, AABB.ofSize(new Vec3(x, (y - 1), z), 2, 2, 2), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, (y - 1), z)).findFirst().orElse(null)) instanceof TamableAnimal _toTame && entity instanceof Player _owner)
						_toTame.tame(_owner);
				}
				if ((entity instanceof ServerPlayer _plr37 && _plr37.level() instanceof ServerLevel
						&& _plr37.getAdvancements().getOrStartProgress(_plr37.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:its_alive"))).isDone()) == false) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.level().getServer().getAdvancements().get(Identifier.parse("the_deep_void:its_alive"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
		}
	}
}
