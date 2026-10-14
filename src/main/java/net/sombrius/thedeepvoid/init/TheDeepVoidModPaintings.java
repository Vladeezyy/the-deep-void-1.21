
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

public class TheDeepVoidModPaintings {
	public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(Registries.PAINTING_VARIANT, TheDeepVoidMod.MODID);
	public static final DeferredHolder<PaintingVariant, PaintingVariant> DECEITFUL = REGISTRY.register("deceitful", () -> new PaintingVariant(32, 32));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> OMINOUS = REGISTRY.register("ominous", () -> new PaintingVariant(16, 16));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> NECROSIS = REGISTRY.register("necrosis", () -> new PaintingVariant(16, 32));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> SCOPOPHOBIA = REGISTRY.register("scopophobia", () -> new PaintingVariant(32, 16));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> SANCTUARY = REGISTRY.register("sanctuary", () -> new PaintingVariant(48, 48));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> UNEASY = REGISTRY.register("uneasy", () -> new PaintingVariant(32, 32));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> REGRETS = REGISTRY.register("regrets", () -> new PaintingVariant(16, 16));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> GRUESOME = REGISTRY.register("gruesome", () -> new PaintingVariant(48, 48));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> I_AM_YOU = REGISTRY.register("i_am_you", () -> new PaintingVariant(32, 32));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> THREATENING = REGISTRY.register("threatening", () -> new PaintingVariant(32, 32));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> THE_DEEP_VOID_MOD = REGISTRY.register("the_deep_void_mod", () -> new PaintingVariant(160, 160));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> CHAINED_UP = REGISTRY.register("chained_up", () -> new PaintingVariant(48, 32));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> DEVOURER_ENCOUNTER = REGISTRY.register("devourer_encounter", () -> new PaintingVariant(64, 64));
	public static final DeferredHolder<PaintingVariant, PaintingVariant> GAZE = REGISTRY.register("gaze", () -> new PaintingVariant(32, 16));
}
