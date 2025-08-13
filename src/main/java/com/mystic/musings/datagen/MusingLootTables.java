package com.mystic.musings.datagen;

import com.mystic.musings.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MusingLootTables extends LootTableProvider {

    public MusingLootTables(PackOutput output, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, requiredTables, subProviders, registries);
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables() {
        return List.of(new LootTableProvider.SubProviderEntry(
                new Function<>() {
                    @Override
                    public LootTableSubProvider apply(HolderLookup.Provider provider) {
                        return builder -> {
                            BlockInit.CIRCLE_BLOCKS.values()
                                    .forEach(holder -> dropSelf(holder.get(), builder));

                            BlockInit.CIRCLE_FLIPS_BLOCKS.values()
                                    .forEach(holder -> dropSelf(holder.get(), builder));

                            BlockInit.CIRCLE_CYCLE_BLOCKS.values()
                                    .forEach(holder -> dropSelf(holder.get(), builder));

                            BlockInit.WOOD_INLAY_BLOCKS.values()
                                    .forEach(holder -> dropSelf(holder.get(), builder));

                            BlockInit.INK_BLOCKS.values()
                                    .forEach(holder -> dropSelf(holder.get(), builder));

                            dropSelf(BlockInit.FLOWER_STONE_BLOCK.get(), builder);
                            dropSelf(BlockInit.GUIDED_STONE_BLOCK.get(), builder);
                            dropSelf(BlockInit.PETAL_STONE_BLOCK.get(), builder);
                            dropSelf(BlockInit.TARGETED_STONE_BLOCK.get(), builder);
                            dropSelf(BlockInit.OPTICAL_STONE_BLOCK.get(), builder);
                        };
                    }

                    private static void dropSelf(Block block, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
                        builder.accept(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block).when(ExplosionCondition.survivesExplosion())).add(LootItem.lootTableItem(block))));
                    }
                }, LootContextParamSets.BLOCK
        ));
    }
}
