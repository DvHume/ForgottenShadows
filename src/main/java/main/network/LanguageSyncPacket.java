package main.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class LanguageSyncPacket {

    public static final Map<UUID, String> playerLanguages = new HashMap<>();

    private final String language;

    public LanguageSyncPacket(String language) {
        this.language = language;
    }

    public static void encode(LanguageSyncPacket packet, PacketBuffer buffer) {
        buffer.writeUtf(packet.language);
    }

    public static LanguageSyncPacket decode(PacketBuffer buffer) {
        return new LanguageSyncPacket(buffer.readUtf());
    }

    public static void handle(LanguageSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                playerLanguages.put(player.getUUID(), packet.language);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
