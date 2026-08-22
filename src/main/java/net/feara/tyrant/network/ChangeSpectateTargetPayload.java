package net.feara.tyrant.network;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public record ChangeSpectateTargetPayload(UUID targetUUID) implements CustomPacketPayload {

    public static final Type<ChangeSpectateTargetPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "change_spectate_target"
            ));

    public static final StreamCodec<ByteBuf, ChangeSpectateTargetPayload> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC,
                    ChangeSpectateTargetPayload::targetUUID,
                    ChangeSpectateTargetPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}