
package net.sombrius.thedeepvoid.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.procedures.WingsBackwardOnKeyPressedProcedure;
import net.sombrius.thedeepvoid.procedures.WingsBackwardOnKeyReleasedProcedure;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record WingsBackwardMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<WingsBackwardMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, "key_wings_backward"));
	public static final StreamCodec<RegistryFriendlyByteBuf, WingsBackwardMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, WingsBackwardMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new WingsBackwardMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<WingsBackwardMessage> type() {
		return TYPE;
	}

	public static void handleData(final WingsBackwardMessage message, final IPayloadContext context) {
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

			WingsBackwardOnKeyPressedProcedure.execute(entity);
		}
		if (type == 1) {

			WingsBackwardOnKeyReleasedProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		TheDeepVoidMod.addNetworkMessage(WingsBackwardMessage.TYPE, WingsBackwardMessage.STREAM_CODEC, WingsBackwardMessage::handleData);
	}
}
