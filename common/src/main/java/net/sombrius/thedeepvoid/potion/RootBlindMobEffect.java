
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.RootBlindEffectExpiresProcedure;

import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.port.neo.IClientMobEffectExtensions;
import net.sombrius.thedeepvoid.procedures.RootBlindOnEffectActiveTickProcedure;

public class RootBlindMobEffect extends MobEffect implements PortUtil.EffectRemovedListener, net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Effect {
	public RootBlindMobEffect() {
		super(MobEffectCategory.HARMFUL, -13290701);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		RootBlindOnEffectActiveTickProcedure.execute(entity.level(), entity);
		return super.applyEffectTick(level, entity, amplifier);
	}

	public IClientMobEffectExtensions clientExtensions() {
		return new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance effect) {
				return false;
			}

			@Override
			public boolean renderInventoryText(MobEffectInstance instance, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int blitOffset) {
				return false;
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		};
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		RootBlindEffectExpiresProcedure.execute(entity.level(), entity);
	}
}
