
package net.sombrius.thedeepvoid.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.FMLCommonSetupEvent;
import net.sombrius.thedeepvoid.port.neo.IPayloadContext;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.procedures.MountUpOnKeyPressedProcedure;
import net.sombrius.thedeepvoid.procedures.MountUpOnKeyReleasedProcedure;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record MountUpMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<MountUpMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, "key_mount_up"));
	public static final StreamCodec<RegistryFriendlyByteBuf, MountUpMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, MountUpMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new MountUpMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<MountUpMessage> type() {
		return TYPE;
	}

	public static void handleData(final MountUpMessage message, final IPayloadContext context) {
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

			MountUpOnKeyPressedProcedure.execute(entity);
		}
		if (type == 1) {

			MountUpOnKeyReleasedProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		TheDeepVoidMod.addNetworkMessage(MountUpMessage.TYPE, MountUpMessage.STREAM_CODEC, MountUpMessage::handleData);
	}
}
