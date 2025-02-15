package ayupitsali.pioneers.data;

import ayupitsali.pioneers.PioneersConfig;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum LivesGroup {
    GREEN(Math.max(PioneersConfig.GREEN_LIVES, 1), Formatting.GREEN),
    YELLOW(Math.max(PioneersConfig.YELLOW_LIVES, 1), Formatting.YELLOW),
    RED(Math.max(PioneersConfig.RED_LIVES, 1), Formatting.RED),
    GHOST(0, Formatting.GRAY);

    private final int lives;
    private final Formatting colourFormatting;

    LivesGroup(int lives, Formatting colourFormatting) {
        this.lives = lives;
        this.colourFormatting = colourFormatting;
    }

    public static LivesGroup getDefaultGroup() {
        return switch (PioneersConfig.DEFAULT_COLOUR) {
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
            case GHOST -> 0;
        };
    }

    public int getMinLives() {
        return switch (this) {
            case GREEN -> RED.getLives() + YELLOW.getLives() + 1;
            case YELLOW -> RED.getLives() + 1;
            case RED -> 1;
            case GHOST -> 0;
        };
    }

    public static int getTotalLives() {
        return GREEN.getMaxLives();
    }

    public static LivesGroup getGroupForLives(int lives) {
        if (lives >= GREEN.getMinLives()) return GREEN;
        if (lives >= YELLOW.getMinLives()) return YELLOW;
        if (lives >= RED.getMinLives()) return RED;
        return GHOST;
    }

    public Formatting getColourFormatting() {
        return colourFormatting;
    }

    public String getName() {
        return switch (this) {
            case GREEN -> PioneersConfig.getGreenGroupName();
            case YELLOW -> PioneersConfig.getYellowGroupName();
            case RED -> PioneersConfig.getRedGroupName();
            case GHOST -> PioneersConfig.getGhostGroupName();
        };
    }

    public MutableText getDisplayName() {
        return Text.literal(getName()).formatted(colourFormatting);
    }
}
