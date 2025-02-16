package ayupitsali.pioneers.block;

import ayupitsali.pioneers.Pioneers;
import ayupitsali.pioneers.item.ShinyBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

public class PioneersBlocks {
    public static final Block MISERABLE_OBSIDIAN = registerBlock("miserable_obsidian",
            Block::new, AbstractBlock.Settings.copy(Blocks.OBSIDIAN),
            BlockItem::new, new Item.Settings());
    public static final Block LIVELY_OBSIDIAN = registerBlock("lively_obsidian",
            Block::new, AbstractBlock.Settings.copy(Blocks.OBSIDIAN),
            (block, settings) -> new ShinyBlockItem(false, Formatting.RED, block, settings), new Item.Settings());

    private static Block registerBlock(String path, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings,
                                       BiFunction<Block, Item.Settings, Item> itemFactory, Item.Settings itemSettings) {
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Pioneers.MOD_ID, path));
        Block block = Blocks.register(blockKey, blockFactory, blockSettings);
        Items.register(block, itemFactory, itemSettings);
        return block;
    }

    public static void registerBlocks() {
        Pioneers.LOGGER.info("Registering Blocks for " + Pioneers.MOD_ID);
    }
}
