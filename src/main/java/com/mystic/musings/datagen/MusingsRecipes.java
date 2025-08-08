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

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

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

            // find the literal “ring” and “bg” indices
            int ringIdx = IntStream.range(0, parts.length)
                    .filter(i -> parts[i].equals("ring"))
                    .findFirst().orElse(-1);
            int bgIdx   = IntStream.range(0, parts.length)
                    .filter(i -> parts[i].equals("bg"))
                    .findFirst().orElse(-1);

            // sanity check
            if (ringIdx < 1 || bgIdx < ringIdx + 1) return;

            // everything between parts[1]..parts[ringIdx] is the ring color
            String ringColor = String.join("_", Arrays.copyOfRange(parts, 1, ringIdx));
            // everything between ringIdx+1..bgIdx is the bg color
            String bgColor   = String.join("_", Arrays.copyOfRange(parts, ringIdx + 1, bgIdx));

            Item ringDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(ringColor + "_dye")
            );
            Item bgDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(bgColor + "_dye")
            );

            // skip identical or missing
            if (bgDye == ringDye) return;
            if (ringDye == Items.AIR || bgDye == Items.AIR) {
                Musings.LOGGER.warn(
                        "Missing dye for pair '{}': ring '{}' or bg '{}' not found, skipping",
                        name, ringColor, bgColor
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
                    .unlockedBy("has_" + ringColor + "_dye", has(ringDye))
                    .unlockedBy("has_" + bgColor   + "_dye", has(bgDye))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Musings.MODID, name));
        });

        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();

            // split on "_" and find the two marker indices
            String[] parts = name.split("_");
            int ringIdx = IntStream.range(0, parts.length)
                    .filter(i -> parts[i].equals("ring"))
                    .findFirst().orElse(-1);
            int bgIdx   = IntStream.range(0, parts.length)
                    .filter(i -> parts[i].equals("bg"))
                    .findFirst().orElse(-1);

            if (ringIdx < 1 || bgIdx < ringIdx + 1) {
                // name didn’t match expected pattern, skip
                return;
            }

            // everything between "circle" (parts[0]) and "ring" is the ring color
            String ringColor = String.join("_", Arrays.copyOfRange(parts, 1, ringIdx));
            // everything between "ring" and "bg" is the background color
            String bgColor   = String.join("_", Arrays.copyOfRange(parts, ringIdx + 1, bgIdx));

            Item ringDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(ringColor + "_dye")
            );
            Item bgDye = BuiltInRegistries.ITEM.get(
                    ResourceLocation.withDefaultNamespace(bgColor + "_dye")
            );

            if (ringDye == Items.AIR || bgDye == Items.AIR) {
                LOGGER.warn("Missing dye for pair '{}': ring '{}' or bg '{}' not found, skipping",
                        name, ringColor, bgColor);
                return;
            }

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, resultBlock)
                    .define('R', ringDye)
                    .define('B', bgDye)
                    .define('T', Items.REDSTONE_TORCH)
                    .define('L', Items.REDSTONE_LAMP)
                    .pattern("RT")
                    .pattern("BL")
                    .unlockedBy("has_" + ringColor + "_dye", has(ringDye))
                    .unlockedBy("has_" + bgColor   + "_dye", has(bgDye))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Musings.MODID, name));
        });

        BlockInit.WOOD_INLAY_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String wood = name.substring(0, name.indexOf("_inlay"));

            Item planksItem = planksItemFor(wood);
            if (planksItem == Items.AIR) {
                Musings.LOGGER.warn("No planks found for wood '{}', skipping recipe for '{}'", wood, name);
                return;
            }

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlock)
                    .define('P', planksItem)
                    .pattern("P P")
                    .pattern(" P ")
                    .pattern("P P")
                    .unlockedBy("has_" + wood + "_planks", has(planksItem))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Musings.MODID, name + "_from_planks_recipe"));
        });

        createStonecutting(recipeOutput, BlockInit.FLOWER_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.GUIDED_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.OPTICAL_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.PETAL_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.TARGETED_STONE_BLOCK.get().asItem());
    }

    private Item planksItemFor(String woodName) {
        var rl = ResourceLocation.withDefaultNamespace(woodName + "_planks");
        var block = BuiltInRegistries.BLOCK.get(rl);
        return block == Blocks.AIR ? Items.AIR : block.asItem();
    }


    private void createStonecutting(RecipeOutput recipeOutput, Item result) {
        // All inputs are vanilla stone
        Ingredient input = Ingredient.of(Blocks.STONE);
        // The recipe category for stone variants
        RecipeCategory category = RecipeCategory.BUILDING_BLOCKS;
        // Build the recipe: 1 stone → 1 result
        SingleItemRecipeBuilder
                .stonecutting(input, category, result, 1)
                .unlockedBy("has_stone", has(Blocks.STONE))
                // Use a unique path: flower_stone_block_from_stone_stonecutting.json
                .save(recipeOutput,
                        ResourceLocation.fromNamespaceAndPath(Musings.MODID,
                                BuiltInRegistries.ITEM.getKey(result).getPath()
                                        + "_from_stone_stonecutting"));
    }
}
