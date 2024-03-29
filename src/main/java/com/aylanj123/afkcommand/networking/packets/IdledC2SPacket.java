package com.aylanj123.afkcommand.networking.packets;

import com.aylanj123.afkcommand.afkstate.capability.PlayerAFKStateProvider;
import com.aylanj123.afkcommand.afkstate.capability.StateSource;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IdledC2SPacket {

    public IdledC2SPacket() {

    }

    public IdledC2SPacket(FriendlyByteBuf buffer) {
        this();
    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public void handle(Supplier<NetworkEvent.ClientCustomPayloadEvent.Context> cxSupplier) {
        NetworkEvent.Context cx = cxSupplier.get();
        ServerPlayer player = cx.getSender();
        if (player == null) {
            cx.setPacketHandled(false);
            return;
        }
        player.getCapability(PlayerAFKStateProvider.AFK_STATE).ifPresent(cap -> {
            if (!cap.isAFK()) cap.putAFK(StateSource.IDLED_TOO_LONG, player);
        });
        cx.setPacketHandled(true);
    }

}
