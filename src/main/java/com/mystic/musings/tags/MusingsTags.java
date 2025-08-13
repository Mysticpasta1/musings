package com.mystic.musings.tags;

import com.mystic.musings.Musings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MusingsTags {
    public static class Items {
        public static final TagKey<Item> MUSINGS_ITEMS = TagKey.create(
                net.minecraft.core.registries.Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Musings.MODID, "musings_items")
        );

        // NEW: all wood inlay items
        public static final TagKey<Item> WOOD_INLAYS = TagKey.create(
                net.minecraft.core.registries.Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Musings.MODID, "wood_inlays")
        );
    }

    public static class Blocks {
        public static final TagKey<Block> MUSINGS_BLOCKS = TagKey.create(
                net.minecraft.core.registries.Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(Musings.MODID, "musings_blocks")
        );

        // NEW: all wood inlay blocks
        public static final TagKey<Block> WOOD_INLAYS = TagKey.create(
                net.minecraft.core.registries.Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(Musings.MODID, "wood_inlays")
        );
    }
}

