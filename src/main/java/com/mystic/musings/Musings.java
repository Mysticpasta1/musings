package com.mystic.musings;

import com.mojang.logging.LogUtils;
import com.mystic.musings.init.BlockInit;
import com.mystic.musings.init.CreativeMenuInit;
import com.mystic.musings.init.ItemInit;
import com.mystic.musings.init.ParticleInit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Musings.MODID)
public class Musings {
    public static final String MODID = "musings";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Musings(ModContainer container) {
        IEventBus bus = container.getEventBus();
        BlockInit.init(bus);
        ItemInit.init(bus);
        CreativeMenuInit.init(bus);
        CreativeMenuInit.bootstrap();
        ParticleInit.init(bus);
    }
}
