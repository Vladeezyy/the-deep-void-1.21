package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class UndertakerRandomBiomeDialogueProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double dialogue = 0;
		double interrupt = 0;
		if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:mourning_graveyard"))) {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 4);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.graveyard1").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.graveyard2").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.graveyard3").getString())));
			} else if (dialogue == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.graveyard4").getString())));
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:filled_graveyard")) || world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:forgotten_valley"))) {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 3);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.graveyard2").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.graveyard3").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.graveyard4").getString())));
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:drifting_monoliths"))) {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 4);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.driftingmonoliths1").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.driftingmonoliths2").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.driftingmonoliths3").getString())));
			} else if (dialogue == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.driftingmonoliths4").getString())));
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:deep_marrows"))) {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 4);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.marrows1").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.marrows2").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.marrows3").getString())));
			} else if (dialogue == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.marrows4").getString())));
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:grim_canopy")) || world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:watching_undergrowth"))) {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 4);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.canopy1").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.canopy2").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.canopy3").getString())));
			} else if (dialogue == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.canopy4").getString())));
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:windswept_hollow"))) {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 3);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.windswept1").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.windswept2").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.windswept3").getString())));
			}
		} else if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:gaol_of_heretics"))) {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 3);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.gaol1").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.gaol2").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.gaol3").getString())));
			}
		} else {
			dialogue = Mth.nextInt(RandomSource.create(), 1, 4);
			if (dialogue == 1) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.generic1").getString())));
			} else if (dialogue == 2) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.generic2").getString())));
			} else if (dialogue == 3) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.generic3").getString())));
			} else if (dialogue == 4) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.generic4").getString())));
			}
		}
		interrupt = Mth.nextInt(RandomSource.create(), 1, 4);
		if (interrupt == 1) {
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.interrupt1").getString())));
		} else if (interrupt == 2) {
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.interrupt2").getString())));
		} else if (interrupt == 3) {
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.interrupt3").getString())));
		} else if (interrupt == 4) {
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.sendSystemMessage(Component.literal(("<" + Component.translatable("entity.the_deep_void.undertaker").getString() + "> " + Component.translatable("entity.the_deep_void.undertaker.interrupt4").getString())));
		}
	}
}
