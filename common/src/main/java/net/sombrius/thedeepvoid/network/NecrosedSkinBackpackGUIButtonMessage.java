package net.sombrius.thedeepvoid.network;

import net.sombrius.thedeepvoid.port.neo.IPayloadContext;
import net.sombrius.thedeepvoid.port.bus.FMLCommonSetupEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.HashMap;

import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.procedures.NecrosedSkinBackpackCraftingButtonClickedProcedure;
import net.sombrius.thedeepvoid.world.inventory.NecrosedSkinBackpackGUIMenu;

@EventBusSubscriber
public record NecrosedSkinBackpackGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<NecrosedSkinBackpackGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, "necrosed_skin_backpack_gui_button"));
	public static final StreamCodec<RegistryFriendlyByteBuf, NecrosedSkinBackpackGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, NecrosedSkinBackpackGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new NecrosedSkinBackpackGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<NecrosedSkinBackpackGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final NecrosedSkinBackpackGUIButtonMessage message, final IPayloadContext context) {
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
		HashMap guistate = NecrosedSkinBackpackGUIMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			NecrosedSkinBackpackCraftingButtonClickedProcedure.execute(x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		TheDeepVoidMod.addNetworkMessage(NecrosedSkinBackpackGUIButtonMessage.TYPE, NecrosedSkinBackpackGUIButtonMessage.STREAM_CODEC, NecrosedSkinBackpackGUIButtonMessage::handleData);
	}
}
