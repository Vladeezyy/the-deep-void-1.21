package net.sombrius.thedeepvoid.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.HashMap;

import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.procedures.UndertakerButton1ClickProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerButton2ClickProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerButton3ClickProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerButton4ClickProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerButton5ClickProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerButton6ClickProcedure;
import net.sombrius.thedeepvoid.world.inventory.UndertakerTradeGuiMenu;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record UndertakerTradeGuiButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<UndertakerTradeGuiButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, "undertaker_trade_gui_button"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UndertakerTradeGuiButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, UndertakerTradeGuiButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new UndertakerTradeGuiButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<UndertakerTradeGuiButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final UndertakerTradeGuiButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = UndertakerTradeGuiMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			UndertakerButton1ClickProcedure.execute(entity);
		}
		if (buttonID == 1) {

			UndertakerButton2ClickProcedure.execute(entity);
		}
		if (buttonID == 2) {

			UndertakerButton3ClickProcedure.execute(entity);
		}
		if (buttonID == 3) {

			UndertakerButton4ClickProcedure.execute(entity);
		}
		if (buttonID == 4) {

			UndertakerButton5ClickProcedure.execute(entity);
		}
		if (buttonID == 5) {

			UndertakerButton6ClickProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		TheDeepVoidMod.addNetworkMessage(UndertakerTradeGuiButtonMessage.TYPE, UndertakerTradeGuiButtonMessage.STREAM_CODEC, UndertakerTradeGuiButtonMessage::handleData);
	}
}
