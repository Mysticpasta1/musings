package com.mystic.musings.datagen;

import com.mystic.musings.init.BlockInit;
import com.mystic.musings.init.ItemInit;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import org.jspecify.annotations.NonNull;

public class MusingsModelProvider extends ModelProvider {
    private static final String[] DYE_NAMES = {
            "white","orange","magenta","light_blue","yellow",
            "lime","pink","gray","light_gray","cyan",
            "purple","blue","brown","green","red","black"
    };

    // Wood types for inlays
    private static final String[] WOOD_TYPES = {
            "oak","spruce","birch","jungle","acacia",
            "dark_oak","mangrove","cherry","bamboo","crimson","warped"
    };

    public MusingsModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        // Register block states and models
        registerBlockStates(blockModels);
        registerItemModels(itemModels);
    }

    private void registerItemModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ItemInit.MUSINGS_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);
    };

    private void registerBlockStates(BlockModelGenerators blockModels) {
        BlockInit.CIRCLE_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialCube(holder.get());
            blockModels.registerSimpleFlatItemModel(holder.get());
        });

        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialCube(holder.get());
            blockModels.registerSimpleFlatItemModel(holder.get());
        });

        BlockInit.CIRCLE_CYCLE_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialCube(holder.get());
            blockModels.registerSimpleFlatItemModel(holder.get());
        });

        BlockInit.INK_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialCube(holder.get());
            blockModels.registerSimpleFlatItemModel(holder.get());
        });

        BlockInit.WOOD_INLAY_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialCube(holder.get());
            blockModels.registerSimpleFlatItemModel(holder.get());
        });

        blockModels.createTrivialCube(BlockInit.FLOWER_STONE_BLOCK.get());
        blockModels.registerSimpleFlatItemModel(BlockInit.FLOWER_STONE_BLOCK.get());
        
        blockModels.createTrivialCube(BlockInit.GUIDED_STONE_BLOCK.get());
        blockModels.registerSimpleFlatItemModel(BlockInit.GUIDED_STONE_BLOCK.get());
        
        blockModels.createTrivialCube(BlockInit.OPTICAL_STONE_BLOCK.get());
        blockModels.registerSimpleFlatItemModel(BlockInit.OPTICAL_STONE_BLOCK.get());
        
        blockModels.createTrivialCube(BlockInit.PETAL_STONE_BLOCK.get());
        blockModels.registerSimpleFlatItemModel(BlockInit.PETAL_STONE_BLOCK.get());
        
        blockModels.createTrivialCube(BlockInit.TARGETED_STONE_BLOCK.get());
        blockModels.registerSimpleFlatItemModel(BlockInit.TARGETED_STONE_BLOCK.get());
    }
}
