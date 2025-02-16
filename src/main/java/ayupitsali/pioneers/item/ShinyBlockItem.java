package ayupitsali.pioneers.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ShinyBlockItem extends BlockItem {
    protected final boolean hasGlint;
    protected final Formatting formatting;

    public ShinyBlockItem(boolean hasGlint, Formatting formatting, Block block, Settings settings) {
        super(block, settings);
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
