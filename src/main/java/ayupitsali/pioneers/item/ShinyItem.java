package ayupitsali.pioneers.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ShinyItem extends Item {
    protected final boolean hasGlint;
    protected final Formatting formatting;

    public ShinyItem(boolean hasGlint, Formatting formatting, Settings settings) {
        super(settings);
        this.hasGlint = hasGlint;
        this.formatting = formatting;
    }

    protected Text getUnformattedName() {
        return getName();
    }

    @Override
    public Text getName(ItemStack stack) {
        return getUnformattedName().copy().formatted(formatting);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return hasGlint;
    }
}
