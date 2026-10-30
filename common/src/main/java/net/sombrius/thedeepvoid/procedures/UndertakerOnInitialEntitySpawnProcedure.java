package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.Holder;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.UndertakerEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class UndertakerOnInitialEntitySpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String temp = "";
		double rndConvert = 0;
		if (world.getBiome(BlockPos.containing(x, y, z)).is(ResourceLocation.parse("the_deep_void:grim_canopy")) || world.getBiome(BlockPos.containing(x, y, z)).is(ResourceLocation.parse("the_deep_void:watching_undergrowth"))) {
			if (entity instanceof UndertakerEntity animatable)
				animatable.setTexture("undertakershotnew");
		} //Roll the first trade
		for (int index0 = 0; index0 < 16; index0++) {
			temp = BuiltInRegistries.ITEM.getKey((BuiltInRegistries.ITEM.getTag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:undertaker_void_misc"))).flatMap(_t -> _t.getRandomElement(RandomSource.create())).map(Holder::value).orElseGet(() -> Items.AIR))).toString();
			if (!(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH))) == ItemStack.EMPTY.getItem())) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putString("firstTrade", temp);
				if (new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH)))).isStackable()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("firstTradeQut", (Mth.nextInt(RandomSource.create(), 2, 16)));
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("firstTradeQut", 1);
				}
				break;
			}
		}
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("firstTradePrice", (TheDeepVoidModVariables.MapVariables.get(world).undertakerKilled == true ? Mth.nextInt(RandomSource.create(), 6, 10) : Mth.nextInt(RandomSource.create(), 2, 6)));//Roll the second and third trade
		for (int index1 = 0; index1 < 16; index1++) {
			temp = BuiltInRegistries.ITEM.getKey((BuiltInRegistries.ITEM.getTag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:undertaker_void_goods"))).flatMap(_t -> _t.getRandomElement(RandomSource.create())).map(Holder::value).orElseGet(() -> Items.AIR))).toString();
			if (!(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH))) == ItemStack.EMPTY.getItem())) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putString("secondTrade", temp);
				if (new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH)))).isStackable()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("secondTradeQut", (Mth.nextInt(RandomSource.create(), 1, 12)));
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("secondTradeQut", 1);
				}
				break;
			}
		}
		for (int index2 = 0; index2 < 16; index2++) {
			temp = BuiltInRegistries.ITEM.getKey((BuiltInRegistries.ITEM.getTag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:undertaker_void_goods"))).flatMap(_t -> _t.getRandomElement(RandomSource.create())).map(Holder::value).orElseGet(() -> Items.AIR))).toString();
			if (!(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH))) == ItemStack.EMPTY.getItem())
					&& !(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH))) == BuiltInRegistries.ITEM.get(ResourceLocation.parse(((net.sombrius.thedeepvoid.port.PortUtil.data(entity).getString("secondTrade"))).toLowerCase(java.util.Locale.ENGLISH))))) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putString("thirdTrade", temp);
				if (new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH)))).isStackable()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("thirdTradeQut", (Mth.nextInt(RandomSource.create(), 1, 6)));
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("thirdTradeQut", 1);
				}
				break;
			}
		}
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("secondTradePrice", (TheDeepVoidModVariables.MapVariables.get(world).undertakerKilled == true ? Mth.nextInt(RandomSource.create(), 7, 12) : Mth.nextInt(RandomSource.create(), 3, 8)));
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("thirdTradePrice", (TheDeepVoidModVariables.MapVariables.get(world).undertakerKilled == true ? Mth.nextInt(RandomSource.create(), 9, 17) : Mth.nextInt(RandomSource.create(), 5, 12)));//Roll the fourth trade
		for (int index3 = 0; index3 < 16; index3++) {
			temp = BuiltInRegistries.ITEM.getKey((BuiltInRegistries.ITEM.getTag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:undertaker_valuables"))).flatMap(_t -> _t.getRandomElement(RandomSource.create())).map(Holder::value).orElseGet(() -> Items.AIR))).toString();
			if (!(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH))) == ItemStack.EMPTY.getItem())) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putString("fourthTrade", temp);
				if (new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH)))).isStackable()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("fourthTradeQut", (Mth.nextInt(RandomSource.create(), 1, 4)));
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("fourthTradeQut", 1);
				}
				break;
			}
		}
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("fourthTradePrice", (TheDeepVoidModVariables.MapVariables.get(world).undertakerKilled == true ? Mth.nextInt(RandomSource.create(), 52, 64) : Mth.nextInt(RandomSource.create(), 24, 32)));//Roll the potion trade
		for (int index4 = 0; index4 < 16; index4++) {
			temp = BuiltInRegistries.ITEM.getKey((BuiltInRegistries.ITEM.getTag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:undertaker_potions"))).flatMap(_t -> _t.getRandomElement(RandomSource.create())).map(Holder::value).orElseGet(() -> Items.AIR))).toString();
			if (!(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH))) == ItemStack.EMPTY.getItem())) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putString("potionTrade", temp);
				if (new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse((temp).toLowerCase(java.util.Locale.ENGLISH)))).isStackable()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("potionTradeQut", (Mth.nextInt(RandomSource.create(), 1, 4)));
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("potionTradeQut", 1);
				}
				break;
			}
		}
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("potionTradePrice", (TheDeepVoidModVariables.MapVariables.get(world).undertakerKilled == true ? Mth.nextInt(RandomSource.create(), 14, 28) : Mth.nextInt(RandomSource.create(), 7, 14)));//Set the conversion trade
		rndConvert = TheDeepVoidModVariables.MapVariables.get(world).undertakerKilled == true ? 1 : Mth.nextInt(RandomSource.create(), 2, 12);
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putString("conversion", (BuiltInRegistries.ITEM.getKey(TheDeepVoidModItems.DARK_STEEL_COIN.get()).toString()));
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("conversionQut", rndConvert);
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("conversionPrice", rndConvert);
	}
}
