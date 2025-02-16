package ayupitsali.pioneers.item;

import ayupitsali.pioneers.Pioneers;
import ayupitsali.pioneers.block.PioneersBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class PioneersItems {
    public static final Item HOLY_GOLD_INGOT = registerItem("holy_gold_ingot",
            (settings) -> new ShinyItem(true, Formatting.YELLOW, settings), new Item.Settings());
    public static final Item HOLY_DIAMOND = registerItem("holy_diamond",
            (settings) -> new ShinyItem(true, Formatting.AQUA, settings), new Item.Settings());
    public static final Item LIFE_TOKEN = registerItem("life_token",
            LifeTokenItem::new, new Item.Settings().maxCount(16));

    private static Item registerItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Pioneers.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }

    public static final ItemGroup PIONEERS_ADDITIONS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.pioneers"))
            .icon(() -> new ItemStack(PioneersItems.LIFE_TOKEN))
            .entries(((displayContext, entries) -> {
                entries.add(PioneersBlocks.MISERABLE_OBSIDIAN);
                entries.add(PioneersBlocks.LIVELY_OBSIDIAN);
                entries.add(HOLY_GOLD_INGOT);
                entries.add(HOLY_DIAMOND);
                entries.add(PioneersItems.LIFE_TOKEN);
            })).build();

    public static void registerItems() {
        Pioneers.LOGGER.info("Registering Items for " + Pioneers.MOD_ID);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(Pioneers.MOD_ID, "pioneers"), PIONEERS_ADDITIONS);
    }
}
