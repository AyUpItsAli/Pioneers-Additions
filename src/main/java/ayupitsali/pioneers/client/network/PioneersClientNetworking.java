package ayupitsali.pioneers.client.network;

import ayupitsali.pioneers.network.PioneerStatusPacket;
import ayupitsali.pioneers.util.PioneerStatus;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class PioneersClientNetworking {
    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(PioneerStatusPacket.TYPE.getId(), (client, handler, buf, sender) -> {
            if (client.player == null)
                return;
            PioneerStatusPacket packet = PioneerStatusPacket.TYPE.read(buf);
            client.execute(() -> {
                switch (packet.getStatus()) {
                    case GAINED_LIVES:
                        World world = client.player.getWorld();
                        PlayerEntity player = packet.getPioneerEntity(world);
                        client.particleManager.addEmitter(player, ParticleTypes.TOTEM_OF_UNDYING, 6);
                        world.playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP,
                                SoundCategory.PLAYERS, 1.0F, 1.0F, false);
                        world.playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST,
                                SoundCategory.PLAYERS, 1.0F, 1.0F, false);
                }
            });
        });
    }
}
