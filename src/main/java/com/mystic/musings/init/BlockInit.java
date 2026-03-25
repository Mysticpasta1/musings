package com.mystic.musings.init;

import com.mystic.musings.Musings;
import com.mystic.musings.blocks.InkBlock;
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

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Musings.MODID);

    public static final Map<String, DeferredBlock<Block>> CIRCLE_BLOCKS = new HashMap<>();
    public static final Map<String, DeferredBlock<Block>> CIRCLE_CYCLE_BLOCKS = new HashMap<>();
    public static final Map<String, DeferredBlock<Block>> CIRCLE_FLIPS_BLOCKS = new HashMap<>();
    public static final Map<String, DeferredBlock<Block>> WOOD_INLAY_BLOCKS = new HashMap<>();
    public static final Map<String, DeferredBlock<Block>> INK_BLOCKS = new HashMap<>();

    public static final DeferredBlock<Block> FLOWER_STONE_BLOCK = registerBlock("flower_stone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> GUIDED_STONE_BLOCK = registerBlock("guided_stone",
            Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> OPTICAL_STONE_BLOCK = registerBlock("optical_stone",
            Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> PETAL_STONE_BLOCK = registerBlock("petal_stone",
            Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> TARGETED_STONE_BLOCK = registerBlock("targeted_stone",
            Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

    private static void registerColoredInks() {
        for (DyeColor color : DyeColor.values()) {
            String name = color.getName() + "_ink_block";
            INK_BLOCKS.put(name, registerBlock(name, (properties) -> new InkBlock(properties
                    .mapColor(color)
                    .strength(1.8F)
                    .sound(net.minecraft.world.level.block.SoundType.SLIME_BLOCK)
            ), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
        }
    }

    static {
        for (DyeColor ring : DyeColor.values()) {
            for (DyeColor bg : DyeColor.values()) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg", ring.getName(), bg.getName());
                CIRCLE_BLOCKS.put(name, registerBlock(name, Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)));
            }
        }

        for (DyeColor ring : DyeColor.values()) {
            for (DyeColor bg : DyeColor.values()) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg_flipping", ring.getName(), bg.getName());
                CIRCLE_FLIPS_BLOCKS.put(name, registerBlock(name, Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)));
            }
        }

        for (DyeColor color : DyeColor.values()) {
            String name = "circle_cycle_" + color.getName();
            CIRCLE_CYCLE_BLOCKS.put(name, registerBlock(name,  Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)));
        }

        registerWoodInlays();
        registerColoredInks();
    }

    private static void registerWoodInlays() {
        registerWoodInlay("oak",       Blocks.OAK_PLANKS);
        registerWoodInlay("spruce",    Blocks.SPRUCE_PLANKS);
        registerWoodInlay("birch",     Blocks.BIRCH_PLANKS);
        registerWoodInlay("jungle",    Blocks.JUNGLE_PLANKS);
        registerWoodInlay("acacia",    Blocks.ACACIA_PLANKS);
        registerWoodInlay("dark_oak",  Blocks.DARK_OAK_PLANKS);
        registerWoodInlay("mangrove",  Blocks.MANGROVE_PLANKS);
        registerWoodInlay("cherry",    Blocks.CHERRY_PLANKS);
        registerWoodInlay("bamboo",    Blocks.BAMBOO_PLANKS);
        registerWoodInlay("crimson",   Blocks.CRIMSON_PLANKS);
        registerWoodInlay("warped",    Blocks.WARPED_PLANKS);
    }

    private static void registerWoodInlay(String woodName, Block basePlanks) {
        String regName = woodName + "_inlay";
        WOOD_INLAY_BLOCKS.put(regName, registerBlock(regName, Block::new, BlockBehaviour.Properties.ofFullCopy(basePlanks)));
    }

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, B> blockFactory, BlockBehaviour.Properties properties) {
        return registerMainTabBlock(name, blockFactory, properties);
    }

    private static <B extends Block, I extends BlockItem> DeferredBlock<B> registerMainTabBlock(String name, Function<BlockBehaviour.Properties, B> blockFactory, BlockBehaviour.Properties properties) {
        var reg = BLOCKS.registerBlock(name, blockFactory, () -> properties);
        var itemReg = ItemInit.ITEMS.registerItem(name, props -> new BlockItem(reg.get(), props), Item.Properties::new);
        CreativeMenuInit.addToMainTab(itemReg);
        return reg;
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
