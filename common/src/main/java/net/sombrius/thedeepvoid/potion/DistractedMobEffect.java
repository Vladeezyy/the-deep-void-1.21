
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.port.neo.IClientMobEffectExtensions;
import net.sombrius.thedeepvoid.procedures.DistractedOnEffectActiveTickProcedure;

public class DistractedMobEffect extends MobEffect implements net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Effect {
	public DistractedMobEffect() {
		super(MobEffectCategory.NEUTRAL, -11852011);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		DistractedOnEffectActiveTickProcedure.execute(entity);
		return super.applyEffectTick(level, entity, amplifier);
	}

	public IClientMobEffectExtensions clientExtensions() {
		return new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance effect) {
				return false;
			}

			@Override
			public boolean renderInventoryText(MobEffectInstance instance, AbstractContainerScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
				return false;
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		};
	}
}
