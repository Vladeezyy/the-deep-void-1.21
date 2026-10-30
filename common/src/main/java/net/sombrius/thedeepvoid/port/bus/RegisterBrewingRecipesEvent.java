package net.sombrius.thedeepvoid.port.bus;

import net.sombrius.thedeepvoid.port.neo.IBrewingRecipe;

import java.util.function.Consumer;

/** Adds brewing recipes in code (both loaders run the list at the brewing stand) */
public class RegisterBrewingRecipesEvent extends Event {
	private final Builder builder;

	public RegisterBrewingRecipesEvent(Consumer<IBrewingRecipe> sink) {
		this.builder = new Builder(sink);
	}

	public Builder getBuilder() {
		return this.builder;
	}

	public static final class Builder {
		private final Consumer<IBrewingRecipe> sink;

		Builder(Consumer<IBrewingRecipe> sink) {
			this.sink = sink;
		}

		public void addRecipe(IBrewingRecipe recipe) {
			this.sink.accept(recipe);
		}
	}
}
