package ayupitsali.pioneers.command;

import ayupitsali.pioneers.Pioneers;
import ayupitsali.pioneers.PioneersConfig;
import ayupitsali.pioneers.network.PioneerStatusPayload;
import ayupitsali.pioneers.util.LivesGroup;
import ayupitsali.pioneers.data.Pioneer;
import ayupitsali.pioneers.data.PioneerData;
import ayupitsali.pioneers.util.PioneerStatus;
import ayupitsali.pioneers.util.PioneersUtils;
import ayupitsali.pioneers.network.PioneersNetworking;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LivesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("lives").executes(LivesCommand::executeLives)
                .then(CommandManager.literal("list").executes(LivesCommand::executeList))
                .then(CommandManager.literal("set").requires(source -> source.hasPermissionLevel(2)).then(CommandManager.argument("player", GameProfileArgumentType.gameProfile()).suggests((context, builder) ->
                        CommandSource.suggestMatching(Pioneers.PIONEER_DATA.get(context.getSource().getWorld().getScoreboard()).getPioneers().stream().map(Pioneer::getName), builder)
                ).then(CommandManager.argument("lives", IntegerArgumentType.integer(0, LivesGroup.getTotalLives())).executes(context ->
                        executeSet(context, GameProfileArgumentType.getProfileArgument(context, "player").iterator().next(), IntegerArgumentType.getInteger(context, "lives"))
                ))))
                .then(CommandManager.literal("reset").requires(source -> source.hasPermissionLevel(2)).executes(context ->
                        executeReset(context, LivesGroup.getDefaultGroup().getMaxLives())
                ).then(CommandManager.argument("lives", IntegerArgumentType.integer(0, LivesGroup.getTotalLives())).executes(context ->
                        executeReset(context, IntegerArgumentType.getInteger(context, "lives"))
                )))
                .then(CommandManager.literal("give").then(CommandManager.argument("player", GameProfileArgumentType.gameProfile()).suggests((context, builder) ->
                        CommandSource.suggestMatching(Pioneers.PIONEER_DATA.get(context.getSource().getWorld().getScoreboard()).getPioneers().stream().map(Pioneer::getName), builder)
                ).then(CommandManager.argument("lives", IntegerArgumentType.integer(1, LivesGroup.getTotalLives())).executes(context ->
                        executeGive(context, GameProfileArgumentType.getProfileArgument(context, "player").iterator().next(), IntegerArgumentType.getInteger(context, "lives"))
                ))))
        );
    }

    public static int executeLives(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Pioneer pioneer = PioneerData.getPioneer(context.getSource().getPlayerOrThrow());
        context.getSource().sendFeedback(() -> Text.translatable("commands.lives.success", pioneer.getLivesDisplay()), false);
        return 1;
    }

    public static int executeList(CommandContext<ServerCommandSource> context) {
        Collection<Pioneer> pioneers = Pioneers.PIONEER_DATA.get(context.getSource().getWorld().getScoreboard()).getPioneers();
        Arrays.stream(LivesGroup.values()).forEach(livesGroup -> {
            List<Pioneer> groupPioneers = pioneers.stream().filter(pioneer -> pioneer.getLivesGroup().equals(livesGroup)).toList();
            context.getSource().sendFeedback(() -> livesGroup.getDisplayName().formatted(Formatting.BOLD), false);
            if (groupPioneers.isEmpty()) {
                context.getSource().sendFeedback(() -> Text.translatable("commands.lives.list.success.item", Text.translatable("commands.lives.list.success.item.empty").formatted(Formatting.GRAY).formatted(Formatting.ITALIC)), false);
            } else if (livesGroup.equals(LivesGroup.GHOST)) {
                groupPioneers.forEach(pioneer -> context.getSource().sendFeedback(() -> Text.translatable("commands.lives.list.success.item", pioneer.getDisplayName()), false));
            } else {
                groupPioneers.forEach(pioneer -> context.getSource().sendFeedback(() -> Text.translatable("commands.lives.list.success.item", Text.translatable("commands.lives.list.success.item.player", pioneer.getDisplayName(), pioneer.getLivesDisplay())), false));
            }
        });
        return 1;
    }

    public static int executeSet(CommandContext<ServerCommandSource> context, GameProfile profile, int lives) throws CommandSyntaxException {
        PioneerData pioneersData = Pioneers.PIONEER_DATA.get(context.getSource().getWorld().getScoreboard());
        if (!pioneersData.pioneerExists(profile)) {
            String message = Text.translatable("commands.failure.player_not_found", PioneersConfig.getTermForPlayersSingular()).getString();
            context.getSource().sendError(Text.literal(StringUtils.capitalize(message)));
            return 0;
        }
        Pioneer pioneer = pioneersData.getPioneer(profile);
        pioneer.setLives(lives);
        if (context.getSource().isExecutedByPlayer() && context.getSource().getPlayerOrThrow().getGameProfile().equals(profile))
            context.getSource().sendFeedback(() -> Text.translatable("commands.lives.set.success.self", pioneer.getLivesDisplay()), false);
        else
            context.getSource().sendFeedback(() -> Text.translatable("commands.lives.set.success.other", pioneer.getDisplayName(), pioneer.getLivesDisplay()), false);
        return 1;
    }

    public static int executeReset(CommandContext<ServerCommandSource> context, int lives) {
        Pioneers.PIONEER_DATA.get(context.getSource().getWorld().getScoreboard()).getPioneers().forEach(pioneer -> pioneer.setLives(lives));
        Formatting formatting = LivesGroup.getGroupForLives(lives).getColourFormatting();
        context.getSource().sendFeedback(() -> Text.translatable("commands.lives.reset.success", PioneersConfig.getTermForPlayersPlural(), Pioneer.getLivesText(lives, formatting)), false);
        return 1;
    }

    public static int executeGive(CommandContext<ServerCommandSource> context, GameProfile profile, int lives) throws CommandSyntaxException {
        if (!PioneersConfig.ENABLE_GIVE_COMMAND) {
            context.getSource().sendError(Text.translatable("commands.lives.give.failure.disabled"));
            return 0;
        }
        ServerWorld world = context.getSource().getWorld();
        PlayerEntity player = context.getSource().getPlayerOrThrow();
        PioneerData pioneersData = Pioneers.PIONEER_DATA.get(world.getScoreboard());
        Pioneer pioneer = pioneersData.getPioneer(player.getGameProfile());
        if (pioneer.getLivesGroup().equals(LivesGroup.GHOST)) {
            context.getSource().sendError(Text.translatable("commands.lives.give.failure.no_lives", PioneersConfig.getTermForLivesPlural()));
            return 0;
        }
        if (!pioneersData.pioneerExists(profile)) {
            String message = Text.translatable("commands.failure.player_not_found", PioneersConfig.getTermForPlayersSingular()).getString();
            context.getSource().sendError(Text.literal(StringUtils.capitalize(message)));
            return 0;
        }
        Pioneer other = pioneersData.getPioneer(profile);
        if (pioneer.equals(other)) {
            context.getSource().sendError(Text.translatable("commands.lives.give.failure.give_self", PioneersConfig.getTermForLivesPlural()));
            return 0;
        }
        if (other.getLivesGroup().equals(LivesGroup.GHOST)) {
            context.getSource().sendError(Text.translatable("commands.lives.give.failure.give_ghost", PioneersConfig.getTermForLivesPlural(), LivesGroup.GHOST.getDisplayName()));
            return 0;
        }
        if (other.getLives() == LivesGroup.getTotalLives()) {
            context.getSource().sendError(Text.translatable("commands.lives.give.failure.max_lives", other.getDisplayName(), PioneersConfig.getTermForLivesPlural()));
            return 0;
        }
        int livesGiven = Math.min(Math.min(lives, pioneer.getLives()), LivesGroup.getTotalLives() - other.getLives());
        other.addLives(livesGiven);
        PlayerEntity otherPlayer = world.getPlayerByUuid(profile.getId());
        if (otherPlayer != null) {
            PioneersNetworking.sendToNearbyPlayers((ServerPlayerEntity) otherPlayer, new PioneerStatusPayload(otherPlayer, PioneerStatus.GAINED_LIVES));
            otherPlayer.sendMessage(Text.translatable("lives.lives_received", player.getDisplayName(), Pioneer.getLivesText(livesGiven, Formatting.GREEN)));
        }
        context.getSource().sendFeedback(() -> Text.translatable("commands.lives.give.success", Pioneer.getLivesText(livesGiven, Formatting.GREEN), other.getDisplayName()), false);
        if (pioneer.addLives(-livesGiven) == 0)
            PioneersUtils.handleOutOfLives(player);
        return 1;
    }
}
