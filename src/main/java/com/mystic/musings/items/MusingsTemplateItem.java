package com.mystic.musings.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MusingsTemplateItem extends Item {
    public MusingsTemplateItem(Properties props) {
        super(props);
    }

    @Override
    public @Nullable ItemStackTemplate getCraftingRemainder(@NonNull ItemInstance instance) {
        return super.getCraftingRemainder(instance);
    }
}
