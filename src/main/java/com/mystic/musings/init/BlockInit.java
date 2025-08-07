package com.mystic.musings.init;

import com.mystic.musings.Musings;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Musings.MODID);

    public static final Map<String, DeferredBlock<Block>> CIRCLE_BLOCKS = new HashMap<>();
    public static final Map<String, DeferredBlock<Block>> CIRCLE_CYCLE_BLOCKS = new HashMap<>();
    public static final Map<String, DeferredBlock<Block>> CIRCLE_FLIPS_BLOCKS = new HashMap<>();

    public static final DeferredBlock<Block> FLOWER_STONE_BLOCK = registerBlock("flower_stone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> GUIDED_STONE_BLOCK = registerBlock("guided_stone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> OPTICAL_STONE_BLOCK = registerBlock("optical_stone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> PETAL_STONE_BLOCK = registerBlock("petal_stone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> TARGETED_STONE_BLOCK = registerBlock("targeted_stone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    static {
        for (DyeColor ring : DyeColor.values()) {
            for (DyeColor bg : DyeColor.values()) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg", ring.getName(), bg.getName());
                CIRCLE_BLOCKS.put(name, registerBlock(name, () ->
                        new Block(BlockBehaviour.Properties
                                .ofFullCopy(Blocks.GLOWSTONE)
                        )
                ));
            }
        }

        for (DyeColor ring : DyeColor.values()) {
            for (DyeColor bg : DyeColor.values()) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg_flipping", ring.getName(), bg.getName());
                CIRCLE_FLIPS_BLOCKS.put(name, registerBlock(name, () ->
                        new Block(BlockBehaviour.Properties
                                .ofFullCopy(Blocks.GLOWSTONE)
                        )
                ));
            }
        }

        for (DyeColor color : DyeColor.values()) {
            String name = "circle_cycle_" + color.getName();
            CIRCLE_CYCLE_BLOCKS.put(name, registerBlock(name, () ->
                    new Block(BlockBehaviour.Properties
                            .ofFullCopy(Blocks.GLOWSTONE)
                    )
            ));
        }
    }

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<B> block) {
        return registerMainTabBlock(name, block, b -> () -> new BlockItem(b.get(), new Item.Properties()));
    }

    private static <B extends Block, I extends BlockItem> DeferredBlock<B> registerMainTabBlock(String name, Supplier<B> block, Function<DeferredBlock<B>, Supplier<I>> item) {
        var reg = BLOCKS.register(name, block);
        CreativeMenuInit.addToMainTab(ItemInit.ITEMS.register(name, () -> item.apply(reg).get()));
        return reg;
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
