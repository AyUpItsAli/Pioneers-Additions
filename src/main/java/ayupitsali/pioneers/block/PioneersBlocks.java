package ayupitsali.pioneers.block;

import ayupitsali.pioneers.Pioneers;
import ayupitsali.pioneers.item.ShinyBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class PioneersBlocks {
    public static final Block MISERABLE_OBSIDIAN = registerBlock("miserable_obsidian",
            new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)),
            (block) -> new BlockItem(block, new Item.Settings()));
    public static final Block LIVELY_OBSIDIAN = registerBlock("lively_obsidian",
            new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)),
            (block) -> new ShinyBlockItem(false, Formatting.RED, block, new Item.Settings()));

    private static Block registerBlock(String id, Block block, Function<Block, BlockItem> blockItem) {
        Registry.register(Registries.ITEM, Identifier.of(Pioneers.MOD_ID, id), blockItem.apply(block));
        return Registry.register(Registries.BLOCK, Identifier.of(Pioneers.MOD_ID, id), block);
    }

    public static void registerBlocks() {
        Pioneers.LOGGER.info("Registering Blocks for " + Pioneers.MOD_ID);
    }
}
