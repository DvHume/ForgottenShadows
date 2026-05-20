package main.init;

import main.network.ChemistryCraftPacket;
import main.network.LanguageSyncPacket;
//import main.network.SoulSyncPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ModNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(

            new ResourceLocation("frs", "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        /*CHANNEL.registerMessage(0, SoulSyncPacket.class,
                SoulSyncPacket::encode,
                SoulSyncPacket::decode,
                SoulSyncPacket::handle);*/

        CHANNEL.registerMessage(1, LanguageSyncPacket.class,
                LanguageSyncPacket::encode,
                LanguageSyncPacket::decode,
                LanguageSyncPacket::handle);

        CHANNEL.registerMessage(2, ChemistryCraftPacket.class,
                ChemistryCraftPacket::encode,
                ChemistryCraftPacket::decode,
                ChemistryCraftPacket::handle);
    }

    public static <MSG> void sendToServer(MSG message) {
        CHANNEL.sendToServer(message);
    }
}
