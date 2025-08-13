package com.mystic.musings.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MusingsTemplateItem extends Item {
    public MusingsTemplateItem(Properties props) {
        super(props);
    }

    @Override
    public boolean hasCraftingRemainingItem(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(@NotNull ItemStack stack) {
        ItemStack remainder = stack.copyWithCount(1);
        int damage = remainder.getDamageValue() + 1;
        int max = remainder.getMaxDamage();

        if (max <= 0) {
            return remainder;
        }

        if (damage >= max) {
            return ItemStack.EMPTY;
        }

        remainder.setDamageValue(damage);
        return remainder;
    }
}
