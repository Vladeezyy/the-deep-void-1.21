package net.sombrius.thedeepvoid.port;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.ProvidesTrimMaterial;

import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 1.21.5: an item provides a trim material through its provides_trim_material component (1.21.1: the material named
 * its ingredient). tools/port_resources_1_21_11.py writes the item -> material map of the mod's materials.
 */
@EventBusSubscriber
public final class PortTrimMaterials {
	private PortTrimMaterials() {
	}

	@SubscribeEvent
	public static void addComponents(ModifyDefaultComponentsEvent event) {
		try (InputStream in = PortTrimMaterials.class.getResourceAsStream("/the_deep_void_trim_materials.json")) {
			if (in == null)
				return;
			JsonObject map = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
			map.entrySet().forEach(entry -> BuiltInRegistries.ITEM.getOptional(Identifier.parse(entry.getKey())).ifPresent(item -> event.modify(item, builder -> builder.set(
					DataComponents.PROVIDES_TRIM_MATERIAL, new ProvidesTrimMaterial(ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.parse(entry.getValue().getAsString())))))));
		} catch (Exception e) {
			TheDeepVoidMod.LOGGER.error("Trim materials of the mod's items", e);
		}
	}
}
