package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import com.mystic.musings.tags.MusingsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MusingsBlockTagsProvider extends BlockTagsProvider {
    public MusingsBlockTagsProvider(PackOutput output,
                                    CompletableFuture<HolderLookup.Provider> lookupProvider,
                                    ExistingFileHelper efh) {
        super(output, lookupProvider, Musings.MODID, efh);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        BlockInit.FLOWER_STONE_BLOCK.get(),
                        BlockInit.GUIDED_STONE_BLOCK.get(),
                        BlockInit.OPTICAL_STONE_BLOCK.get(),
                        BlockInit.PETAL_STONE_BLOCK.get(),
                        BlockInit.TARGETED_STONE_BLOCK.get()
                )
                .add(BlockInit.INK_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new))
                .add(BlockInit.CIRCLE_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new))
                .add(BlockInit.CIRCLE_FLIPS_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new))
                .add(BlockInit.CIRCLE_CYCLE_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new));

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlockInit.WOOD_INLAY_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new));

        tag(MusingsTags.Blocks.MUSINGS_BLOCKS)
                .add(
                        BlockInit.FLOWER_STONE_BLOCK.get(),
                        BlockInit.GUIDED_STONE_BLOCK.get(),
                        BlockInit.OPTICAL_STONE_BLOCK.get(),
                        BlockInit.PETAL_STONE_BLOCK.get(),
                        BlockInit.TARGETED_STONE_BLOCK.get()
                )
                .add(BlockInit.CIRCLE_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new))
                .add(BlockInit.CIRCLE_FLIPS_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new))
                .add(BlockInit.CIRCLE_CYCLE_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new))
                .add(BlockInit.WOOD_INLAY_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new));

        tag(MusingsTags.Blocks.WOOD_INLAYS)
                .add(BlockInit.WOOD_INLAY_BLOCKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new));
    }

    @Override
    public @NotNull String getName() {
        return "Musings Block Tags";
    }
}
