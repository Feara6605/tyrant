package net.feara.tyrant.network;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public record StartSpectatePayload(UUID targetUUID) implements CustomPacketPayload {



    public static final Type<StartSpectatePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "start_spectate"
            ));

    public static final StreamCodec<ByteBuf, StartSpectatePayload> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC,
                    StartSpectatePayload::targetUUID,
                    StartSpectatePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}