package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.level.LevelAccessor;

public abstract class LevelEvent extends Event {
	private final LevelAccessor level;

	protected LevelEvent(LevelAccessor level) {
		this.level = level;
	}

	public LevelAccessor getLevel() {
		return this.level;
	}

	/** A level was loaded (server and client) */
	public static class Load extends LevelEvent {
		public Load(LevelAccessor level) {
			super(level);
		}
	}
}
