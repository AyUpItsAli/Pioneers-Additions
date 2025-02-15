package ayupitsali.pioneers.item;

import ayupitsali.pioneers.Pioneers;
import ayupitsali.pioneers.block.PioneersBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class PioneersItems {
    public static final Item HOLY_GOLD = registerItem("holy_gold",
            new ShinyItem(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item HOLY_DIAMOND = registerItem("holy_diamond",
            new ShinyItem(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item LIFE_TOKEN = registerItem("life_token", new LifeTokenItem());

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Pioneers.MOD_ID, id), item);
    }

    public static final ItemGroup PIONEERS_ADDITIONS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.pioneers"))
            .icon(() -> new ItemStack(PioneersItems.LIFE_TOKEN))
            .entries(((displayContext, entries) -> {
                entries.add(PioneersBlocks.DEPRESSED_OBSIDIAN);
                entries.add(PioneersBlocks.LIVELY_OBSIDIAN);
                entries.add(HOLY_GOLD);
                entries.add(HOLY_DIAMOND);
                entries.add(PioneersItems.LIFE_TOKEN);
            })).build();

    public static void registerItems() {
        Pioneers.LOGGER.info("Registering Items for " + Pioneers.MOD_ID);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(Pioneers.MOD_ID, "pioneers"), PIONEERS_ADDITIONS);
    }
}
