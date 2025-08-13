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
        BlockInit.CIRCLE_BLOCKS.forEach((name, holder) -> simpleBlock(holder.get(),
            models().cubeAll(name, modLoc("block/circle_pairs/" + name))
        ));

        BlockInit.CIRCLE_FLIPS_BLOCKS.forEach((name, holder) -> simpleBlock(holder.get(),
                models().cubeAll(name, modLoc("block/circle_flips/" + name))
        ));

        BlockInit.CIRCLE_CYCLE_BLOCKS.forEach((name, holder) -> simpleBlock(holder.get(),
            models().cubeAll(name, modLoc("block/circle_cycles/" + name))
        ));

        BlockInit.INK_BLOCKS.forEach((name, holder) -> simpleBlock(holder.get(),
            models().cubeAll(name, modLoc("block/ink_blocks/" + name))
        ));

        BlockInit.WOOD_INLAY_BLOCKS.forEach((name, holder) -> simpleBlock(holder.get(),
                models().cubeAll(name, modLoc("block/wooden_inlays/" + name))
        ));

        simpleBlock(BlockInit.FLOWER_STONE_BLOCK.get(), models().cubeAll("flower_stone", modLoc("block/stone_carvings/flower_stone")));
        simpleBlock(BlockInit.GUIDED_STONE_BLOCK.get(), models().cubeAll("guided_stone", modLoc("block/stone_carvings/guided_stone")));
        simpleBlock(BlockInit.OPTICAL_STONE_BLOCK.get(), models().cubeAll("optical_stone", modLoc("block/stone_carvings/optical_stone")));
        simpleBlock(BlockInit.PETAL_STONE_BLOCK.get(), models().cubeAll("petal_stone", modLoc("block/stone_carvings/petal_stone")));
        simpleBlock(BlockInit.TARGETED_STONE_BLOCK.get(), models().cubeAll("targeted_stone", modLoc("block/stone_carvings/targeted_stone")));
    }
}
