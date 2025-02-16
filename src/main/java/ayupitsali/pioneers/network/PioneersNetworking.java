package ayupitsali.pioneers.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class PioneersNetworking {
    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(PioneerStatusPayload.ID, PioneerStatusPayload.CODEC);
    }

    public static void sendToNearbyPlayers(ServerPlayerEntity player, CustomPayload payload) {
        sendToNearbyPlayers(player, payload, true);
    }

    public static void sendToNearbyPlayers(ServerPlayerEntity player, CustomPayload payload, boolean includeSelf) {
        if (includeSelf)
            ServerPlayNetworking.send(player, payload);
        for (ServerPlayerEntity nearbyPlayer : PlayerLookup.tracking(player)) {
            ServerPlayNetworking.send(nearbyPlayer, payload);
        }
    }
}
