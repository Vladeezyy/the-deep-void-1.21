package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

@FunctionalInterface
public interface IPayloadHandler<T extends CustomPacketPayload> {
	void handle(T payload, IPayloadContext context);
}
