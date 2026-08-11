package net.feara.tyrant.client.helpers;

import net.feara.tyrant.client.ClientIdentityCache;
import net.feara.tyrant.identity.ModIdentities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class YellowedVisibility {
    private YellowedVisibility() {}
    public static boolean shouldHideFrom(LocalPlayer viewer, Player target) {
        ResourceLocation id = ClientIdentityCache.get(target.getUUID());
        if (id == null || !id.equals(ModIdentities.YELLOWED.id())) return false;
        //System.out.println("ID: " + id);
        var camera = Minecraft.getInstance()
                .gameRenderer
                .getMainCamera()
                .getPosition();

        var look = viewer.getLookAngle();

        var toTarget = target.getEyePosition()
                .subtract(camera)
                .normalize();

        double dot = look.normalize().dot(toTarget);

        //0.65 is current default but needs to be nerfed a bit according to agent
        //target.setInvisible(true); //was for testing
        return dot > 0.65;
    }

    public static void hideForRender(Player target) {
        target.setInvisible(true);
    }

    public static void restoreAfterRender(Player target) {
        target.setInvisible(false);
    }
}
