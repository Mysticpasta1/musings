package com.mystic.musings;

import com.mojang.logging.LogUtils;
import com.mystic.musings.init.BlockInit;
import com.mystic.musings.init.CreativeMenuInit;
import com.mystic.musings.init.ItemInit;
import com.mystic.musings.init.ParticleInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Musings.MODID)
public class Musings {
    public static final String MODID = "musings";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Musings(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        BlockInit.init(bus);
        ItemInit.init(bus);
        CreativeMenuInit.init(bus);
        CreativeMenuInit.bootstrap();
        ParticleInit.init(bus);
    }
}
