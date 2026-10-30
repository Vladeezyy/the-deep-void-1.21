package net.sombrius.thedeepvoid.port;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import net.sombrius.thedeepvoid.entity.WatchingStalkerEntity;
import net.sombrius.thedeepvoid.entity.VoidDwellerEntity;
import net.sombrius.thedeepvoid.entity.UndertakerEntity;
import net.sombrius.thedeepvoid.entity.StalkerEntity;
import net.sombrius.thedeepvoid.entity.OverseerPillarEntity;
import net.sombrius.thedeepvoid.entity.OverseerEntity;
import net.sombrius.thedeepvoid.entity.OverseerBigPillarEntity;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.entity.BigOverseerEntity;
import net.sombrius.thedeepvoid.entity.AmalgamEntity;

import java.util.List;
import java.util.ArrayList;

/**
 * The entities that the player tick handlers look for every tick (stalkers, overseers, …), kept per level while the level
 * tracks them (from {@code onTrackingStart} to {@code onTrackingEnd}, the {@code EntityCallbacks} mixins), the same set
 * {@code getEntitiesOfClass} sees. {@link #find} answers from that short list instead of walking the entity sections of a
 * box up to 250 blocks wide.
 */
public final class PortEntities {
	private static final Class<?>[] TRACKED = {StalkerEntity.class, WatchingStalkerEntity.class, OverseerEntity.class, BigOverseerEntity.class, OverseerPillarEntity.class,
			OverseerBigPillarEntity.class, AmalgamEntity.class, VoidDwellerEntity.class, FalseHydraEntity.class, UndertakerEntity.class};

	/** Implemented by {@code Level} (LevelMixin). */
	public interface Holder {
		List<Entity> deepvoid$tracked();
	}

	private PortEntities() {
	}

	private static boolean tracked(Entity entity) {
		for (Class<?> c : TRACKED)
			if (c.isInstance(entity))
				return true;
		return false;
	}

	public static void onTrackingStart(Entity entity) {
		if (tracked(entity) && entity.level() instanceof Holder holder)
			holder.deepvoid$tracked().add(entity);
	}

	public static void onTrackingEnd(Entity entity) {
		if (tracked(entity) && entity.level() instanceof Holder holder)
			holder.deepvoid$tracked().remove(entity);
	}

	/** {@code world.getEntitiesOfClass(type, box, e -> true)}. */
	@SuppressWarnings("unchecked")
	public static <T extends Entity> List<T> find(LevelAccessor world, Class<T> type, AABB box) {
		if (!(world instanceof Level level) || !(level instanceof Holder holder) || !isTracked(type))
			return world.getEntitiesOfClass(type, box, e -> true);
		List<T> found = null;
		for (Entity e : holder.deepvoid$tracked()) {
			if (type.isInstance(e) && e.getBoundingBox().intersects(box)) {
				if (found == null)
					found = new ArrayList<>(2);
				found.add((T) e);
			}
		}
		return found == null ? List.of() : found;
	}

	/** Bench check: what {@link #find} answers against the entity sections, for every tracked class in a box around a point. */
	static String check(Level level, double x, double y, double z) {
		AABB box = AABB.ofSize(new net.minecraft.world.phys.Vec3(x, y, z), 250, 250, 250);
		int found = 0;
		for (Class<?> c : TRACKED) {
			@SuppressWarnings("unchecked")
			Class<Entity> type = (Class<Entity>) c;
			java.util.Set<Entity> index = new java.util.HashSet<>(find(level, type, box));
			java.util.Set<Entity> sections = new java.util.HashSet<>(level.getEntitiesOfClass(type, box, e -> true));
			if (!index.equals(sections))
				return "MISMATCH " + c.getSimpleName() + " index=" + index.size() + " sections=" + sections.size();
			found += index.size();
		}
		return "ok (" + found + " tracked entities in the box)";
	}

	private static boolean isTracked(Class<?> type) {
		for (Class<?> c : TRACKED)
			if (c.isAssignableFrom(type))
				return true;
		return false;
	}
}
