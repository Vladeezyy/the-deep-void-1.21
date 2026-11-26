#!/usr/bin/env python3
"""Thaumaturge compat data (NeoForge only): python -I tools/gen_thaumaturge.py

Writes the built-in data pack neoforge/src/main/resources/data/the_deep_void/datapacks/thaumaturge, which
compat/ThaumaturgeCompat registers only when Thaumaturge is loaded (without it NeoForge would warn about data map
files of a data map type that does not exist):
  data/the_deep_void/thaumaturge/aspect/<aspect>.json        the mod's own aspects (ASPECTS)
  data/thaumaturge/data_maps/item/base_aspects.json          aspects of the items that no plain recipe makes
  data/thaumaturge/data_maps/entity_type/entity_aspects.json what the thaumometer reads from the mobs
Items crafted, smelted, stonecut or smithed from these get their aspects from the recipe (Thaumaturge derives them).
Item ids are checked against the en_us language file, mobs against TheDeepVoidModEntities, so a renamed one fails
here and not in game. Aspect icons: tools/gen_aspect_icons.py. Self-test: tools/thaumtest.sh.
"""
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
PACK = ROOT / "neoforge/src/main/resources/data/the_deep_void/datapacks/thaumaturge"
LANG = ROOT / "common/src/main/resources/assets/the_deep_void/lang/en_us.json"
ENTITIES_JAVA = ROOT / "common/src/main/java/net/sombrius/thedeepvoid/init/TheDeepVoidModEntities.java"
# data pack format of the branch's Minecraft version (26.1.2: data 101.1)
PACK_FORMAT = {"min_format": 101, "max_format": 101}
NS = "the_deep_void"

# The mod's own aspects: two components each (Thaumaturge's compounds), none the same pair as one of Thaumaturge's
ASPECTS = {
    "abyssus": {"color": 0x4B1F8A, "components": ["thaumaturge:alienis", "thaumaturge:vacuos"]},  # the Abyss, the Deep Void
    "caro": {"color": 0xA8323E, "components": ["thaumaturge:bestia", "thaumaturge:mortuus"]},  # flesh
    "os": {"color": 0xE6DCC0, "components": ["thaumaturge:mortuus", "thaumaturge:terra"]},  # bone
    "oculus": {"color": 0xC8B432, "components": ["thaumaturge:sensus", "thaumaturge:alienis"]},  # the watching eye
}

THAUMATURGE_ASPECTS = {
    "aer", "alienis", "alkimia", "aqua", "auram", "aversio", "bestia", "cognitio", "desiderium", "exanimis", "fabrico",
    "gelum", "herba", "humanus", "ignis", "instrumentum", "lux", "machina", "metallum", "mortuus", "motus", "ordo",
    "perditio", "permutatio", "potentia", "praecantatio", "praemunio", "sensus", "spiritus", "tenebrae", "terra",
    "vacuos", "victus", "vinculum", "vitium", "vitreus", "volatus",
}

