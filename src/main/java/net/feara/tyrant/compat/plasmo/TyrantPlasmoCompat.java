package net.feara.tyrant.compat.plasmo;

import su.plo.voice.api.addon.AddonInitializer;
import su.plo.voice.api.addon.InjectPlasmoVoice;
import su.plo.voice.api.addon.annotation.Addon;
import su.plo.voice.api.server.PlasmoVoiceServer;

@Addon(
        id = "tyrant-plasmo",
        name = "Tyrant Plasmo Compatibility",
        version = "1.0.0",
        authors = {"Feara"}
)
public final class TyrantPlasmoCompat implements AddonInitializer {

    @InjectPlasmoVoice
    private PlasmoVoiceServer voiceServer;

    @Override
    public void onAddonInitialize() {
        System.out.println("[Tyrant] Plasmo Voice compatibility initialized!");
    }

    @Override
    public void onAddonShutdown() {
        System.out.println("[Tyrant] Plasmo Voice compatibility shut down!");
    }
}