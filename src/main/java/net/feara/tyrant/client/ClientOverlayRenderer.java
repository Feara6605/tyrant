package net.feara.tyrant.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.feara.tyrant.identity.IdentityLogicHelper;
import net.feara.tyrant.identity.ModIdentities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

public class ClientOverlayRenderer {

    private static final ResourceLocation YELLOWED_ICON =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/gui/yellowed_icon.png"
            );

    private static final ResourceLocation YELLOWED_OVERLAY =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/overlay/yellowed_overlay.png"
            );

    private static final ResourceLocation NONE_ICON =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/gui/none_icon.png"
            );

    private static final ResourceLocation LOST_ICON =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/gui/lost_icon.png"
            );

    private static final ResourceLocation CHARRED_ICON =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/gui/charred_icon.png"
            );
    private static final ResourceLocation SWOONED_ICON =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/gui/swooned_icon.png"
            );

    private static final ResourceLocation UNCHAINED_ICON =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/gui/unchained_icon.png"
            );

    private static final ResourceLocation UNCHANGED_ICON =
            ResourceLocation.fromNamespaceAndPath(
                    "tyrant",
                    "textures/gui/unchanged_icon.png"
            );

    // Smooth overlay state
    private static float currentStrength = 0.0f;

    @SubscribeEvent
    public static void onRenderGui(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player localPlayer = mc.player;

        if (localPlayer == null || mc.level == null) return;

        GuiGraphics gui = event.getGuiGraphics();

        var localId = ClientIdentityCache.get(localPlayer.getUUID());

        if (ModIdentities.YELLOWED.id().equals(localId)) {
            gui.blit(YELLOWED_ICON, 10, 10, 0, 0, 16, 16, 16, 16);
        }
        if (ModIdentities.STRANDED.id().equals(localId)) {
            gui.blit(LOST_ICON, 10, 10, 0, 0, 16, 16, 16, 16);
        }
        if (ModIdentities.SWOONED.id().equals(localId)) {
            gui.blit(SWOONED_ICON, 10, 10, 0, 0, 16, 16, 16, 16);
        }
        if (ModIdentities.CHARRED.id().equals(localId)) {
            gui.blit(CHARRED_ICON, 10, 10, 0, 0, 16, 16, 16, 16);
        }
        if (ModIdentities.UNCHAINED.id().equals(localId)) {
            gui.blit(UNCHAINED_ICON, 10, 10, 0, 0, 16, 16, 16, 16);
        }
        if (ModIdentities.UNCHANGED.id().equals(localId)) {
            gui.blit(UNCHANGED_ICON, 10, 10, 0, 0, 16, 16, 16, 16);
        }
        if (ModIdentities.NONE.id().equals(localId)) {
            gui.blit(NONE_ICON, 10, 10, 0, 0, 16, 16, 16, 16);
        }

        float nearestDistance = IdentityLogicHelper.getNearestIdentityDistance(
                localPlayer,
                ModIdentities.YELLOWED.id(),
                10
        );

        // Calculate target strength
        float targetStrength = 0.0f;

        if (nearestDistance != Float.MAX_VALUE) {
            targetStrength = 1.0f - (nearestDistance / 10.0f);
        }

        // Smooth interpolation
        currentStrength = Mth.lerp(0.08f, currentStrength, targetStrength);

        // Tiny deadzone to prevent micro flicker
        if (currentStrength < 0.01f) {
            currentStrength = 0.0f;
        }

        if (currentStrength <= 0.0f) return;

        renderOverlay(mc, gui, currentStrength);
    }

    private static void renderOverlay(Minecraft mc, GuiGraphics gui, float strength) {
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        float time = mc.level.getGameTime();

        float pulse =
                (float)Math.sin(time * 0.03f)
                        * (0.01f + strength * 0.03f);

        float alpha = 0.01f + strength * 0.05f + pulse;
        alpha = Mth.clamp(alpha, 0.0f, 1.0f);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        gui.setColor(1f, 1f, 1f, alpha);

        gui.blit(
                YELLOWED_OVERLAY,
                0, 0,
                0, 0,
                width, height,
                512, 512
        );

        gui.setColor(1f, 1f, 1f, 1f);

        RenderSystem.disableBlend();
    }
}