# item -> aspects (short names: Thaumaturge's or the mod's own). Only what no plain recipe makes: ores, drops, plants,
# loot and structure blocks, the mod's custom recipe types; the rest gets its aspects from its recipes.
ITEMS = {
    # the void
    "void_matter": "abyssus 10, vacuos 5, alienis 3",
    "voidrium_ore": "abyssus 10, terra 5, vitreus 5",
    "solid_void": "abyssus 15, vacuos 10, terra 5",
    "cracked_bedrock": "terra 10, vacuos 10, perditio 10, abyssus 5",
    "voidlight": "lux 10, abyssus 5",
    "void_lens": "vitreus 5, sensus 5, abyssus 3",
    "void_filled_sponge": "terra 5, vinculum 5, abyssus 5",
    "void_roots": "herba 3, abyssus 2",
    "void_rose": "herba 3, abyssus 3, sensus 2",
    "void_fern": "herba 3, abyssus 2",
    "void_stem": "herba 5, abyssus 3",
    "ash_pile": "perditio 3, ignis 2, abyssus 1",
    "voided_heart": "abyssus 10, caro 5, victus 5",
    "rune_of_void": "abyssus 10, praecantatio 10, terra 3",
    "return_portal": "abyssus 10, motus 10, praecantatio 10",
    "void_fly": "bestia 3, volatus 3, abyssus 2",
    # bone
    "pile_of_bones": "os 5, mortuus 3",
    "bone_marrow": "os 3, victus 3, herba 1",
    "canopy_bone_marrow": "os 3, victus 3, herba 1",
    "hanging_marrow": "os 3, victus 2",
    "bone_ball": "os 3, aversio 2",
    "teeth": "os 3, aversio 3, bestia 1",
    "teeth_trap_block": "os 5, aversio 5",
    "trap_bone_spikes": "os 5, aversio 5",
    "ground_bones_plant": "os 3, herba 2",
    "covered_skull_pile": "os 10, mortuus 5, terra 2",
    "covered_bone_pile": "os 8, mortuus 3, terra 2",
    "bone_crawler_chitin": "os 5, praemunio 5, bestia 2",
    "bone_crawler_molt_piece": "os 3, praemunio 3",
    "flesh_bone": "os 5, caro 5",
    "twisting_spine": "os 8, mortuus 3, motus 2",
    "worm_tooth": "os 3, bestia 3, aversio 2",
    "block_of_shattered_bone": "os 15, perditio 5",
    "block_of_old_shattered_bone": "os 15, perditio 5, mortuus 2",
    "block_of_rotten_shattered_bone": "os 15, perditio 5, vitium 2",
    "rotten_fossil": "os 8, terra 5, mortuus 3",
    "bone_crawler_egg": "os 3, victus 5, bestia 3",
    "bone_crawler_egg_generate": "os 3, victus 5, bestia 3",
    "infested_bone_crawler_egg": "os 3, victus 3, vitium 3, bestia 3",
    "infested_bone_crawler_egg_generate": "os 3, victus 3, vitium 3, bestia 3",
    "bone_crawler_embryo": "bestia 5, mortuus 5, os 2",
    # flesh
    "flesh_block": "caro 15, victus 5",
    "active_flesh_block": "caro 15, victus 5",
    "veiny_flesh": "caro 10, victus 5",
    "pustulent_flesh_bricks": "caro 10, vitium 3",
    "fossilized_flesh": "caro 5, terra 5, mortuus 3",
    "mourner_hide": "caro 3, mortuus 3, praemunio 3",
    "hunter_hide": "praemunio 5, caro 3, bestia 3",
    "bloody_tongue": "caro 5, sensus 3",
    "pus_ball": "vitium 3, caro 2, aqua 2",
    "regurgitated_matter": "caro 3, perditio 3, bestia 2",
    "golden_regurgitated_matter": "desiderium 5, caro 3, metallum 3",
    "devourer_tendril": "caro 5, bestia 3, vinculum 3",
    "ambrosial_flesh_chunk": "caro 5, victus 5, praecantatio 3",
    "flesh_parasite": "caro 3, bestia 3, vitium 3",
    "misted_heart": "caro 10, victus 10, alienis 10, abyssus 5",
    "misted_remains": "caro 5, mortuus 5, aer 3",
    "corpse_blossom": "caro 3, herba 3, mortuus 3",
    "dead_man_hand": "mortuus 10, caro 5, humanus 5",
    "opened_dead_man_hand": "mortuus 10, caro 5, humanus 5",
    "rotting_corpse_in_a_cage": "caro 10, mortuus 10, metallum 5, vinculum 3",
    # the eye
    "eye_vine_stem": "herba 5, oculus 2",
    "huge_eye_vine_stem": "herba 5, oculus 2",
    "eye_of_the_hive_watcher": "oculus 20, sensus 10, praecantatio 10, bestia 5",
    "creep_o_meter": "sensus 10, machina 5, oculus 3",
    # fish, insects, feathers
    "voidfish": "aqua 5, bestia 3, abyssus 3",
    "rotfish": "aqua 5, bestia 3, perditio 3",
    "bloodfin": "aqua 5, bestia 3, caro 3",
    "pusfish": "aqua 5, bestia 3, vitium 3",
    "bloatfish": "aqua 5, bestia 3, aer 3",
    "eyefish": "aqua 5, bestia 3, oculus 3",
    "ambrosiafish": "aqua 5, bestia 3, praecantatio 3",
    "needlefish": "aqua 5, bestia 3, aversio 3",
    "knightfish": "aqua 5, bestia 3, praemunio 3",
    "lamprey": "aqua 5, bestia 3, caro 3",
    "void_fly_maggot": "bestia 3, victus 3, perditio 2",
    "fly_wing_piece": "volatus 5, bestia 2",
    "death_vulture_feather": "volatus 5, aer 3, mortuus 2",
    "elytra_piece": "volatus 5, alienis 2",
    "fishy_potion": "alkimia 5, aqua 5, bestia 3",
    # plants and soil
    "rotten_grass": "terra 5, herba 2, perditio 2",
    "canopy_rotten_grass": "herba 3, perditio 2",
    "cave_growth": "herba 3, tenebrae 2",
    "desolate_soil": "terra 5, tenebrae 2",
    "desolate_soil_active": "terra 5, tenebrae 2",
    "desolate_growth": "herba 3, tenebrae 2",
    "desolate_stem": "herba 5, tenebrae 2",
    "desolate_lichen": "herba 2, tenebrae 2",
    "blossoming_desolate_lichen": "herba 3, tenebrae 2, lux 1",
    "overgrown_gloom_growth": "herba 5, tenebrae 3",
    "living_gloom_stem": "herba 5, tenebrae 2, victus 2",
    "grimberry": "herba 3, victus 3, tenebrae 2",
    "grim_soil": "terra 5, tenebrae 3, mortuus 1",
    "disturbing_root": "herba 3, alienis 2",
    "inactive_peeping_roots": "herba 3, oculus 2",
    "inactive_infested_rotten_roots": "herba 3, vitium 2",
    "stripped_rotten_log": "herba 5, perditio 2",
    "mandrake": "herba 5, victus 3, sensus 3",
    "mandrake_roots": "herba 3, victus 2",
    "goo_ball": "aqua 3, vinculum 3, alienis 1",
    "goo_cover": "aqua 2, vinculum 2",
    # stone, ores and minerals
    "cobbled_ancient_deepslate": "terra 5, tenebrae 2, perditio 1",
    "primordial_stone": "terra 5, tenebrae 3, alienis 2",
    "onyx_ore": "terra 5, vitreus 5, tenebrae 5",
    "lavendite_ore": "terra 5, vitreus 5, praecantatio 3",
    "lavendite_shard": "vitreus 3, praecantatio 2",
    "bismuth_ore": "metallum 10, terra 5, permutatio 3",
    "sulfur_ore": "terra 5, ignis 5, perditio 3",
    "cinnabar_ore": "terra 5, alkimia 5, metallum 3",
    "bloodstone_ore": "terra 5, vitreus 5, victus 3",
    "flesh_bloodstone_ore": "caro 5, vitreus 5, victus 3",
    "dark_steel_ore": "metallum 15, terra 5, tenebrae 3",
    "deep_void_iron_ore": "metallum 15, terra 5, tenebrae 2",
    "deep_void_copper_ore": "metallum 10, terra 5, tenebrae 2",
    "deep_void_gold_ore": "metallum 10, desiderium 10, terra 5, tenebrae 2",
    "deep_void_lapis_lazuli_ore": "sensus 15, terra 5, tenebrae 2",
    "deep_void_redstone_ore": "potentia 15, terra 5, tenebrae 2",
    "deep_void_diamond_ore": "vitreus 15, desiderium 15, terra 5, tenebrae 2",
    "sacred_cinnabar": "alkimia 10, praecantatio 10, vitium 5",
    "grim_shard": "vitreus 5, tenebrae 5, mortuus 3",
    "crystallized_ichor": "vitreus 5, praecantatio 5, victus 5",
    "liquid_ichor_bucket": "metallum 8, praecantatio 5, victus 5, aqua 3",
    "bottle_of_ambrosia": "victus 10, praecantatio 5, vitreus 1",
    "empty_solid_ambrosia": "vitreus 5, vacuos 5",
    "blue_fire": "ignis 5, spiritus 3",
    # souls
    "soul_shard": "spiritus 10, vitreus 3",
    "soul_container": "spiritus 15, vinculum 5, vitreus 5",
    "soul_orb_pillar": "spiritus 10, terra 5",
    "soul_forge": "ignis 10, spiritus 10, fabrico 5, terra 5",
    # loot and structure blocks
    "deepslate_sarcophagus": "terra 10, mortuus 10, vacuos 5",
    "ancient_pot": "terra 5, vacuos 5",
    "ancient_urns_generate": "terra 5, vacuos 5",
    "rotten_crate": "herba 5, vacuos 3",
    "golden_rotten_crate": "herba 5, desiderium 5, vacuos 3",
    "flesh_crate": "caro 5, vacuos 3",
    "golden_flesh_crate": "caro 5, desiderium 5, vacuos 3",
    "cinnabar_crate": "alkimia 3, metallum 3, vacuos 3",
    "miniature_coffer": "desiderium 5, vacuos 5, metallum 3",
    "incense_burner": "ignis 5, aer 5, sensus 3",
    "red_gate": "metallum 10, vinculum 10",
    "red_gate_non_solid": "metallum 10, vinculum 10",
    "dungeon_dark_steel_bars": "metallum 5, vinculum 5, tenebrae 2",
    "dungeon_dark_steel_bars_active": "metallum 5, vinculum 5, tenebrae 2",
    "trap_dark_steel_spikes": "metallum 5, aversio 5",
    "flail_executioner_spawner": "metallum 10, praecantatio 5, mortuus 5, vinculum 5",
    "flail_executioner_spawner_active": "metallum 10, praecantatio 5, mortuus 5, vinculum 5",
    "fake_torch": "lux 3, alienis 2",
    "big_smoke_vent_corner": "aer 5, ignis 3, terra 3",
    "short_circuit_rooter": "potentia 5, herba 3, perditio 2",
    "powered_lava_geyser_on": "ignis 10, potentia 5, terra 5",
    # corpses
    "corpse_spawn": "caro 10, mortuus 10, humanus 5",
    "corpse_spawn_graveyard": "caro 10, mortuus 10, humanus 5",
    "covered_corpse_spawn": "caro 10, mortuus 10, humanus 5",
    "laying_corpse_spawn": "caro 10, mortuus 10, humanus 5",
    "rotting_corpse_spawn": "caro 10, mortuus 10, humanus 5, perditio 3",
    "rotting_covered_corpse_spawn": "caro 10, mortuus 10, humanus 5, perditio 3",
    "rotting_laying_corpse_spawn": "caro 10, mortuus 10, humanus 5, perditio 3",
    # boss drops
    "weaver_of_souls_treasure_bag": "desiderium 15, praecantatio 10, spiritus 10",
    "apostle_of_catastrophe_treasure_bag": "desiderium 15, praecantatio 10, alienis 10",
    "misanthropic_hivemind_treasure_bag": "desiderium 15, praecantatio 10, caro 10",
    "primordial_crawler_treasure_bag": "desiderium 15, praecantatio 10, os 10",
    "hive_watcher_treasure_bag": "desiderium 15, praecantatio 10, oculus 10",
    "false_hydra_treasure_bag": "desiderium 15, praecantatio 10, caro 10",
    "head_of_the_hivemind": "caro 10, mortuus 10, spiritus 10, cognitio 5",
    "head_of_the_primordial_crawler": "os 15, mortuus 10, bestia 5",
    "head_of_the_apostle": "mortuus 10, spiritus 10, alienis 10",
    "head_of_the_weaver": "spiritus 15, mortuus 10, praecantatio 5",
    "head_of_the_false_hydra": "caro 10, mortuus 10, oculus 5",
    # tools, weapons and other finds
    "blood_bow": "aversio 10, caro 5, volatus 3",
    "ceremonial_dagger": "aversio 5, metallum 5, mortuus 5, praecantatio 3",
    "death_whistle": "mortuus 10, sensus 5, aer 5",
    "debilitator": "aversio 10, machina 5, vitium 5",
    "entity_immobilizer": "vinculum 15, machina 5, praecantatio 5",
    "entity_turner": "motus 10, machina 5, praecantatio 5",
    "ancient_gun_part": "machina 5, metallum 5, aversio 3",
    "simple_gun_part": "machina 3, metallum 3",
    "cursed_pearl": "alienis 10, motus 10, vitium 5",
    "blessed_pearl": "alienis 10, motus 10, praecantatio 5",
    "hammer_and_chisel": "instrumentum 5, metallum 5",
    "chisel": "instrumentum 5, metallum 3",
    "dark_steel_coin": "metallum 3, desiderium 3, tenebrae 1",
    "coin_bag": "desiderium 10, metallum 5, fabrico 2",
    "book_of_ancient_knowledge": "cognitio 20, praecantatio 5",
    "greater_experience_bottle": "cognitio 40",
    "experience_orbs": "cognitio 10",
    "tattered_cult_hood": "fabrico 3, praemunio 3, alienis 2",
    "tattered_cult_tunic": "fabrico 5, praemunio 5, alienis 2",
    "tattered_cult_leggings": "fabrico 4, praemunio 4, alienis 2",
    "tattered_cult_boots": "fabrico 2, praemunio 2, alienis 2",
    # crafted only from each other (block <-> item), so Thaumaturge finds no source in the recipes
    "rotten_bone": "os 3, mortuus 3, perditio 2",
    "misted_bone": "os 3, mortuus 2, aer 2",
    "sulfur": "ignis 5, perditio 3, alkimia 2",
    "black_moss": "herba 3, tenebrae 3",
    "ancient_onyx": "vitreus 5, tenebrae 5, terra 2",
    "soul_fused_ingot": "metallum 10, spiritus 10, praecantatio 3",
    "hanged_lurker_head": "mortuus 10, abyssus 5, tenebrae 5, bestia 5",
    "lurker_head": "mortuus 10, abyssus 5, tenebrae 5, bestia 5",
    "chant_of_the_forgotten_distorted": "sensus 15, desiderium 10, alienis 5, tenebrae 5",
    # recipes that make many: the share of each is below 1 and rounds down to nothing
    "dark_steel_nugget": "metallum 2, tenebrae 1",
    "dark_steel_bars": "metallum 3, tenebrae 1",
    "dark_steel_chain": "metallum 3, vinculum 2",
    "dark_steel_kunai": "aversio 3, metallum 2",
    "candelabra_foot": "metallum 3, vitreus 2",
    "framed_sulfur_glass_pane": "vitreus 2, ignis 1",
    "framed_bismuth_glass_pane": "vitreus 2, metallum 1",
    "framed_cinnabar_glass_pane": "vitreus 2, alkimia 1",
    "peeping_roots": "herba 3, oculus 2",
    # smithing with an input Thaumaturge does not take
    "lavendite_shield": "praemunio 15, vitreus 10, praecantatio 5",
    "grim_licker_hook": "vinculum 10, tenebrae 5, mortuus 5, motus 5",
    # what the recipes give is too little of the mod or piles up (a block of 9 gems of many ingredients: 468 Vitreus)
    "voidrium_gem": "abyssus 15, vitreus 10, praecantatio 5",
    "voidrium_alloy": "abyssus 15, metallum 10, praecantatio 5",
    "void_core": "abyssus 20, praecantatio 10, vitreus 5",
    "refined_onyx": "vitreus 10, tenebrae 8, ordo 2",
    "refined_bismuth": "metallum 10, permutatio 5, vitreus 3",
    "refined_cinnabar": "alkimia 10, metallum 5, ignis 3",
    "hard_flesh_ingot": "caro 10, praemunio 5, metallum 3",
    "grim_gem": "vitreus 10, tenebrae 10, mortuus 5",
    "rotten_bone_block": "os 12, mortuus 10, perditio 5",
    # stonecut from the Rotten Bone Block, but Thaumaturge leaves them empty
    "cut_rotten_bone_block": "os 10, mortuus 8, perditio 4",
    "cut_rotten_bone_block_stairs": "os 10, mortuus 8, perditio 4",
    "cut_rotten_bone_block_slab": "os 5, mortuus 4, perditio 2",
    "chiseled_cut_rotten_bone_block": "os 10, mortuus 8, perditio 4",
    "block_of_bone": "os 15, mortuus 5",
    "eyeball": "oculus 5, caro 3, sensus 3",
    "flesh_chunk": "caro 5, victus 2",
    "pus_flower": "herba 3, vitium 3, caro 2",
}

