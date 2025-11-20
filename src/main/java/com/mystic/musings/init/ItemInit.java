package com.mystic.musings.init;

import com.mystic.musings.Musings;
import com.mystic.musings.items.MusingsTemplateItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Musings.MODID);

    public static final RegistryObject<Item> MUSINGS_TEMPLATE =
            register("musings_template", () ->
                    new MusingsTemplateItem(new Item.Properties()
                            .stacksTo(1)
                            .durability(256)
                    )
            );

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        var register = ITEMS.register(name, item);
        CreativeMenuInit.addToMainTabItems(register);
        return register;
    }

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
