package ayupitsali.pioneers.network;

import ayupitsali.pioneers.client.network.PioneerStatusPayload;
import ayupitsali.pioneers.data.PioneerStatus;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class PioneersNetworking {
    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(PioneerStatusPayload.ID, PioneerStatusPayload.CODEC);
    }

    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(PioneerStatusPayload.ID, (payload, context) -> context.client().execute(() -> {
            switch (payload.getStatus()) {
                case PioneerStatus.GAINED_LIFE:
                    World world = context.player().getWorld();
                    PlayerEntity player = payload.getPioneerEntity(world);
                    context.client().particleManager.addEmitter(player, ParticleTypes.TOTEM_OF_UNDYING, 6);
                    world.playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP,
                            SoundCategory.PLAYERS, 1.0F, 1.0F, false);
                    world.playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST,
                            SoundCategory.PLAYERS, 1.0F, 1.0F, false);
            }
        }));
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
