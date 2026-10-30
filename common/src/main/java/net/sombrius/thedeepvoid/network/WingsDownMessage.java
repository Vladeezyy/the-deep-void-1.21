
package net.sombrius.thedeepvoid.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.FMLCommonSetupEvent;
import net.sombrius.thedeepvoid.port.neo.IPayloadContext;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.procedures.WingsDownOnKeyPressedProcedure;
import net.sombrius.thedeepvoid.procedures.WingsDownOnKeyReleasedProcedure;

@EventBusSubscriber
public record WingsDownMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<WingsDownMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, "key_wings_down"));
	public static final StreamCodec<RegistryFriendlyByteBuf, WingsDownMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, WingsDownMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new WingsDownMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<WingsDownMessage> type() {
		return TYPE;
	}

	public static void handleData(final WingsDownMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				pressAction(context.player(), message.eventType, message.pressedms);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {

			WingsDownOnKeyPressedProcedure.execute(entity);
		}
		if (type == 1) {

			WingsDownOnKeyReleasedProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		TheDeepVoidMod.addNetworkMessage(WingsDownMessage.TYPE, WingsDownMessage.STREAM_CODEC, WingsDownMessage::handleData);
	}
}
