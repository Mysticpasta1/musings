package com.mystic.musings.init;

import com.mojang.serialization.Codec;
import com.mystic.musings.Musings;
import com.mystic.musings.particles.InkTearOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ParticleInit {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Musings.MODID);

    // HANG
    public static final RegistryObject<ParticleType<InkTearOptions>> INK_TEAR_HANG =
            PARTICLES.register("ink_tear_hang", () ->
                    new ParticleType<>(false, InkTearOptions.HANG_DESERIALIZER) {
                        @Override
                        public @NotNull Codec<InkTearOptions> codec() {
                            return InkTearOptions.HANG_CODEC;
                        }
                    });

    // FALL
    public static final RegistryObject<ParticleType<InkTearOptions>> INK_TEAR_FALL =
            PARTICLES.register("ink_tear_fall", () ->
                    new ParticleType<>(false, InkTearOptions.FALL_DESERIALIZER) {
                        @Override
                        public @NotNull Codec<InkTearOptions> codec() {
                            return InkTearOptions.FALL_CODEC;
                        }
                    });

    // LAND
    public static final RegistryObject<ParticleType<InkTearOptions>> INK_TEAR_LAND =
            PARTICLES.register("ink_tear_land", () ->
                    new ParticleType<>(false, InkTearOptions.LAND_DESERIALIZER) {
                        @Override
                        public @NotNull Codec<InkTearOptions> codec() {
                            return InkTearOptions.LAND_CODEC;
                        }
                    });

    public static void init(IEventBus bus) {
        PARTICLES.register(bus);
    }
}
