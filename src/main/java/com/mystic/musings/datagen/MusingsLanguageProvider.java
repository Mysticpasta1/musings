package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import com.mystic.musings.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

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

        for (Map.Entry<String, ? extends net.neoforged.neoforge.registries.DeferredBlock<Block>> e
                : BlockInit.WOOD_INLAY_BLOCKS.entrySet()) {
            String name = e.getKey();
            String wood = name.substring(0, name.indexOf("_inlay"));
            add(e.getValue().get(), toTitle(wood) + " Inlay");
        }

        for (Map.Entry<String, ? extends DeferredBlock<Block>> e : BlockInit.INK_BLOCKS.entrySet()) {
            String name = e.getKey();
            String color = name.substring(0, name.length() - "_ink_block".length());
            String prettyColor = capitalizeWords(color); // e.g. "Red"
            add(e.getValue().get(), prettyColor + " Ink Block");
        }

        add(ItemInit.MUSINGS_TEMPLATE.get(), "Musings Template");
        add("block.musings.flower_stone", "Flower Stone Block");
        add("block.musings.guided_stone", "Guided Stone Block");
        add("block.musings.optical_stone", "Optical Stone Block");
        add("block.musings.petal_stone",  "Petal Stone Block");
        add("block.musings.targeted_stone", "Targeted Stone Block");


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

    private static String toTitle(String id) {
        String[] parts = id.split("_");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            sb.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.length() > 1 ? p.substring(1) : "")
                    .append(" ");
        }
        return sb.toString().trim();
    }
}
