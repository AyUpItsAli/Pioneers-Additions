package ayupitsali.pioneers.mixin;

import ayupitsali.pioneers.PioneersConfig;
import ayupitsali.pioneers.network.PioneerStatusPayload;
import ayupitsali.pioneers.util.LivesGroup;
import ayupitsali.pioneers.data.Pioneer;
import ayupitsali.pioneers.data.PioneerData;
import ayupitsali.pioneers.util.PioneerStatus;
import ayupitsali.pioneers.util.PioneersUtils;
import ayupitsali.pioneers.network.PioneersNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropInventory", at = @At("HEAD"))
    private void onDropInventory(final CallbackInfo info) {
        Pioneer pioneer = PioneerData.getPioneer(this);
        if (!pioneer.getLivesGroup().equals(LivesGroup.GRAY)) {
            if (attackingPlayer != null) {
                Pioneer attacker = PioneerData.getPioneer(attackingPlayer);
                if (attacker.shouldGainLivesFromKill(pioneer)) {
                    attacker.addLives(PioneersConfig.LIVES_GAINED_ON_KILL);
                    PioneersNetworking.sendToNearbyPlayers((ServerPlayerEntity) attackingPlayer, new PioneerStatusPayload(attackingPlayer, PioneerStatus.GAINED_LIVES));
                    attackingPlayer.sendMessage(Text.translatable("lives.gained_lives.kill", Pioneer.getLivesText(PioneersConfig.LIVES_GAINED_ON_KILL, Formatting.GREEN), getDisplayName()));
                }
            }
            if (pioneer.addLives(-PioneersConfig.LIVES_LOST_ON_DEATH) == 0) {
                PioneersUtils.handleOutOfLives(this);
            } else {
                sendMessage(Text.translatable("lives.lives_changed", pioneer.getLivesDisplay()));
            }
        }
    }

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void onGetDisplayName(CallbackInfoReturnable<Text> cir) {
        LivesGroup livesGroup = PioneerData.getPioneer(this).getLivesGroup();
        cir.setReturnValue(cir.getReturnValue().copy().formatted(livesGroup.getColorFormatting()));
    }
}