
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.world.features.CrumblingBedrockGenerateFeature;
import net.sombrius.thedeepvoid.world.features.FleshBlockGenerateNormalFeature;
import net.sombrius.thedeepvoid.world.features.FleshMouthFeature;
import net.sombrius.thedeepvoid.world.features.FleshyFormationUndergroundFeature;
import net.sombrius.thedeepvoid.world.features.HuntersCabinFeature;
import net.sombrius.thedeepvoid.world.features.RottenMossGenerateFeature;
import net.sombrius.thedeepvoid.world.features.RottenMossGenerateNormalFeature;

public class TheDeepVoidModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(Registries.FEATURE, TheDeepVoidMod.MODID);
	public static final DeferredHolder<Feature<?>, Feature<?>> ROTTEN_MOSS_GENERATE = REGISTRY.register("rotten_moss_generate", RottenMossGenerateFeature::new);
	public static final DeferredHolder<Feature<?>, Feature<?>> FLESH_MOUTH = REGISTRY.register("flesh_mouth", FleshMouthFeature::new);
	public static final DeferredHolder<Feature<?>, Feature<?>> FLESHY_FORMATION_UNDERGROUND = REGISTRY.register("fleshy_formation_underground", FleshyFormationUndergroundFeature::new);
	public static final DeferredHolder<Feature<?>, Feature<?>> ROTTEN_MOSS_GENERATE_NORMAL = REGISTRY.register("rotten_moss_generate_normal", RottenMossGenerateNormalFeature::new);
	public static final DeferredHolder<Feature<?>, Feature<?>> FLESH_BLOCK_GENERATE_NORMAL = REGISTRY.register("flesh_block_generate_normal", FleshBlockGenerateNormalFeature::new);
	public static final DeferredHolder<Feature<?>, Feature<?>> CRUMBLING_BEDROCK_GENERATE = REGISTRY.register("crumbling_bedrock_generate", CrumblingBedrockGenerateFeature::new);
	public static final DeferredHolder<Feature<?>, Feature<?>> HUNTERS_CABIN = REGISTRY.register("hunters_cabin", HuntersCabinFeature::new);
}
