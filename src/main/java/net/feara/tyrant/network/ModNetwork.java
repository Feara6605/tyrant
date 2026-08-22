package net.feara.tyrant.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ModNetwork {
    private ModNetwork() {}

    public static void register(RegisterPayloadHandlersEvent event) {
        System.out.println("Registering identity sync payload");

        var registrar = event.registrar("1");

        registrar.playToClient(
                IdentitySyncPayload.TYPE,
                IdentitySyncPayload.STREAM_CODEC,
                IdentitySyncPayloadHandler::handleClient
        );
        registrar.playToServer(
                StartSpectatePayload.TYPE,
                StartSpectatePayload.STREAM_CODEC,
                SpectatePayloadHandler::handleStart
        );

        registrar.playToServer(
                ChangeSpectateTargetPayload.TYPE,
                ChangeSpectateTargetPayload.STREAM_CODEC,
                SpectatePayloadHandler::handleChange
        );

        registrar.playToServer(
                StopSpectatePayload.TYPE,
                StopSpectatePayload.STREAM_CODEC,
                SpectatePayloadHandler::handleStop
        );
    }
}