package com.mystic.musings;

import com.mystic.musings.init.ParticleInit;
import com.mystic.musings.particles.BaseInkTear;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent e) {
        e.registerSpriteSet(ParticleInit.INK_TEAR_HANG.get(), sprites ->
                (options, level, x, y, z, xd, yd, zd) -> {
                    var p = new BaseInkTear.InkTearHangParticle(level, x, y, z, options);
                    p.pickSprite(sprites);
                    return p;
                });

        e.registerSpriteSet(ParticleInit.INK_TEAR_FALL.get(), sprites ->
                (options, level, x, y, z, xd, yd, zd) -> {
                    var p = new BaseInkTear.InkTearFallParticle(level, x, y, z, options);
                    p.pickSprite(sprites);
                    return p;
                });

        e.registerSpriteSet(ParticleInit.INK_TEAR_LAND.get(), sprites ->
                (options, level, x, y, z, xd, yd, zd) -> {
                    var p = new BaseInkTear.InkTearLandParticle(level, x, y, z, options);
                    p.pickSprite(sprites);
                    return p;
                });
    }
}
