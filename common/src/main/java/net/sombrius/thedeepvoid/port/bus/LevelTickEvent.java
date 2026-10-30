package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.level.Level;

public abstract class LevelTickEvent extends Event {
	private final Level level;

	protected LevelTickEvent(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return this.level;
	}

	/** After a level ticked (server and client) */
	public static class Post extends LevelTickEvent {
		public Post(Level level) {
			super(level);
		}
	}
}
