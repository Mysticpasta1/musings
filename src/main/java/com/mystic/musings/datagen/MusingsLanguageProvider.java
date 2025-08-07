package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MusingsLanguageProvider extends LanguageProvider {
    public MusingsLanguageProvider(PackOutput packOutput) {
        super(packOutput, Musings.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + Musings.MODID + ".general", "Musings");
        BlockInit.CIRCLE_CYCLE_BLOCKS.keySet().forEach(name -> {
            String color = name.substring("circle_cycle_".length());
            String pretty = capitalizeWords(color);
            String display = pretty + " Circle Cycle";
            add("block." + Musings.MODID + "." + name, display);
            add("item."  + Musings.MODID + "." + name, display);
        });

        BlockInit.CIRCLE_BLOCKS.keySet().forEach(name -> {
            String core = name.substring("circle_".length(), name.length() - "_bg".length());
            int idx = core.indexOf("_ring_");
            String ring = core.substring(0, idx);
            String bg   = core.substring(idx + "_ring_".length());
            String prettyRing = capitalizeWords(ring);
            String prettyBg   = capitalizeWords(bg);
            String display = prettyRing + " on " + prettyBg + " Circle";
            add("block." + Musings.MODID + "." + name, display);
            add("item."  + Musings.MODID + "." + name, display);
        });

        BlockInit.CIRCLE_FLIPS_BLOCKS.keySet().forEach(name -> {
            String base = name;
            String flippingSuffix = "_flipping";
            if (base.endsWith(flippingSuffix)) {
                base = base.substring(0, base.length() - flippingSuffix.length());
            }

            String core = base.substring(
                    "circle_".length(),
                    base.length() - "_bg".length()
            );

            int idx = core.indexOf("_ring_");
            String ring = core.substring(0, idx);
            String bg   = core.substring(idx + "_ring_".length());

            String prettyRing = capitalizeWords(ring);
            String prettyBg   = capitalizeWords(bg);
            String display    = prettyRing + " on " + prettyBg + " Circle Flipping";

            add("block." + Musings.MODID + "." + name, display);
            add("item."  + Musings.MODID + "." + name, display);
        });

    }

    private static String capitalizeWords(String input) {
        String[] parts = input.split("_");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].substring(0,1).toUpperCase() + parts[i].substring(1).toLowerCase();
        }
        return String.join(" ", parts);
    }
}
