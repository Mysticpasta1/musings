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
        BlockInit.CIRCLE_CYCLE_BLOCKS.forEach((name, block) -> {
            String color = name.substring("circle_cycle_".length());
            String pretty = capitalizeWords(color);
            String display = pretty + " Circle Cycle";
            add(block.get(), display);
        });

        BlockInit.CIRCLE_BLOCKS.forEach((name, block) -> {
            String core = name.substring("circle_".length());
            // find "_ring_"
            int idx = core.indexOf("_ring_");
            if (idx == -1) {
                // fallback
                add(block.get(), capitalizeWords(name));
                return;
            }
            String ring = core.substring(0, idx);
            // removing "_bg" from the end part
            String remainder = core.substring(idx + "_ring_".length());
            if (remainder.endsWith("_bg")) {
                remainder = remainder.substring(0, remainder.length() - "_bg".length());
            }

            String bg = remainder;

            String prettyRing = capitalizeWords(ring);
            String prettyBg   = capitalizeWords(bg);

            // e.g. "White on Orange Circle"
            String display = prettyRing + " on " + prettyBg + " Circle";
            add(block.get(), display);
        });

        for (Map.Entry<String, DeferredBlock<Block>> e : BlockInit.WOOD_INLAY_BLOCKS.entrySet()) {
            String name = e.getKey();
            // name is e.g. "oak_inlay"
            // we want "Oak Inlay"
            // Check if ends with _inlay
            if (name.endsWith("_inlay")) {
                String wood = name.substring(0, name.length() - "_inlay".length());
                add(e.getValue().get(), capitalizeWords(wood) + " Inlay");
            } else {
                add(e.getValue().get(), capitalizeWords(name));
            }
        }

        for (Map.Entry<String, DeferredBlock<Block>> e : BlockInit.INK_BLOCKS.entrySet()) {
            String name = e.getKey();
            if (name.endsWith("_ink_block")) {
                String color = name.substring(0, name.length() - "_ink_block".length());
                String prettyColor = capitalizeWords(color);
                add(e.getValue().get(), prettyColor + " Ink Block");
            } else {
                add(e.getValue().get(), capitalizeWords(name));
            }
        }

        add(ItemInit.MUSINGS_TEMPLATE.get(), "Musings Template");
        add(BlockInit.FLOWER_STONE_BLOCK.get(), "Flower Stone Block");
        add(BlockInit.GUIDED_STONE_BLOCK.get(), "Guided Stone Block");
        add(BlockInit.OPTICAL_STONE_BLOCK.get(), "Optical Stone Block");
        add(BlockInit.PETAL_STONE_BLOCK.get(),  "Petal Stone Block");
        add(BlockInit.TARGETED_STONE_BLOCK.get(), "Targeted Stone Block");


        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, block) -> {
            // e.g. "circle_white_ring_orange_bg_flipping"
            String base = name;
            String flippingSuffix = "_flipping";
            if (base.endsWith(flippingSuffix)) {
                base = base.substring(0, base.length() - flippingSuffix.length());
            }

            // base is now "circle_white_ring_orange_bg"
            String core = base.substring("circle_".length());
            int idx = core.indexOf("_ring_");
            if (idx == -1) {
                add(block.get(), capitalizeWords(name));
                return;
            }
            String ring = core.substring(0, idx);
            String remainder = core.substring(idx + "_ring_".length());
            if (remainder.endsWith("_bg")) {
                remainder = remainder.substring(0, remainder.length() - "_bg".length());
            }
            String bg = remainder;

            String prettyRing = capitalizeWords(ring);
            String prettyBg   = capitalizeWords(bg);

            String display    = prettyRing + " on " + prettyBg + " Circle Flipping";

            add(block.get(), display);
        });

    }

    private static String capitalizeWords(String input) {
        String[] parts = input.split("_");
        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1).toLowerCase();
            }
        }
        return String.join(" ", parts);
    }
}
