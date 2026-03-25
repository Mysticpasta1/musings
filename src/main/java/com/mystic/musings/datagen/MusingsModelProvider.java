package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import com.mystic.musings.init.ItemInit;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class MusingsModelProvider extends ModelProvider {
    public MusingsModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        registerBlockStates(blockModels);
        registerItemModels(itemModels);
    }

    private void registerItemModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ItemInit.MUSINGS_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);
    }

    private void registerBlockStates(BlockModelGenerators blockModels) {

        BlockInit.CIRCLE_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialBlock(holder.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/circle_pairs/" + name)));
            blockModels.createFlatItemModelWithBlockTexture(holder.get().asItem(), holder.get(), "");
        });

        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialBlock(holder.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/circle_flips/" + name)));
            blockModels.createFlatItemModelWithBlockTexture(holder.get().asItem(), holder.get(), "");
        });

        BlockInit.CIRCLE_CYCLE_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialBlock(holder.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/circle_cycles/" + name)));
            blockModels.createFlatItemModelWithBlockTexture(holder.get().asItem(), holder.get(), "");
        });

        BlockInit.INK_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialBlock(holder.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/ink_blocks/" + name)));
            blockModels.createFlatItemModelWithBlockTexture(holder.get().asItem(), holder.get(), "");
        });

        BlockInit.WOOD_INLAY_BLOCKS.forEach((name, holder) -> {
            blockModels.createTrivialBlock(holder.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/wooden_inlays/" + name)));
            blockModels.createFlatItemModelWithBlockTexture(holder.get().asItem(), holder.get(), "");
        });

        blockModels.createTrivialBlock(BlockInit.FLOWER_STONE_BLOCK.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/stone_carvings/flower_stone")));
        blockModels.createFlatItemModelWithBlockTexture(BlockInit.FLOWER_STONE_BLOCK.get().asItem(), BlockInit.FLOWER_STONE_BLOCK.get(), "");

        blockModels.createTrivialBlock(BlockInit.GUIDED_STONE_BLOCK.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/stone_carvings/guided_stone")));
        blockModels.createFlatItemModelWithBlockTexture(BlockInit.GUIDED_STONE_BLOCK.get().asItem(), BlockInit.GUIDED_STONE_BLOCK.get(), "");

        blockModels.createTrivialBlock(BlockInit.OPTICAL_STONE_BLOCK.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/stone_carvings/optical_stone")));
        blockModels.createFlatItemModelWithBlockTexture(BlockInit.OPTICAL_STONE_BLOCK.get().asItem(), BlockInit.OPTICAL_STONE_BLOCK.get(), "");

        blockModels.createTrivialBlock(BlockInit.PETAL_STONE_BLOCK.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/stone_carvings/petal_stone")));
        blockModels.createFlatItemModelWithBlockTexture(BlockInit.PETAL_STONE_BLOCK.get().asItem(), BlockInit.PETAL_STONE_BLOCK.get(), "");

        blockModels.createTrivialBlock(BlockInit.TARGETED_STONE_BLOCK.get(), block -> getTextureModel(Identifier.fromNamespaceAndPath(Musings.MODID, "block/stone_carvings/targeted_stone")));
        blockModels.createFlatItemModelWithBlockTexture(BlockInit.TARGETED_STONE_BLOCK.get().asItem(), BlockInit.TARGETED_STONE_BLOCK.get(), "");
    }

    private TexturedModel getTextureModel(Identifier id) {
        return TexturedModel.createAllSame(new Material(id));
    }
}
