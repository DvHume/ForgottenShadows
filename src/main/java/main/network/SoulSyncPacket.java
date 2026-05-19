/*package main.network;

import main.capability.SoulCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SoulSyncPacket {

    private final float souls;

    public SoulSyncPacket(float souls) {
        this.souls = souls;
    }

    public static void encode(SoulSyncPacket packet, PacketBuffer buffer) {
        buffer.writeFloat(packet.souls);
    }

    public static SoulSyncPacket decode(PacketBuffer buffer) {
        return  new SoulSyncPacket(buffer.readFloat());
    }

    public static void handle(SoulSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> handleClient(packet));
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(SoulSyncPacket packet) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        mc.player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
            souls.setSouls(packet.souls);
        });
    }
}
*/