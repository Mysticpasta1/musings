package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import com.mystic.musings.tags.MusingsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MusingsItemTagsProvider extends ItemTagsProvider {
    public MusingsItemTagsProvider(PackOutput output,
                                   CompletableFuture<HolderLookup.Provider> lookupProvider,
                                   CompletableFuture<TagLookup<Block>> blockTagLookup,
                                   ExistingFileHelper efh) {
        super(output, lookupProvider, blockTagLookup, Musings.MODID, efh);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(MusingsTags.Items.MUSINGS_BLOCKS)
                .add(
                        BlockInit.FLOWER_STONE_BLOCK.get().asItem(),
                        BlockInit.GUIDED_STONE_BLOCK.get().asItem(),
                        BlockInit.OPTICAL_STONE_BLOCK.get().asItem(),
                        BlockInit.PETAL_STONE_BLOCK.get().asItem(),
                        BlockInit.TARGETED_STONE_BLOCK.get().asItem()
                )
                .add(BlockInit.CIRCLE_BLOCKS.values().stream().map(b -> b.get().asItem()).toArray(Item[]::new))
                .add(BlockInit.CIRCLE_FLIPS_BLOCKS.values().stream().map(b -> b.get().asItem()).toArray(Item[]::new))
                .add(BlockInit.CIRCLE_CYCLE_BLOCKS.values().stream().map(b -> b.get().asItem()).toArray(Item[]::new));
    }

    @Override
    public @NotNull String getName() {
        return "Musings Item Tags";
    }
}
