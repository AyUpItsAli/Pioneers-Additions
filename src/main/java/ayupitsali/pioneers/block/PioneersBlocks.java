package ayupitsali.pioneers.block;

import ayupitsali.pioneers.Pioneers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class PioneersBlocks {
    public static final Block DEPRESSED_OBSIDIAN = registerBlock("depressed_obsidian",
            new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)),
            new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Block LIVELY_OBSIDIAN = registerBlock("lively_obsidian",
            new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)),
            new Item.Settings().rarity(Rarity.RARE));

    private static Block registerBlock(String id, Block block, Item.Settings itemSettings) {
        registerBlockItem(id, block, itemSettings);
        return Registry.register(Registries.BLOCK, Identifier.of(Pioneers.MOD_ID, id), block);
    }

    private static void registerBlockItem(String id, Block block, Item.Settings itemSettings) {
        Registry.register(Registries.ITEM, Identifier.of(Pioneers.MOD_ID, id), new BlockItem(block, itemSettings));
    }

    public static void registerBlocks() {
        Pioneers.LOGGER.info("Registering Blocks for " + Pioneers.MOD_ID);
    }
}
