package ayupitsali.pioneers.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class PioneersNetworking {
    public static void sendToNearbyPlayers(ServerPlayerEntity player, FabricPacket packet) {
        sendToNearbyPlayers(player, packet, true);
    }

    public static void sendToNearbyPlayers(ServerPlayerEntity player, FabricPacket packet, boolean includeSelf) {
        if (includeSelf)
            ServerPlayNetworking.send(player, packet);
        for (ServerPlayerEntity nearbyPlayer : PlayerLookup.tracking(player)) {
            ServerPlayNetworking.send(nearbyPlayer, packet);
        }
    }
}
