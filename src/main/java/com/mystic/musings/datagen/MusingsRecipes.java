package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MusingsRecipes extends RecipeProvider {
    public MusingsRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput, HolderLookup.@NotNull Provider holderLookup) {
        BlockInit.CIRCLE_CYCLE_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String color = name.substring("circle_cycle_".length());
            Item dye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(color + "_dye")
            );

            ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, resultBlock, 1)
                    .requires(Items.REDSTONE_LAMP)
                    .requires(dye)
                    .requires(Items.REDSTONE)
                    .unlockedBy("has_" + color + "_dye", has(dye))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Musings.MODID, name));
        });

        BlockInit.CIRCLE_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String[] parts = name.split("_");
            String ring = parts[1];
            String bg = parts[3];

            Item ringDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(ring + "_dye")
            );
            Item bgDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(bg + "_dye")
            );

            if (bgDye == ringDye) {
                return;
            }

            if (ringDye == Items.AIR || bgDye == Items.AIR) {
                Musings.LOGGER.warn(
                        "Missing dye for pair '{}': ring '{}' or bg '{}' not found, skipping",
                        name, ring, bg
                );
                return;
            }

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, resultBlock, 1)
                    .define('R', ringDye)
                    .define('B', bgDye)
                    .define('S', Items.REDSTONE)
                    .define('L', Items.REDSTONE_LAMP)
                    .pattern("RS")
                    .pattern("BL")
                    .unlockedBy("has_" + ring + "_dye", has(ringDye))
                    .unlockedBy("has_" + bg + "_dye", has(bgDye))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Musings.MODID, name));
        });




        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String[] parts = name.split("_");
            String ring = parts[1];
            String bg = parts[3];

            Item ringDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(ring + "_dye")
            );
            Item bgDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(bg + "_dye")
            );

            if (bgDye == ringDye) {
                return;
            }

            if (ringDye == Items.AIR || bgDye == Items.AIR) {
                Musings.LOGGER.warn(
                        "Missing dye for pair '{}': ring '{}' or bg '{}' not found, skipping",
                        name, ring, bg
                );
                return;
            }

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, resultBlock, 1)
                    .define('R', ringDye)
                    .define('B', bgDye)
                    .define('T', Items.REDSTONE_TORCH)
                    .define('L', Items.REDSTONE_LAMP)
                    .pattern("RT")
                    .pattern("BL")
                    .unlockedBy("has_" + ring + "_dye", has(ringDye))
                    .unlockedBy("has_" + bg + "_dye", has(bgDye))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Musings.MODID, name));
        });

        createStonecutting(recipeOutput, BlockInit.FLOWER_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.GUIDED_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.OPTICAL_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.PETAL_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.TARGETED_STONE_BLOCK.get().asItem());
    }

    private void createStonecutting(RecipeOutput recipeOutput, Item result) {
        // All inputs are vanilla stone
        Ingredient input = Ingredient.of(Blocks.STONE);
        // The recipe category for stone variants
        RecipeCategory category = RecipeCategory.BUILDING_BLOCKS;
        // Build the recipe: 1 stone → 1 result
        SingleItemRecipeBuilder
                .stonecutting(input, category, result, 1)
                .unlockedBy("has_stone", this.has(Blocks.STONE))
                // Use a unique path: flower_stone_block_from_stone_stonecutting.json
                .save(recipeOutput,
                        ResourceLocation.fromNamespaceAndPath(Musings.MODID,
                                BuiltInRegistries.ITEM.getKey(result).getPath()
                                        + "_from_stone_stonecutting"));
    }
}
