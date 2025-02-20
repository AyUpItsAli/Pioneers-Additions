package ayupitsali.pioneers.util;

import ayupitsali.pioneers.PioneersConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class PioneersUtils {
    public static void handleOutOfLives(LivingEntity player) {
        if (player instanceof PlayerEntity playerEntity) {
            World world = playerEntity.getWorld();
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightningEntity.setCosmetic(true);
            lightningEntity.setPosition(playerEntity.getPos());
            world.spawnEntity(lightningEntity);
            world.getPlayers().forEach(plr -> plr.sendMessage(Text.translatable("lives.out_of_lives", playerEntity.getDisplayName(), PioneersConfig.getTermForLivesPlural())));
        }
    }
}
