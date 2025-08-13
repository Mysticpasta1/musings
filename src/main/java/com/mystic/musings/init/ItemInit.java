package com.mystic.musings.init;

import com.mystic.musings.Musings;
import com.mystic.musings.items.MusingsTemplateItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Musings.MODID);

    public static final DeferredItem<Item> MUSINGS_TEMPLATE =
            register("musings_template", () ->
                    new MusingsTemplateItem(new Item.Properties()
                            .stacksTo(1)
                            .durability(256)
                    )
            );

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        var register = ITEMS.register(name, item);
        CreativeMenuInit.addToMainTabItems(register);
        return register;
    }

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