# Gear from the smithing table: Thaumaturge gives it only its combat aspect from the stats (a netherite sword: Aversio
# 20), so the mod's armor and tools looked all the same. Keep that aspect (the amounts Thaumaturge gives) and add the
# material's: smithing addition -> (its aspects, {item: Thaumaturge's aspect})
SMITHED = {
    "refined_bismuth": ("metallum 8, permutatio 4", {
        "bismuth_netherite_axe": "instrumentum 16", "bismuth_netherite_boots": "praemunio 16",
        "bismuth_netherite_chestplate": "praemunio 32", "bismuth_netherite_helmet": "praemunio 16",
        "bismuth_netherite_hoe": "instrumentum 16", "bismuth_netherite_leggings": "praemunio 32",
        "bismuth_netherite_pickaxe": "instrumentum 16", "bismuth_netherite_shovel": "instrumentum 16",
        "bismuth_netherite_sword": "aversio 24"}),
    "dark_steel_scrap": ("metallum 8, tenebrae 4", {
        "dark_steel_axe": "instrumentum 16", "dark_steel_bismuth_netherite_boots": "praemunio 16",
        "dark_steel_bismuth_netherite_chestplate": "praemunio 32", "dark_steel_bismuth_netherite_helmet": "praemunio 16",
        "dark_steel_bismuth_netherite_leggings": "praemunio 32", "dark_steel_hoe": "instrumentum 16",
        "dark_steel_naginata": "aversio 28", "dark_steel_onyx_boots": "praemunio 16",
        "dark_steel_onyx_chestplate": "praemunio 32", "dark_steel_onyx_helmet": "praemunio 16",
        "dark_steel_onyx_leggings": "praemunio 28", "dark_steel_pickaxe": "instrumentum 16",
        "dark_steel_shovel": "instrumentum 16", "dark_steel_sword": "aversio 28"}),
    "dark_steel_chainmail": ("metallum 6, tenebrae 3", {
        "dark_steel_chainmail_armor_boots": "praemunio 12", "dark_steel_chainmail_armor_chestplate": "praemunio 28",
        "dark_steel_chainmail_armor_helmet": "praemunio 12", "dark_steel_chainmail_armor_leggings": "praemunio 24"}),
    "grim_gem": ("tenebrae 6, mortuus 4", {
        "grim_rotten_bone_boots": "praemunio 16", "grim_rotten_bone_chestplate": "praemunio 24",
        "grim_rotten_bone_helmet": "praemunio 16", "grim_rotten_bone_leggings": "praemunio 24",
        "grim_crawler_boots": "praemunio 16", "grim_crawler_chestplate": "praemunio 28",
        "grim_crawler_helmet": "praemunio 16", "grim_crawler_leggings": "praemunio 28",
        "grim_lavendite_boots": "praemunio 16", "grim_lavendite_chestplate": "praemunio 28",
        "grim_lavendite_helmet": "praemunio 16", "grim_lavendite_leggings": "praemunio 28",
        "grim_sickle": "aversio 4"}),
    "hard_flesh_ingot": ("caro 8, victus 3", {
        "flesh_armor_boots": "praemunio 16", "flesh_armor_chestplate": "praemunio 20",
        "flesh_armor_helmet": "praemunio 16", "flesh_armor_leggings": "praemunio 20"}),
    "block_of_lavendite": ("vitreus 8, praecantatio 4", {
        "lavendite_armor_boots": "praemunio 12", "lavendite_armor_chestplate": "praemunio 20",
        "lavendite_armor_helmet": "praemunio 12", "lavendite_armor_leggings": "praemunio 20"}),
    "refined_onyx": ("vitreus 6, tenebrae 6", {
        "onyx_axe": "instrumentum 16", "onyx_armor_boots": "praemunio 16", "onyx_armor_chestplate": "praemunio 32",
        "onyx_armor_helmet": "praemunio 16", "onyx_hoe": "instrumentum 16", "onyx_armor_leggings": "praemunio 28",
        "onyx_pickaxe": "instrumentum 16", "onyx_scythe": "aversio 16", "onyx_shovel": "instrumentum 16",
        "onyx_sword": "aversio 16"}),
    "soul_fused_ingot": ("spiritus 8, abyssus 6, praecantatio 4", {
        "sacred_voidrium_boots": "praemunio 24", "sacred_voidrium_chestplate": "praemunio 36",
        "sacred_voidrium_helmet": "praemunio 24", "sacred_voidrium_leggings": "praemunio 28"}),
    "void_matter": ("abyssus 8, vacuos 4", {"void_sickle": "aversio 12"}),
    "voidrium_alloy": ("abyssus 10, metallum 5", {
        "voidrium_axe": "instrumentum 16", "voidrium_boots": "praemunio 20", "voidrium_chestplate": "praemunio 32",
        "voidrium_helmet": "praemunio 20", "voidrium_hoe": "instrumentum 16", "voidrium_leggings": "praemunio 28",
        "voidrium_pickaxe": "instrumentum 16", "voidrium_shovel": "instrumentum 16", "voidrium_sword": "aversio 20"}),
}
for material, (flavor, gear) in SMITHED.items():
    for item, combat in gear.items():
        ITEMS[item] = f"{combat}, {flavor}"
