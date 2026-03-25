package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import com.mystic.musings.init.ItemInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.mystic.musings.Musings.LOGGER;

public class MusingsRecipeProvider extends RecipeProvider {
    private HolderLookup.Provider provider;
    private RecipeOutput recipeOutput;

    public MusingsRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
        this.provider = provider;
        this.recipeOutput = recipeOutput;
    }

    @Override
    protected void buildRecipes() {
        BlockInit.CIRCLE_CYCLE_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String color = name.substring("circle_cycle_".length());
            Item dye = BuiltInRegistries.ITEM.get(Identifier.withDefaultNamespace(color + "_dye")).orElseThrow().value();

            ShapelessRecipeBuilder.shapeless(provider.lookupOrThrow(BuiltInRegistries.ITEM.key()), RecipeCategory.DECORATIONS, resultBlock, 1)
                    .requires(Items.REDSTONE_LAMP)
                    .requires(dye)
                    .requires(Items.REDSTONE)
                    .requires(ItemInit.MUSINGS_TEMPLATE.get())
                    .unlockedBy("has_" + color + "_dye", has(dye))
                    .unlockedBy("has_musings_template", has(ItemInit.MUSINGS_TEMPLATE.get()))
                    .save(recipeOutput, Musings.MODID + ":" + name);
        });

        BlockInit.CIRCLE_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String[] parts = name.split("_");

            int ringIdx = IntStream.range(0, parts.length).filter(i -> parts[i].equals("ring")).findFirst().orElse(-1);
            int bgIdx   = IntStream.range(0, parts.length).filter(i -> parts[i].equals("bg")).findFirst().orElse(-1);
            if (ringIdx < 1 || bgIdx < ringIdx + 1) return;

            String ringColor = String.join("_", Arrays.copyOfRange(parts, 1, ringIdx));
            String bgColor   = String.join("_", Arrays.copyOfRange(parts, ringIdx + 1, bgIdx));

            Item ringDye = BuiltInRegistries.ITEM.get(Identifier.withDefaultNamespace(ringColor + "_dye")).orElseThrow().value();
            Item bgDye   = BuiltInRegistries.ITEM.get(Identifier.withDefaultNamespace(bgColor + "_dye")).orElseThrow().value();

            if (bgDye == ringDye) return;
            if (ringDye == Items.AIR || bgDye == Items.AIR) {
                logMissingDye(name, ringColor, bgColor);
                return;
            }

            ShapedRecipeBuilder.shaped(provider.lookupOrThrow(BuiltInRegistries.ITEM.key()), RecipeCategory.DECORATIONS, resultBlock, 1)
                    .define('R', ringDye)
                    .define('B', bgDye)
                    .define('S', Items.REDSTONE)
                    .define('L', Items.REDSTONE_LAMP)
                    .define('M', ItemInit.MUSINGS_TEMPLATE.get())
                    .pattern("RS ")
                    .pattern("BLM")
                    .unlockedBy("has_" + ringColor + "_dye", has(ringDye))
                    .unlockedBy("has_" + bgColor   + "_dye", has(bgDye))
                    .unlockedBy("has_musings_template", has(ItemInit.MUSINGS_TEMPLATE.get()))
                    .save(recipeOutput, Musings.MODID + ":" + name);
        });

        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String[] parts = name.split("_");

            int ringIdx = IntStream.range(0, parts.length).filter(i -> parts[i].equals("ring")).findFirst().orElse(-1);
            int bgIdx   = IntStream.range(0, parts.length).filter(i -> parts[i].equals("bg")).findFirst().orElse(-1);
            if (ringIdx < 1 || bgIdx < ringIdx + 1) return;

            String ringColor = String.join("_", Arrays.copyOfRange(parts, 1, ringIdx));
            String bgColor   = String.join("_", Arrays.copyOfRange(parts, ringIdx + 1, bgIdx));

            Item ringDye = BuiltInRegistries.ITEM.get(Identifier.withDefaultNamespace(ringColor + "_dye")).orElseThrow().value();
            Item bgDye   = BuiltInRegistries.ITEM.get(Identifier.withDefaultNamespace(bgColor + "_dye")).orElseThrow().value();
            if (ringDye == Items.AIR || bgDye == Items.AIR) {
                logMissingDye(name, ringColor, bgColor);
                return;
            }

            ShapedRecipeBuilder.shaped(provider.lookupOrThrow(BuiltInRegistries.ITEM.key()), RecipeCategory.DECORATIONS, resultBlock)
                    .define('R', ringDye)
                    .define('B', bgDye)
                    .define('T', Items.REDSTONE_TORCH)
                    .define('L', Items.REDSTONE_LAMP)
                    .define('M', ItemInit.MUSINGS_TEMPLATE.get())
                    .pattern("RT ")
                    .pattern("BLM")
                    .unlockedBy("has_" + ringColor + "_dye", has(ringDye))
                    .unlockedBy("has_" + bgColor   + "_dye", has(bgDye))
                    .unlockedBy("has_musings_template", has(ItemInit.MUSINGS_TEMPLATE.get()))
                    .save(recipeOutput, Musings.MODID + ":" + name);
        });

        BlockInit.WOOD_INLAY_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String wood = name.substring(0, name.indexOf("_inlay"));

            Item planksItem = planksItemFor(wood);
            if (planksItem == Items.AIR) {
                LOGGER.warn("No planks found for wood '{}', skipping recipe for '{}'", wood, name);
                return;
            }

            ShapedRecipeBuilder.shaped(provider.lookupOrThrow(BuiltInRegistries.ITEM.key()), RecipeCategory.BUILDING_BLOCKS, resultBlock)
                    .define('P', planksItem)
                    .define('M', ItemInit.MUSINGS_TEMPLATE.get())
                    .pattern("P P")
                    .pattern(" M ")
                    .pattern("P P")
                    .unlockedBy("has_" + wood + "_planks", has(planksItem))
                    .unlockedBy("has_musings_template", has(ItemInit.MUSINGS_TEMPLATE.get()))
                    .save(recipeOutput, Musings.MODID + ":" + name + "_from_planks_recipe");
        });

        BlockInit.INK_BLOCKS.forEach((name, holder) -> {
            Item resultBlock = holder.get().asItem();
            String color = name.substring(0, name.indexOf("_ink_block"));
            Item dye = BuiltInRegistries.ITEM.get(Identifier.withDefaultNamespace(color + "_dye")).orElseThrow().value();

            ShapedRecipeBuilder.shaped(provider.lookupOrThrow(BuiltInRegistries.ITEM.key()), RecipeCategory.DECORATIONS, resultBlock, 1)
                    .define('D', dye)
                    .define('S', Items.SLIME_BLOCK)
                    .define('M', ItemInit.MUSINGS_TEMPLATE.get())
                    .pattern("DSD")
                    .pattern("SMS")
                    .pattern("DSD")
                    .unlockedBy("has_" + color + "_dye", has(dye))
                    .unlockedBy("has_musings_template", has(ItemInit.MUSINGS_TEMPLATE.get()))
                    .save(recipeOutput, Musings.MODID + ":" + name);

        });

        createStonecutting(recipeOutput, BlockInit.FLOWER_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.GUIDED_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.OPTICAL_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.PETAL_STONE_BLOCK.get().asItem());
        createStonecutting(recipeOutput, BlockInit.TARGETED_STONE_BLOCK.get().asItem());

        ShapedRecipeBuilder.shaped(provider.lookupOrThrow(BuiltInRegistries.ITEM.key()), RecipeCategory.MISC, ItemInit.MUSINGS_TEMPLATE.get())
                .define('P', Items.PAPER)
                .define('S', Items.STRING)
                .define('I', Items.BLACK_DYE)
                .pattern("SPS")
                .pattern("PIP")
                .pattern("SPS")
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput, Musings.MODID + ":musings_template");
    }

    private static void logMissingDye(String name, String ringColor, String bgColor) {
        LOGGER.warn("Missing dye for pair '{}': ring '{}' or bg '{}' not found, skipping", name, ringColor, bgColor);
    }

    private Item planksItemFor(String woodName) {
        var rl = Identifier.withDefaultNamespace(woodName + "_planks");
        var block = BuiltInRegistries.BLOCK.get(rl);
        return block.orElseThrow().value() == Blocks.AIR ? Items.AIR : block.orElseThrow().value().asItem();
    }

    private void createStonecutting(RecipeOutput recipeOutput, Item result) {
        Ingredient input = Ingredient.of(Blocks.STONE);
        RecipeCategory category = RecipeCategory.BUILDING_BLOCKS;
        SingleItemRecipeBuilder
                .stonecutting(input, category, result, 1)
                .unlockedBy("has_stone", has(Blocks.STONE))
                .save(recipeOutput,
                        Musings.MODID + ":" +
                                BuiltInRegistries.ITEM.getKey(result).getPath()
                                + "_from_stone_stonecutting");
    }
}
