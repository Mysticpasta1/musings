package com.mystic.musings.init;

import com.mystic.musings.Musings;
import com.mystic.musings.blocks.InkBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, Musings.MODID);

    public static final Map<String, RegistryObject<Block>> CIRCLE_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> CIRCLE_CYCLE_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> CIRCLE_FLIPS_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> WOOD_INLAY_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> INK_BLOCKS = new HashMap<>();

    public static final RegistryObject<Block> FLOWER_STONE_BLOCK = registerBlock("flower_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> GUIDED_STONE_BLOCK = registerBlock("guided_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> OPTICAL_STONE_BLOCK = registerBlock("optical_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> PETAL_STONE_BLOCK = registerBlock("petal_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> TARGETED_STONE_BLOCK = registerBlock("targeted_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    private static void registerColoredInks() {
        for (DyeColor color : DyeColor.values()) {
            String name = color.getName() + "_ink_block";
            INK_BLOCKS.put(name, registerBlock(name, () ->
                    new InkBlock(BlockBehaviour.Properties
                            .of()
                            .mapColor(color)
                            .strength(1.8F)
                            .sound(net.minecraft.world.level.block.SoundType.SLIME_BLOCK)
                    )
            ));
        }
    }

    static {
        for (DyeColor ring : DyeColor.values()) {
            for (DyeColor bg : DyeColor.values()) {
                if (ring.equals(bg)) continue;
                String name = String.format("circle_%s_ring_%s_bg", ring.getName(), bg.getName());
                CIRCLE_BLOCKS.put(name, registerBlock(name, () ->
                        new Block(BlockBehaviour.Properties
                                .copy(Blocks.GLOWSTONE)
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
                                .copy(Blocks.GLOWSTONE)
                        )
                ));
            }
        }

        for (DyeColor color : DyeColor.values()) {
            String name = "circle_cycle_" + color.getName();
            CIRCLE_CYCLE_BLOCKS.put(name, registerBlock(name, () ->
                    new Block(BlockBehaviour.Properties
                            .copy(Blocks.GLOWSTONE)
                    )
            ));
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
        WOOD_INLAY_BLOCKS.put(regName, registerBlock(regName,
                () -> new Block(BlockBehaviour.Properties.copy(basePlanks))));
    }

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> block) {
        return registerMainTabBlock(name, block, b -> () -> new BlockItem(b.get(), new Item.Properties()));
    }

    private static <B extends Block, I extends BlockItem> RegistryObject<B> registerMainTabBlock(String name, Supplier<B> block, Function<RegistryObject<B>, Supplier<I>> item) {
        var reg = BLOCKS.register(name, block);
        CreativeMenuInit.addToMainTab(ItemInit.ITEMS.register(name, () -> item.apply(reg).get()));
        return reg;
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
