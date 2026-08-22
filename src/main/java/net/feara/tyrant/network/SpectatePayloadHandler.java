package net.feara.tyrant.network;

import net.feara.tyrant.identity.IdentityManager;
import net.feara.tyrant.identity.ModIdentities;
import net.feara.tyrant.identity.abilities.PlayerSpectateData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SpectatePayloadHandler {

    public static void handleStart(

            StartSpectatePayload payload,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }
            if (!IdentityManager.getIdentity(player).equals(ModIdentities.YELLOWED.id())) {
                return;
            }
            ServerPlayer target = player.serverLevel().getServer()
                    .getPlayerList()
                    .getPlayer(payload.targetUUID());

            if (target == null || target == player) {
                return;
            }
            System.out.println(player.getName().getString() + " is spectating " + target.getName().getString());
            PlayerSpectateData data = PlayerSpectateData.get(player);
            data.setTarget(payload.targetUUID());
        });
    }

    public static void handleChange(
            ChangeSpectateTargetPayload payload,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }

            ServerPlayer target = player.serverLevel().getServer()
                    .getPlayerList()
                    .getPlayer(payload.targetUUID());

            if (target == null || target == player) {
                return;
            }
            System.out.println(player.getName().getString() + " changed to spectate " + target.getName().getString());
            PlayerSpectateData data = PlayerSpectateData.get(player);
            data.setTarget(payload.targetUUID());
        });
    }

    public static void handleStop(
            StopSpectatePayload payload,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }

            PlayerSpectateData data = PlayerSpectateData.get(player);
            data.stopSpectating();
        });
    }
}