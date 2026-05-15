package main.network;

import main.tile.ChemistryTableTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ChemistryCraftPacket {
    private final BlockPos pos;

    public ChemistryCraftPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static ChemistryCraftPacket decode(PacketBuffer buf) {
        return new ChemistryCraftPacket(buf.readBlockPos());
    }

    public void encode(PacketBuffer buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(ChemistryCraftPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerWorld world = ctx.get().getSender().getLevel();
            if (world.isLoaded(msg.pos)) {
                TileEntity tile = world.getBlockEntity(msg.pos);
                if (tile instanceof ChemistryTableTile) {
                    ((ChemistryTableTile) tile).craft();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
