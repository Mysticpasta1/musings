package com.mystic.musings;

import com.mystic.musings.init.ParticleInit;
import com.mystic.musings.particles.BaseInkTear;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod(value = Musings.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Musings.MODID, value = Dist.CLIENT)
public class MusingsClient {
    public MusingsClient(ModContainer container) {
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent e) {
        e.registerSpriteSet(ParticleInit.INK_TEAR_HANG.get(), sprites ->
                (options, level, x, y, z, xd, yd, zd, randomSource) -> {
                    return new BaseInkTear.InkTearHangParticle(level, x, y, z, sprites.first(), options);
                });

        e.registerSpriteSet(ParticleInit.INK_TEAR_FALL.get(), sprites ->
                (options, level, x, y, z, xd, yd, zd, randomSource) -> {
                    return new BaseInkTear.InkTearFallParticle(level, x, y, z, sprites.first(), options);
                });

        e.registerSpriteSet(ParticleInit.INK_TEAR_LAND.get(), sprites ->
                (options, level, x, y, z, xd, yd, zd, randomSource) -> {
                    return new BaseInkTear.InkTearLandParticle(level, x, y, z, sprites.first(), options);
                });
    }
}
