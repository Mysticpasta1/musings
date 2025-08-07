package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = Musings.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MusingsDataGenerators {
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient()) {
            gen.addProvider(true, new MusingsLanguageProvider(gen.getPackOutput()));
            gen.addProvider(true, new MusingsBlockStates(gen.getPackOutput(), helper));
            gen.addProvider(true, new MusingsModelProvider(gen.getPackOutput(), helper));
        }

        if (event.includeServer()) {
            gen.addProvider(true, new MusingsRecipes(gen.getPackOutput(), event.getLookupProvider()));
            gen.addProvider(true, new MusingLootTables(gen.getPackOutput(), Set.of(), List.of(), event.getLookupProvider()));
        }
    }
}
