package net.feara.tyrant.client;

import net.feara.tyrant.client.helpers.YellowedVisibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

public final class ClientRenderEvents {
    private ClientRenderEvents() {}
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        //System.out.println("Render event fired for: " + event.getEntity().getName().getString());
        LocalPlayer viewer = Minecraft.getInstance().player;
        if (viewer == null) return;

        Player target = event.getEntity();
        if (target == viewer) return;

        if (YellowedVisibility.shouldHideFrom(viewer, target)) { //0.65 is current default but needs to be nerfed a bit according to agent
            //event.setCanceled(true);
            //target.setInvisible(true); //was for testing
            YellowedVisibility.hideForRender(target);
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        LocalPlayer viewer = Minecraft.getInstance().player;
        Player target = event.getEntity();

        if (!YellowedVisibility.shouldHideFrom(viewer, target)) {
            YellowedVisibility.restoreAfterRender(target);
        }
    }
}
