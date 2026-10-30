package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class CallUponTheHiveRightclickedOnBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if ((world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, Identifier.parse("the_deep_void:bone_block")))) {
			if (entity.isShiftKeyDown()) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack, 10);
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.hiveCallX = x;
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.hiveCallY = y;
					_vars.syncPlayerVariables(entity);
				}
				{
					TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
					_vars.hiveCallZ = z;
					_vars.syncPlayerVariables(entity);
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.the_deep_void.call_upon_the_hive.set_destination").getString())), false);
			}
		}
	}
}
