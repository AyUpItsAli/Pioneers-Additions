package ayupitsali.pioneers.data;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class PioneerData implements Component {
    private LivesGroup livesGroup = LivesGroup.GREEN;
    private int lives = livesGroup.getMaxLives();

    public LivesGroup getLivesGroup() {
        return livesGroup;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = Math.min(Math.max(lives, 0), LivesGroup.GREEN.getMaxLives());
        if (this.lives >= LivesGroup.GREEN.getMinLives()) {
            livesGroup = LivesGroup.GREEN;
        } else if (this.lives >= LivesGroup.YELLOW.getMinLives()) {
            livesGroup = LivesGroup.YELLOW;
        } else if (this.lives >= LivesGroup.RED.getMinLives()) {
            livesGroup = LivesGroup.RED;
        } else {
            livesGroup = LivesGroup.GHOST;
        }
    }

    public void addLives(int amount) {
        setLives(lives + amount);
    }

    public MutableText getLivesDisplay() {
        return Text.literal(Integer.toString(lives)).formatted(livesGroup.getColourFormatting());
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        lives = tag.getInt("lives");
        livesGroup = LivesGroup.valueOf(tag.getString("livesGroup"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("lives", lives);
        tag.putString("livesGroup", livesGroup.toString());
    }
}
