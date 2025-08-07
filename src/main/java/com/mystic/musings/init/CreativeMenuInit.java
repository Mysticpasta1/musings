package com.mystic.musings.init;

import com.mystic.musings.Musings;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CreativeMenuInit {
    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Musings.MODID);

    public static final List<Supplier<? extends ItemLike>> MAIN_BLOCKS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> MAIN_ITEMS = new ArrayList<>();

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.musings.general"))
            .icon(() -> {
                if (MAIN_BLOCKS.isEmpty()) {
                    return new ItemStack(Items.BARRIER);
                }
                int idx = (int)((System.currentTimeMillis() / 1000L) % MAIN_BLOCKS.size());
                ItemLike block = MAIN_BLOCKS.get(idx).get();
                return new ItemStack(block);
            })
            .displayItems((pParameters, pOutput) -> {
                    MAIN_BLOCKS.forEach(itemLike -> pOutput.accept(itemLike.get()));
                    MAIN_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get()));
            })
            .build());

    public static <T extends Item> DeferredItem<T> addToMainTab (DeferredItem<T> itemLike) {
        MAIN_BLOCKS.add(itemLike);
        return itemLike;
    }

    public static <T extends Item> DeferredItem<T> addToMainTabItems (DeferredItem<T> itemLike) {
        MAIN_ITEMS.add(itemLike);
        return itemLike;
    }

    public static void init(IEventBus bus){
        CREATIVE_TABS.register(bus);
    }
}
