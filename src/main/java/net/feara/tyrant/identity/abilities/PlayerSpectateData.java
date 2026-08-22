package net.feara.tyrant.identity.abilities;

import net.feara.tyrant.ModAttachments;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class PlayerSpectateData {
    private boolean spectating = false;
    private UUID targetUUID;

    public static PlayerSpectateData get(Player player) {
        return player.getData(ModAttachments.PLAYER_SPECTATE);
    }

    public boolean isSpectating() {
        return spectating;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    public void setTarget(UUID targetUUID) {
        if (targetUUID == null) {
            stopSpectating();
            return;
        }

        this.spectating = true;
        this.targetUUID = targetUUID;
    }

    public void stopSpectating() {
        this.spectating = false;
        this.targetUUID = null;
    }


}
