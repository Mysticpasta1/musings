package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Musings.MODID)
public class MusingsDataGenerators {
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        MusingsBlockTagsProvider blockTags = new MusingsBlockTagsProvider(packOutput, lookupProvider, helper);

        if (event.includeClient()) {
            gen.addProvider(true, new MusingsLanguageProvider(packOutput));
            gen.addProvider(true, new MusingsBlockStates(packOutput, helper));
            gen.addProvider(true, new MusingsModelProvider(packOutput, helper));
        }

        if (event.includeServer()) {
            gen.addProvider(true, blockTags);
            gen.addProvider(true, new MusingsItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), helper));
            gen.addProvider(true, new MusingsRecipes(packOutput, lookupProvider));
            gen.addProvider(true, new MusingLootTables(packOutput, Set.of(), List.of(), lookupProvider));
        }
    }
}
