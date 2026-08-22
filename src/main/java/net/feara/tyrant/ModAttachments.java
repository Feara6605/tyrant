package net.feara.tyrant;

import net.feara.tyrant.identity.PlayerIdentityData;
import net.feara.tyrant.identity.abilities.PlayerSpectateData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public final class ModAttachments {
    public static final String MODID = "tyrant";
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);
    public static final Supplier<AttachmentType<PlayerIdentityData>> PLAYER_IDENTITY =
            ATTACHMENT_TYPES.register("player_identity",
                    () -> AttachmentType.<PlayerIdentityData>builder(PlayerIdentityData::new)
                            .serialize(new IAttachmentSerializer<CompoundTag, PlayerIdentityData>() {
                                           @Override
                                           public PlayerIdentityData read(IAttachmentHolder iAttachmentHolder, CompoundTag tag, HolderLookup.Provider provider) {
                                               PlayerIdentityData data = new PlayerIdentityData();
                                               data.load(tag);

                                               return data;
                                           }

                                           @Override
                                           public @Nullable CompoundTag write(PlayerIdentityData attachment, HolderLookup.Provider provider) {
                                               return attachment.save();
                                           }
                                       }
                            )
                            .copyOnDeath()
                            .build());
    public static final Supplier<AttachmentType<PlayerSpectateData>> PLAYER_SPECTATE =
            ATTACHMENT_TYPES.register("player_spectate",
                    () -> AttachmentType.<PlayerSpectateData>builder(PlayerSpectateData::new)
                            .build());
    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
    private ModAttachments() {}
}
