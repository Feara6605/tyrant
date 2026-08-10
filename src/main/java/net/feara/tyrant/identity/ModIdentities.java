package net.feara.tyrant.identity;

import net.minecraft.resources.ResourceLocation;

public class ModIdentities {
    public static final IdentityType YELLOWED =
            new IdentityType(ResourceLocation.parse("tyrant:yellowed"), "yellowed");
    public static final IdentityType SWOONED =
            new IdentityType(ResourceLocation.parse("tyrant:swooned"), "swooned");
    public static final IdentityType CHARRED =
            new IdentityType(ResourceLocation.parse("tyrant:charred"), "charred");
    public static final IdentityType UNCHAINED =
            new IdentityType(ResourceLocation.parse("tyrant:unchained"), "unchained");
    public static final IdentityType UNCHANGED =
            new IdentityType(ResourceLocation.parse("tyrant:unchanged"), "unchanged");
    public static final IdentityType STRANDED =
            new IdentityType(ResourceLocation.parse("tyrant:stranded"), "stranded");
    public static final IdentityType NONE =
            new IdentityType(ResourceLocation.parse("tyrant:none"), "none");
    public static void init() {
        IdentityRegistry.register(YELLOWED);
        IdentityRegistry.register(SWOONED);
        IdentityRegistry.register(CHARRED);
        IdentityRegistry.register(UNCHAINED);
        IdentityRegistry.register(STRANDED);
        IdentityRegistry.register(UNCHANGED);
        IdentityRegistry.register(NONE);
    }
}
