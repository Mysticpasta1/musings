package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MusingsModelProvider extends ItemModelProvider {
    private static final String[] DYE_NAMES = {
        "white","orange","magenta","light_blue","yellow",
        "lime","pink","gray","light_gray","cyan",
        "purple","blue","brown","green","red","black"
    };

    public MusingsModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Musings.MODID, helper);
    }

    @Override
    protected void registerModels() {
        for (String ring : DYE_NAMES) {
            for (String bg : DYE_NAMES) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg", ring, bg);
                getBuilder(name)
                        .parent(new ModelFile.UncheckedModelFile(
                                Musings.MODID + ":block/" + name
                        ));
            }
        }

        for (String ring : DYE_NAMES) {
            for (String bg : DYE_NAMES) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg_flipping", ring, bg);
                getBuilder(name)
                        .parent(new ModelFile.UncheckedModelFile(
                                Musings.MODID + ":block/" + name
                        ));
            }
        }

        for (String color : DYE_NAMES) {
            String cycleName = String.format("circle_cycle_%s", color);
            getBuilder(cycleName)
                    .parent(new ModelFile.UncheckedModelFile(
                            Musings.MODID + ":block/" + cycleName
                    ));
        }
    }

}
