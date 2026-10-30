package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

/** A sound type whose sounds are looked up when played (they may be registered after the block) */
public class DeferredSoundType extends SoundType {
	private final Supplier<SoundEvent> breakSound, stepSound, placeSound, hitSound, fallSound;

	public DeferredSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound, Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound,
			Supplier<SoundEvent> fallSound) {
		super(volume, pitch, null, null, null, null, null);
		this.breakSound = breakSound;
		this.stepSound = stepSound;
		this.placeSound = placeSound;
		this.hitSound = hitSound;
		this.fallSound = fallSound;
	}

	@Override
	public SoundEvent getBreakSound() {
		return this.breakSound.get();
	}

	@Override
	public SoundEvent getStepSound() {
		return this.stepSound.get();
	}

	@Override
	public SoundEvent getPlaceSound() {
		return this.placeSound.get();
	}

	@Override
	public SoundEvent getHitSound() {
		return this.hitSound.get();
	}

	@Override
	public SoundEvent getFallSound() {
		return this.fallSound.get();
	}
}
