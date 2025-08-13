package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MusingsModelProvider extends ItemModelProvider {
    private static final String[] DYE_NAMES = {
            "white","orange","magenta","light_blue","yellow",
            "lime","pink","gray","light_gray","cyan",
            "purple","blue","brown","green","red","black"
    };

    // Wood types for inlays
    private static final String[] WOOD_TYPES = {
            "oak","spruce","birch","jungle","acacia",
            "dark_oak","mangrove","cherry","bamboo","crimson","warped"
    };

    public MusingsModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Musings.MODID, helper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("flower_stone", modLoc("block/flower_stone"));
        withExistingParent("guided_stone", modLoc("block/guided_stone"));
        withExistingParent("optical_stone", modLoc("block/optical_stone"));
        withExistingParent("petal_stone", modLoc("block/petal_stone"));
        withExistingParent("targeted_stone", modLoc("block/targeted_stone"));

        // circle blocks
        for (String ring : DYE_NAMES) {
            for (String bg : DYE_NAMES) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg", ring, bg);
                withExistingParent(name, modLoc("block/" + name));
            }
        }

        // flipping variants
        for (String ring : DYE_NAMES) {
            for (String bg : DYE_NAMES) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg_flipping", ring, bg);
                withExistingParent(name, modLoc("block/" + name));
            }
        }

        // cycle variants
        for (String color : DYE_NAMES) {
            String cycleName = "circle_cycle_" + color;
            withExistingParent(cycleName, modLoc("block/" + cycleName));
        }

        // colored ink blocks
        for (String color : DYE_NAMES) {
            String name = color + "_ink_block";
            withExistingParent(name, modLoc("block/" + name));
        }

        // wood inlays
        for (String wood : WOOD_TYPES) {
            String name = wood + "_inlay";
            withExistingParent(name, modLoc("block/" + name));
        }

        // musings template item
        singleTexture("musings_template",
                mcLoc("item/generated"),
                "layer0", modLoc("item/musings_template"));

    }
}

