package com.mystic.musings.datagen;

import com.mystic.musings.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MusingLootTables extends LootTableProvider {

    public MusingLootTables(PackOutput output, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, requiredTables, subProviders, registries);
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables() {
        return List.of(new SubProviderEntry(
                provider -> (BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) -> {
                    BlockInit.CIRCLE_BLOCKS.values().forEach(holder -> {
                        Block block = holder.get();
                        block.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)))));
                    });

                    BlockInit.CIRCLE_FLIPS_BLOCKS.values().forEach(holder -> {
                        Block block = holder.get();
                        block.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)))));
                    });

                    BlockInit.CIRCLE_CYCLE_BLOCKS.values().forEach(holder -> {
                        Block block = holder.get();
                        block.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)))));
                    });

                    BlockInit.WOOD_INLAY_BLOCKS.values().forEach(holder -> {
                        Block block = holder.get();
                        block.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)))));
                    });

                    BlockInit.INK_BLOCKS.values().forEach(holder -> {
                        Block block = holder.get();
                        block.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)))));
                    });

                    Block b1 = BlockInit.FLOWER_STONE_BLOCK.get();
                    b1.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(b1)))));

                    Block b2 = BlockInit.GUIDED_STONE_BLOCK.get();
                    b2.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(b2)))));

                    Block b3 = BlockInit.PETAL_STONE_BLOCK.get();
                    b3.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(b3)))));

                    Block b4 = BlockInit.TARGETED_STONE_BLOCK.get();
                    b4.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(b4)))));

                    Block b5 = BlockInit.OPTICAL_STONE_BLOCK.get();
                    b5.getLootTable().ifPresent(key -> builder.accept(key, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(b5)))));
                }, LootContextParamSets.BLOCK
        ));
    }
}
