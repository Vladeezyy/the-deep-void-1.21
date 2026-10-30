/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

import net.sombrius.thedeepvoid.port.platform.Registrar;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

/** 1.21.11: game rules are a registry (the_deep_void:spawn_hole_structure, the_deep_void:give_wiki_book) */
public class TheDeepVoidModGameRules {
	public static final Registrar<GameRule<?>> REGISTRY = Registrar.create(Registries.GAME_RULE, TheDeepVoidMod.MODID);
	public static final RegistryEntry<GameRule<?>, GameRule<Boolean>> SPAWN_HOLE_STRUCTURE = REGISTRY.register("spawn_hole_structure", () -> bool(GameRuleCategory.MISC, true));
	public static final RegistryEntry<GameRule<?>, GameRule<Boolean>> GIVE_WIKI_BOOK = REGISTRY.register("give_wiki_book", () -> bool(GameRuleCategory.PLAYER, true));

	private static GameRule<Boolean> bool(GameRuleCategory category, boolean defaultValue) {
		return new GameRule<>(category, GameRuleType.BOOL, BoolArgumentType.bool(), GameRuleTypeVisitor::visitBoolean, Codec.BOOL, value -> value ? 1 : 0, defaultValue, FeatureFlagSet.of());
	}
}
