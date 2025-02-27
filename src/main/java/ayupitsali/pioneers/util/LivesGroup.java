package ayupitsali.pioneers.util;

import ayupitsali.pioneers.PioneersConfig;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum LivesGroup {
    GREEN(Math.max(PioneersConfig.GREEN_LIVES, 1), Formatting.GREEN),
    YELLOW(Math.max(PioneersConfig.YELLOW_LIVES, 1), Formatting.YELLOW),
    RED(Math.max(PioneersConfig.RED_LIVES, 1), Formatting.RED),
    GRAY(0, Formatting.GRAY);

    private final int lives;
    private final Formatting colorFormatting;

    LivesGroup(int lives, Formatting colorFormatting) {
        this.lives = lives;
        this.colorFormatting = colorFormatting;
    }

    public static LivesGroup getDefaultGroup() {
        return switch (PioneersConfig.DEFAULT_COLOR) {
            case GREEN -> GREEN;
            case YELLOW -> YELLOW;
            case RED -> RED;
        };
    }

    public int getLives() {
        return lives;
    }

    public int getMaxLives() {
        return switch (this) {
            case GREEN -> RED.getLives() + YELLOW.getLives() + GREEN.getLives();
            case YELLOW -> RED.getLives() + YELLOW.getLives();
            case RED -> RED.getLives();
            case GRAY -> 0;
        };
    }

    public int getMinLives() {
        return switch (this) {
            case GREEN -> RED.getLives() + YELLOW.getLives() + 1;
            case YELLOW -> RED.getLives() + 1;
            case RED -> 1;
            case GRAY -> 0;
        };
    }

    public static int getTotalLives() {
        return GREEN.getMaxLives();
    }

    public static LivesGroup getGroupForLives(int lives) {
        if (lives >= GREEN.getMinLives()) return GREEN;
        if (lives >= YELLOW.getMinLives()) return YELLOW;
        if (lives >= RED.getMinLives()) return RED;
        return GRAY;
    }

    public Formatting getColorFormatting() {
        return colorFormatting;
    }

    public String getName() {
        return switch (this) {
            case GREEN -> PioneersConfig.getGreenGroupName();
            case YELLOW -> PioneersConfig.getYellowGroupName();
            case RED -> PioneersConfig.getRedGroupName();
            case GRAY -> PioneersConfig.getGrayGroupName();
        };
    }

    public MutableText getDisplayName() {
        return Text.literal(getName()).formatted(colorFormatting);
    }
}
