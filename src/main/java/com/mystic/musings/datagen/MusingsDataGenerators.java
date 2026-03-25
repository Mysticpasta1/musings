package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Musings.MODID)
public class MusingsDataGenerators {
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent.Server event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        gen.addProvider(true, new MusingsRecipes(packOutput, lookupProvider));
        gen.addProvider(true, new MusingLootTables(packOutput, Set.of(), List.of(), lookupProvider));
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        MusingsBlockTagsProvider blockTags = new MusingsBlockTagsProvider(packOutput, lookupProvider);
        gen.addProvider(true, blockTags);
        gen.addProvider(true, new MusingsItemTagsProvider(packOutput, lookupProvider));
        gen.addProvider(true, new MusingsLanguageProvider(packOutput));
        gen.addProvider(true, new MusingsModelProvider(packOutput, Musings.MODID));
    }
}
