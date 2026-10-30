package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

/** Changes the default data components of items (both loaders: before the item registry is frozen) */
public class ModifyDefaultComponentsEvent extends Event {
	private final Sink sink;

	/** What a listener may do to an item's default components */
	public interface Builder {
		<T> Builder set(DataComponentType<T> type, T value);

		Builder remove(DataComponentType<?> type);
	}

	public interface Sink {
		void modify(ItemLike item, Consumer<Builder> patch);
	}

	public ModifyDefaultComponentsEvent(Sink sink) {
		this.sink = sink;
	}

	public void modify(ItemLike item, Consumer<Builder> patch) {
		this.sink.modify(item, patch);
	}
}
