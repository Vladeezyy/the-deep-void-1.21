package net.sombrius.thedeepvoid.procedures;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PrimordialStoneEntityWalksOnTheBlockProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player || entity instanceof ServerPlayer) {
			if (!(entity instanceof ServerPlayer _plr2 && _plr2.level() instanceof ServerLevel && _plr2.getAdvancements().getOrStartProgress(_plr2.server.getAdvancements().get(ResourceLocation.parse("the_deep_void:weve_hit_rock_bottom"))).isDone())) {
				if (entity instanceof ServerPlayer _player) {
					AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("the_deep_void:weve_hit_rock_bottom"));
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