# eye vines and big eyes in every color
for color in ("", "green_", "red_", "yellow_", "brown_", "purple_"):
    ITEMS[f"{color}eye_vine"] = "herba 3, oculus 3, sensus 2"
for color in ("blue", "green", "red", "yellow", "brown", "purple"):
    ITEMS[f"big_{color}_eye"] = "oculus 10, sensus 5, caro 3"
# music discs (as the vanilla ones, darker)
for disc in ("lurking_music_disc", "chasms_music_disc", "darkmare", "chainsaw", "pitch_black", "the_haunt", "monsters_lair", "slaughter"):
    ITEMS[disc] = "sensus 15, desiderium 10, aer 5, tenebrae 5"
# needles: a dose of an effect
NEEDLES = {
    "regeneration": "victus 5", "absorption": "praemunio 5", "resistance": "praemunio 5", "glowing": "lux 5",
    "rot": "perditio 5, vitium 3", "jump_boost": "motus 5", "hexed": "vitium 5, alienis 3",
    "fire_resistance": "ignis 5, praemunio 3", "poison": "mortuus 5", "speed": "motus 5", "health_boost": "victus 5",
    "wither": "mortuus 5, perditio 3", "saturation": "victus 5", "instant_damage": "mortuus 5, aversio 3",
    "strength": "aversio 5", "weakness": "perditio 5", "slowness": "vinculum 5", "invisibility": "sensus 5, vacuos 3",
}
for effect, extra in NEEDLES.items():
    ITEMS[f"{effect}_needle"] = f"alkimia 5, {extra}, aversio 2"

