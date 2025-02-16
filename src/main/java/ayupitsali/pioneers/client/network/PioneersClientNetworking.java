package ayupitsali.pioneers.client.network;

import ayupitsali.pioneers.network.PioneerStatusPayload;
import ayupitsali.pioneers.util.PioneerStatus;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class PioneersClientNetworking {
    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(PioneerStatusPayload.ID, (payload, context) -> context.client().execute(() -> {
            switch (payload.getStatus()) {
                case PioneerStatus.GAINED_LIVES:
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
}
