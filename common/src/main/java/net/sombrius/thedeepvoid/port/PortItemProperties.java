package net.sombrius.thedeepvoid.port;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MCreator's item properties (1.21.1 ItemProperties.register with a value procedure). Since 1.21.4 an item's model is
 * chosen by its item model definition (assets/the_deep_void/items): the definitions written by
 * tools/port_resources_1_21_11.py select by {@code "property": "the_deep_void:procedure", "name": <property id>},
 * and this property runs the registered procedure. The loaders register {@link #ID} with {@link #MAP_CODEC}.
 */
public final class PortItemProperties {
	public static final Identifier ID = Identifier.fromNamespaceAndPath("the_deep_void", "procedure");
	private static final Map<Identifier, Function> FUNCTIONS = new ConcurrentHashMap<>();

	@FunctionalInterface
	public interface Function {
		float get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed);
	}

	public record Procedure(Identifier name) implements RangeSelectItemModelProperty {
		public static final MapCodec<Procedure> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("name").forGetter(Procedure::name)).apply(instance, Procedure::new));

		@Override
		public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
			Function function = FUNCTIONS.get(this.name);
			return function == null ? 0 : function.get(stack, level, owner == null ? null : owner.asLivingEntity(), seed);
		}

		@Override
		public MapCodec<Procedure> type() {
			return MAP_CODEC;
		}
	}

	public static final MapCodec<Procedure> MAP_CODEC = Procedure.MAP_CODEC;

	private PortItemProperties() {
	}

	/** MCreator's ItemProperties.register (the item is in the item model definition) */
	public static void register(Item item, Identifier name, Function function) {
		FUNCTIONS.put(name, function);
	}
}
