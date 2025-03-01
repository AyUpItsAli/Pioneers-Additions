package ayupitsali.pioneers.item;

import ayupitsali.pioneers.PioneersConfig;
import ayupitsali.pioneers.network.PioneerStatusPayload;
import ayupitsali.pioneers.util.LivesGroup;
import ayupitsali.pioneers.data.Pioneer;
import ayupitsali.pioneers.data.PioneerData;
import ayupitsali.pioneers.util.PioneerStatus;
import ayupitsali.pioneers.network.PioneersNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class LifeTokenItem extends ShinyItem {
    public LifeTokenItem() {
        super(true, Formatting.LIGHT_PURPLE, new Item.Settings().maxCount(16));
    }

    @Override
    public Text getName() {
        return Text.translatable(getTranslationKey(), StringUtils.capitalize(PioneersConfig.getTermForLivesSingular()));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!(world instanceof ServerWorld))
            return TypedActionResult.consume(stack);
        if (!PioneersConfig.ENABLE_LIFE_TOKEN) {
            user.sendMessage(Text.translatable("item.pioneers.life_token.use.failure.disabled", getName()).formatted(Formatting.RED));
            return TypedActionResult.fail(stack);
        }
        Pioneer pioneer = PioneerData.getPioneer(user);
        if (pioneer.getLivesGroup().equals(LivesGroup.GRAY)) {
            user.sendMessage(Text.translatable("item.pioneers.life_token.use.failure.gray", LivesGroup.GRAY.getDisplayName(), getName()).formatted(Formatting.RED));
            return TypedActionResult.fail(stack);
        }
        if (pioneer.getLives() == LivesGroup.getTotalLives()) {
            user.sendMessage(Text.translatable("item.pioneers.life_token.use.failure.max_lives", PioneersConfig.getTermForLivesPlural()).formatted(Formatting.RED));
            return TypedActionResult.fail(stack);
        }
        pioneer.addLives(1);
        PioneersNetworking.sendToNearbyPlayers((ServerPlayerEntity) user, new PioneerStatusPayload(user, PioneerStatus.GAINED_LIVES));
        user.sendMessage(Text.translatable("lives.gained_lives", Pioneer.getLivesText(1, Formatting.GREEN)));
        stack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(stack, false);
    }
}
