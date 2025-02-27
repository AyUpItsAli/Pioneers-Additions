package ayupitsali.pioneers.data;

import ayupitsali.pioneers.PioneersConfig;
import ayupitsali.pioneers.util.LivesGroup;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;

public class Pioneer {
    private final PioneerData component;
    private final String name;
    private int lives;
    private LivesGroup livesGroup;

    public Pioneer(PioneerData component, String name, int lives) {
        this.component = component;
        this.name = name;
        setLives(lives);
    }

    public Pioneer(PioneerData component, String name) {
        this(component, name, LivesGroup.getDefaultGroup().getMaxLives());
    }

    public String getName() {
        return name;
    }

    public MutableText getDisplayName() {
        return Text.literal(name).formatted(livesGroup.getColorFormatting());
    }

    public int getLives() {
        return lives;
    }

    public int setLives(int lives) {
        this.lives = Math.min(Math.max(lives, 0), LivesGroup.getTotalLives());
        livesGroup = LivesGroup.getGroupForLives(this.lives);
        component.sync();
        return this.lives;
    }

    public int addLives(int amount) {
        return setLives(lives + amount);
    }

    public LivesGroup getLivesGroup() {
        return livesGroup;
    }

    public MutableText getLivesDisplay() {
        return Pioneer.getLivesText(lives, livesGroup.getColorFormatting());
    }

    public static MutableText getLivesText(int lives, Formatting livesFormatting) {
        MutableText livesText = MutableText.of(TextContent.EMPTY).append(Text.literal(Integer.toString(lives)).formatted(livesFormatting));
        if (lives == 1)
            return livesText.append(Text.literal(" " + PioneersConfig.getTermForLivesSingular()));
        else
            return livesText.append(Text.literal(" " + PioneersConfig.getTermForLivesPlural()));
    }

    public boolean shouldGainLivesFromKill(Pioneer killed) {
        return switch (livesGroup) {
            case GREEN, GRAY -> false;
            case YELLOW -> killed.getLivesGroup().equals(LivesGroup.GREEN);
            case RED -> killed.getLivesGroup().equals(LivesGroup.YELLOW) || killed.getLivesGroup().equals(LivesGroup.GREEN);
        };
    }
}