# mob -> aspects (what the thaumometer reads): every mob with a spawn egg and a few without
ENTITIES = {
    # the void
    "lurker": "abyssus 10, tenebrae 10, bestia 5",
    "abductor": "abyssus 10, alienis 10, motus 5",
    "devourer": "abyssus 15, bestia 10, vacuos 10",
    "summoned_devourer": "abyssus 15, bestia 10, vacuos 10",
    "seeker": "abyssus 15, vacuos 10, motus 5",
    "void_dweller": "abyssus 15, oculus 10, alienis 5",
    "void_tentacle": "abyssus 10, vinculum 5",
    "voidborn": "abyssus 20, alienis 10, bestia 10",
    "voidborn_tentacles": "abyssus 10, vinculum 5",
    "black_fly": "volatus 5, bestia 3, abyssus 3",
    "harbinger_of_cataclysm": "abyssus 20, alienis 15, praecantatio 15, perditio 10",
    "harbinger_clone": "abyssus 5, alienis 5, praecantatio 5",
    # bone
    "bone_crawler": "os 10, bestia 5, aversio 3",
    "baby_bone_crawler": "os 5, bestia 5, victus 3",
    "alpha_bone_crawler": "os 15, bestia 10, aversio 5",
    "mother_bone_crawler": "os 20, bestia 10, victus 10",
    "primordial_bone_crawler": "os 25, bestia 15, alienis 10",
    "sleeping_primordial_crawler": "os 15, bestia 10",
    "giant_bone_crawler_egg": "os 10, victus 10, bestia 5",
    "bone_blockade": "os 10, aversio 5",
    "giant_bone_spike": "os 15, aversio 10",
    "bone_cage": "os 10, vinculum 10",
    "bone_cage_closed": "os 10, vinculum 10",
    "bone_saw": "aversio 10, os 5, machina 3",
    "skull_smasher": "os 10, aversio 10, humanus 5",
    "watcher_teeth": "os 10, aversio 10",
    "marrow_roots": "os 5, herba 5",
    # flesh
    "amalgam": "caro 15, bestia 5, vitium 5",
    "death_maw": "caro 10, bestia 10, aversio 10, abyssus 5",
    "death_maw_hidden": "caro 10, bestia 10, aversio 10, abyssus 5",
    "everhunger": "caro 15, bestia 10, desiderium 10",
    "false_hydra": "caro 20, sensus 15, alienis 10, tenebrae 10",
    "false_hydra_arm": "caro 10, alienis 5",
    "false_hydra_singing": "caro 10, sensus 10",
    "flesh_cube": "caro 15, vinculum 5",
    "small_flesh_cube": "caro 5, vinculum 2",
    "flesh_fangs": "caro 5, os 5, aversio 5",
    "flesh_lamprey": "caro 5, aqua 5, bestia 5",
    "flesh_worm": "caro 5, bestia 5, terra 3",
    "gore_expectorator": "caro 15, vitium 5, aversio 5",
    "gore_lurker": "caro 10, tenebrae 10, bestia 5",
    "grabbing_hands": "vinculum 10, tenebrae 5, caro 3",
    "hive_brain": "cognitio 15, caro 10, bestia 5",
    "hive_fangs": "caro 5, os 5, aversio 5",
    "summoned_hive_fangs": "caro 5, os 5, aversio 5",
    "hivemind_tentacles": "caro 10, vinculum 5",
    "misanthropic_hivemind": "caro 25, cognitio 15, bestia 15, alienis 10",
    "rotten_corpse": "caro 10, mortuus 10, exanimis 10",
    "falling_corpse": "caro 5, mortuus 5",
    "maggot": "bestia 3, victus 3, perditio 3",
    "swarmer": "bestia 5, volatus 5, caro 3",
    "licker": "bestia 10, sensus 5, caro 5",
    "fool_eater": "bestia 10, caro 5, alienis 5",
    # the eye
    "beholder": "oculus 20, alienis 10, sensus 10",
    "eye_of_the_watcher": "oculus 20, sensus 10, alienis 10",
    "hive_watcher": "oculus 20, caro 10, bestia 10, volatus 5",
    "centigaze": "oculus 10, bestia 10, motus 5",
    "centigaze_hidden": "oculus 10, bestia 10, motus 5",
    "eyekin": "oculus 10, bestia 5",
    "rideable_eyekin": "oculus 10, bestia 5",
    "eyekin_flying": "oculus 10, bestia 5, volatus 5",
    "rideable_flying_eyekin": "oculus 10, bestia 5, volatus 5",
    "big_eye": "oculus 15, humanus 5, mortuus 5",
    "cross_eyes": "oculus 10, humanus 5, mortuus 5",
    "four_eyes": "oculus 10, humanus 5, mortuus 5",
    "multiple_eyes": "oculus 10, humanus 5, mortuus 5",
    "overseer": "oculus 10, terra 5, praemunio 5",
    "built_overseer": "oculus 10, terra 5, praemunio 5",
    "overseer_pillar": "oculus 10, terra 5, praemunio 5",
    "built_overseer_pillar": "terra 10, oculus 5",
    "big_overseer": "oculus 15, terra 10, praemunio 10",
    "built_big_overseer": "oculus 15, terra 10, praemunio 10",
    "overseer_big_pillar": "oculus 15, terra 10, praemunio 10",
    "built_big_overseer_pillar": "terra 10, oculus 5",
    "watcher_blockade": "oculus 10, terra 5, praemunio 5",
    "watching_stalker": "tenebrae 10, oculus 10, bestia 5",
    # the dark and the dead
    "stalker": "tenebrae 15, bestia 10, aversio 5",
    "shadow_hand": "tenebrae 10, alienis 5, humanus 3",
    "hand_spawn": "tenebrae 10, alienis 5, humanus 3",
    "summoned_shadow_hand": "tenebrae 10, alienis 5, humanus 3",
    "lasting_shadow_hand": "tenebrae 10, alienis 5, humanus 3",
    "giant_shadow_hand": "tenebrae 20, alienis 10, humanus 5",
    "wanderer": "tenebrae 5, humanus 5, alienis 5",
    "hollowed": "vacuos 10, humanus 5, mortuus 5",
    "damned": "humanus 10, mortuus 10, spiritus 5",
    "forsaken": "humanus 10, spiritus 10, mortuus 10",
    "mourner": "spiritus 10, mortuus 10, humanus 5",
    "roamer": "humanus 5, mortuus 5, motus 5",
    "gravekeeper": "mortuus 10, humanus 5, terra 5",
    "undertaker": "mortuus 20, humanus 10, terra 10, spiritus 10",
    "dooming_coffin": "mortuus 15, spiritus 10, vinculum 5",
    "dooming_tombstone": "mortuus 10, terra 10, spiritus 5",
    "preserver": "mortuus 10, alienis 5, praemunio 5",
    "death_vulture": "volatus 10, mortuus 10, bestia 5",
    "muzzled_death_vulture": "volatus 10, mortuus 5, bestia 5, vinculum 5",
    "soul_orb": "spiritus 15, lux 5",
    "ghost": "spiritus 10, exanimis 5",
    "cathedral_ghost": "spiritus 10, exanimis 5",
    "ghostly_nightmare": "spiritus 10, tenebrae 10, sensus 5",
    "chained_weaver": "spiritus 15, vinculum 15, praecantatio 5",
    "weaver_of_souls_boss": "spiritus 25, praecantatio 15, vinculum 10, alienis 10",
    "warden_of_the_pit": "tenebrae 20, os 15, aversio 15, praemunio 10",
    # the cult and the prison
    "apostle_boss": "alienis 20, praecantatio 15, humanus 10, spiritus 10",
    "apostle_clone": "alienis 5, praecantatio 5, humanus 3",
    "idling_apostle": "alienis 10, humanus 5, praecantatio 5",
    "rotten_cult_member": "humanus 10, mortuus 5, exanimis 5, alienis 3",
    "mad_cultist": "humanus 10, alienis 5, vitium 5",
    "maniac": "humanus 10, aversio 10, vitium 5",
    "sage_of_wrath": "praecantatio 15, humanus 10, aversio 10, ignis 5",
    "executioner": "aversio 15, humanus 10, mortuus 5",
    "flail_executioner": "aversio 15, humanus 10, metallum 5",
    "masked_hunter": "humanus 10, aversio 10, bestia 5",
    "penitent": "humanus 10, spiritus 5, vinculum 5",
    "gaoler": "humanus 10, vinculum 10, metallum 5",
    "prison_guard": "humanus 10, praemunio 10, metallum 5",
    "prisoner": "humanus 10, vinculum 5",
    "pit_prisoner": "humanus 10, vinculum 5",
    "shank_prisoner": "humanus 10, vinculum 5, aversio 5",
    "saw_thrower": "aversio 10, humanus 5, metallum 5",
    "dark_steel_saw": "metallum 10, aversio 10, machina 5",
    # the rest
    "bog_walker": "humanus 5, aqua 5, mortuus 5, herba 3",
    "rooted": "herba 10, humanus 5, mortuus 5",
    "rooted_clone": "herba 10, humanus 5, mortuus 5",
    "scarecrow": "herba 10, sensus 5, exanimis 5",
    "spore_spewer": "herba 10, vitium 10, aer 5",
    "goo_spitter": "aqua 5, vinculum 5, bestia 5",
    "mimic": "alienis 10, permutatio 10, sensus 5",
    "thumper_entity": "terra 10, motus 5, aversio 5",
}


