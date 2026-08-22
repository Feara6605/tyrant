package net.feara.tyrant.network;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import io.netty.buffer.ByteBuf;

public record StopSpectatePayload() implements CustomPacketPayload {

    public static final Type<StopSpectatePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "stop_spectate"
            ));

    public static final StreamCodec<ByteBuf, StopSpectatePayload> STREAM_CODEC =
            StreamCodec.unit(new StopSpectatePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}