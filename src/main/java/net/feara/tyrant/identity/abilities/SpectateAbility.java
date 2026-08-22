package net.feara.tyrant.identity.abilities;

import net.feara.tyrant.client.ClientIdentityCache;
import net.feara.tyrant.client.input.YellowedKeyHandler;
import net.feara.tyrant.identity.ModIdentities;
import net.feara.tyrant.network.SpectateNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.ArrayList;
import java.util.List;

public class SpectateAbility {

    static Player currentTarget;
    static int targetIndex = 0;
    private static boolean spectating;

    public static List<Player> getAvailableTargets(Player source) {
        List<Player> players = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) return players;
        for (Player other : mc.level.players()) {
            if (other == null) continue;
            if (other == source) continue;
            players.add(other);
        }
        return players;
    }

    public static void enterSpectate() {
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;
        if (localPlayer == null || mc.level == null) return;

        List<Player> availableTargets = getAvailableTargets(localPlayer);
        if (availableTargets.isEmpty()) return;

        targetIndex = Math.floorMod(targetIndex, availableTargets.size());
        currentTarget = availableTargets.get(targetIndex);

        mc.setCameraEntity(currentTarget);
        spectating = true;

        SpectateNetwork.start(currentTarget.getUUID());
    }

    public static void exitSpectate() {
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;
        if (localPlayer == null || mc.level == null) return;

        mc.setCameraEntity(localPlayer);
        currentTarget = null;
        targetIndex = 0;
        spectating = false;
        SpectateNetwork.stop();
    }

    public static void cycleTarget() {
        if (spectating) {
            Minecraft mc = Minecraft.getInstance();
            Player localPlayer = mc.player;
            if (localPlayer == null || mc.level == null) return;

            List<Player> availableTargets = getAvailableTargets(localPlayer);
            if (availableTargets.isEmpty()) return;

            targetIndex = Math.floorMod(targetIndex + 1, availableTargets.size());
            currentTarget = availableTargets.get(targetIndex);
            mc.setCameraEntity(currentTarget);
            SpectateNetwork.changeTarget(currentTarget.getUUID());
        }
    }



    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;
        if (localPlayer == null || mc.level == null) return;

        var id = ClientIdentityCache.get(localPlayer.getUUID());
        if (id == null || !id.equals(ModIdentities.YELLOWED.id())) return;
        if (spectating && currentTarget == null) {
            exitSpectate();
        }

        while (YellowedKeyHandler.TOGGLE_SPECTATE.get().consumeClick()) {
            if (!spectating) {
                enterSpectate();
            } else {
                exitSpectate();
            }

            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal("Spectate Toggled!"));
            }
        }

        while (YellowedKeyHandler.CYCLE_TARGET.get().consumeClick()) {
            cycleTarget();
            if (mc.player != null) {
                mc.player.sendSystemMessage(Component.literal("Target Cycled!"));
            }
        }
    }
}
