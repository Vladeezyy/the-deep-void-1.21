package net.sombrius.thedeepvoid.port.bus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The mod's own event bus: listeners per event class (exact class, no hierarchy), in priority order. Posting an event
 * nobody listens to costs one map lookup.
 */
public final class ModBus {
	private static final Map<Class<?>, List<Listener<?>>> ALL = new IdentityHashMap<>();
	private static final Map<Class<?>, Listener<?>[]> LISTENERS = new IdentityHashMap<>();
	private static final Set<Class<?>> DISABLED = new HashSet<>();
	private static final Listener<?>[] NONE = new Listener<?>[0];

	private record Listener<E>(EventPriority priority, Consumer<E> handler, Class<?> owner) {
	}

	private ModBus() {
	}

	public static <E extends Event> void add(Class<E> type, EventPriority priority, Consumer<E> handler) {
		add(type, priority, handler, ModBus.class);
	}

	/** {@code owner}: the class the listener belongs to ({@link #setEnabled}) */
	public static synchronized <E extends Event> void add(Class<E> type, EventPriority priority, Consumer<E> handler, Class<?> owner) {
		List<Listener<?>> list = ALL.computeIfAbsent(type, t -> new ArrayList<>());
		list.add(new Listener<>(priority, handler, owner));
		list.sort(Comparator.comparing(Listener::priority));
		rebuild(type);
	}

	/** Takes the listeners of a class off the bus or puts them back (the bench scene's base line) */
	public static synchronized void setEnabled(Class<?> owner, boolean enabled) {
		if (enabled ? DISABLED.remove(owner) : DISABLED.add(owner))
			ALL.keySet().forEach(ModBus::rebuild);
	}

	private static void rebuild(Class<?> type) {
		Listener<?>[] active = ALL.get(type).stream().filter(l -> !DISABLED.contains(l.owner())).toArray(Listener<?>[]::new);
		if (active.length == 0)
			LISTENERS.remove(type);
		else
			LISTENERS.put(type, active);
	}

	public static boolean hasListeners(Class<? extends Event> type) {
		return LISTENERS.containsKey(type);
	}

	/** Runs the listeners; returns true if the event was canceled */
	@SuppressWarnings("unchecked")
	public static <E extends Event> boolean post(E event) {
		for (Listener<?> listener : LISTENERS.getOrDefault(event.getClass(), NONE)) {
			((Consumer<E>) listener.handler()).accept(event);
		}
		return event.canceled();
	}
}
