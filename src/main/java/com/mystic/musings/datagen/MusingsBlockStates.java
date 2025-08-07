package com.mystic.musings.datagen;

import com.mystic.musings.Musings;
import com.mystic.musings.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MusingsBlockStates extends BlockStateProvider {
    public MusingsBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, Musings.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockInit.CIRCLE_BLOCKS.forEach((name, holder) -> {
            simpleBlock(holder.get(),
                models().cubeAll(name, modLoc("block/circle_pairs/" + name))
            );
        });

        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, holder) -> {
            simpleBlock(holder.get(),
                    models().cubeAll(name, modLoc("block/circle_flips/" + name))
            );
        });

        BlockInit.CIRCLE_CYCLE_BLOCKS.forEach((name, holder) -> {
            simpleBlock(holder.get(),
                models().cubeAll(name, modLoc("block/circle_cycles/" + name))
            );
        });
    }
}