def aspects(spec, owner):
    out = []
    for part in spec.split(","):
        name, amount = part.split()
        if name in ASPECTS:
            aspect = f"{NS}:{name}"
        elif name in THAUMATURGE_ASPECTS:
            aspect = "thaumaturge:" + name
        else:
            sys.exit(f"{owner}: unknown aspect {name}")
        out.append({"aspect": aspect, "amount": int(amount)})
    return out


def write(path, data):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(data, indent=2) + "\n", encoding="utf-8", newline="\n")


def main():
    lang = json.loads(LANG.read_text(encoding="utf-8"))
    for item in ITEMS:
        if f"item.{NS}.{item}" not in lang and f"block.{NS}.{item}" not in lang:
            sys.exit(f"no item {NS}:{item}")
    registered = set(re.findall(r'register\("([a-z0-9_]+)"', ENTITIES_JAVA.read_text(encoding="utf-8")))
    for entity in ENTITIES:
        if entity not in registered:
            sys.exit(f"no entity type {NS}:{entity}")
    for name in ASPECTS:
        for key in (f"aspect.{NS}.{name}", f"aspect.{NS}.{name}.desc", f"aspect.{NS}.{name}.help"):
            if key not in lang:
                sys.exit(f"no translation {key}")
    write(PACK / "pack.mcmeta", {"pack": {"description": {"translate": f"pack.{NS}.thaumaturge"}, **PACK_FORMAT}})
    for name, aspect in ASPECTS.items():
        write(PACK / f"data/{NS}/thaumaturge/aspect/{name}.json",
              {"tag": name, **aspect, "texture": f"{NS}:textures/aspects/{name}.png"})
    write(PACK / "data/thaumaturge/data_maps/item/base_aspects.json",
          {"values": {f"{NS}:{k}": aspects(v, k) for k, v in ITEMS.items()}})
    write(PACK / "data/thaumaturge/data_maps/entity_type/entity_aspects.json",
          {"values": {f"{NS}:{k}": aspects(v, k) for k, v in ENTITIES.items()}})
    print(f"{len(ASPECTS)} aspects, {len(ITEMS)} items, {len(ENTITIES)} mobs -> {PACK.relative_to(ROOT)}")


if __name__ == "__main__":
    main()
