package net.feara.tyrant.network;

import net.neoforged.neoforge.network.PacketDistributor;

import java.util.UUID;

public final class SpectateNetwork {

    private SpectateNetwork() {}

    public static void start(UUID targetUUID) {
        PacketDistributor.sendToServer(
                new StartSpectatePayload(targetUUID)
        );
    }

    public static void changeTarget(UUID targetUUID) {
        PacketDistributor.sendToServer(
                new ChangeSpectateTargetPayload(targetUUID)
        );
    }

    public static void stop() {
        PacketDistributor.sendToServer(
                new StopSpectatePayload()
        );
    }
}