package com.mystic.musings.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class MusingsRecipes extends RecipeProvider.Runner {
    protected MusingsRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
        return new MusingsRecipeProvider(registries, output);
    }

    @Override
    public @NonNull String getName() {
        return "Recipes";
    }
}
