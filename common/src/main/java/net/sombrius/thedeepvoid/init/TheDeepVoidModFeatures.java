
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;
import net.sombrius.thedeepvoid.port.platform.Registrar;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.world.features.CrumblingBedrockGenerateFeature;
import net.sombrius.thedeepvoid.world.features.FleshBlockGenerateNormalFeature;
import net.sombrius.thedeepvoid.world.features.FleshMouthFeature;
import net.sombrius.thedeepvoid.world.features.FleshyFormationUndergroundFeature;
import net.sombrius.thedeepvoid.world.features.HuntersCabinFeature;
import net.sombrius.thedeepvoid.world.features.RottenMossGenerateFeature;
import net.sombrius.thedeepvoid.world.features.RottenMossGenerateNormalFeature;

public class TheDeepVoidModFeatures {
	public static final Registrar<MapCodec<? extends Feature>> REGISTRY = Registrar.create(Registries.FEATURE_TYPE, TheDeepVoidMod.MODID);
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<RottenMossGenerateFeature>> ROTTEN_MOSS_GENERATE = REGISTRY.register("rotten_moss_generate", () -> RottenMossGenerateFeature.CODEC);
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<FleshMouthFeature>> FLESH_MOUTH = REGISTRY.register("flesh_mouth", () -> FleshMouthFeature.CODEC);
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<FleshyFormationUndergroundFeature>> FLESHY_FORMATION_UNDERGROUND = REGISTRY.register("fleshy_formation_underground", () -> FleshyFormationUndergroundFeature.CODEC);
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<RottenMossGenerateNormalFeature>> ROTTEN_MOSS_GENERATE_NORMAL = REGISTRY.register("rotten_moss_generate_normal", () -> RottenMossGenerateNormalFeature.CODEC);
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<FleshBlockGenerateNormalFeature>> FLESH_BLOCK_GENERATE_NORMAL = REGISTRY.register("flesh_block_generate_normal", () -> FleshBlockGenerateNormalFeature.CODEC);
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<CrumblingBedrockGenerateFeature>> CRUMBLING_BEDROCK_GENERATE = REGISTRY.register("crumbling_bedrock_generate", () -> CrumblingBedrockGenerateFeature.CODEC);
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<HuntersCabinFeature>> HUNTERS_CABIN = REGISTRY.register("hunters_cabin", () -> HuntersCabinFeature.CODEC);
}
