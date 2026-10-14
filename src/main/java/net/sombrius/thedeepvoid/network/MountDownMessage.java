
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
import net.sombrius.thedeepvoid.procedures.MountDownOnKeyPressedProcedure;
import net.sombrius.thedeepvoid.procedures.MountUpOnKeyReleasedProcedure;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record MountDownMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<MountDownMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, "key_mount_down"));
	public static final StreamCodec<RegistryFriendlyByteBuf, MountDownMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, MountDownMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new MountDownMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<MountDownMessage> type() {
		return TYPE;
	}

	public static void handleData(final MountDownMessage message, final IPayloadContext context) {
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

			MountDownOnKeyPressedProcedure.execute(entity);
		}
		if (type == 1) {

			MountUpOnKeyReleasedProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		TheDeepVoidMod.addNetworkMessage(MountDownMessage.TYPE, MountDownMessage.STREAM_CODEC, MountDownMessage::handleData);
	}
}
