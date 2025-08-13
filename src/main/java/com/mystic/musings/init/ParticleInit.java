// com.mystic.musings.init.ParticleInit.java
package com.mystic.musings.init;

import com.mojang.serialization.MapCodec;
import com.mystic.musings.Musings;
import com.mystic.musings.particles.InkTearOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, Musings.MODID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<InkTearOptions>> INK_TEAR_HANG =
            PARTICLES.register("ink_tear_hang", () -> new ParticleType<>(false) {
                @Override
                public @NotNull MapCodec<InkTearOptions> codec() {
                    return InkTearOptions.CODEC.xmap(
                            o -> InkTearOptions.hang(o.r(), o.g(), o.b()),
                            o -> o
                    );
                }

                @Override
                public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, InkTearOptions> streamCodec() {
                    return InkTearOptions.RGB_STREAM.map(
                            o -> InkTearOptions.hang(o.r(), o.g(), o.b()),
                            o -> o
                    );
                }
            });

    public static final DeferredHolder<ParticleType<?>, ParticleType<InkTearOptions>> INK_TEAR_FALL =
            PARTICLES.register("ink_tear_fall", () -> new ParticleType<>(false) {
                @Override
                public @NotNull MapCodec<InkTearOptions> codec() {
                    return InkTearOptions.CODEC.xmap(
                            o -> InkTearOptions.fall(o.r(), o.g(), o.b()),
                            o -> o
                    );
                }

                @Override
                public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, InkTearOptions> streamCodec() {
                    return InkTearOptions.RGB_STREAM.map(
                            o -> InkTearOptions.fall(o.r(), o.g(), o.b()),
                            o -> o
                    );
                }
            });

    public static final DeferredHolder<ParticleType<?>, ParticleType<InkTearOptions>> INK_TEAR_LAND =
            PARTICLES.register("ink_tear_land", () -> new ParticleType<>(false) {
                @Override
                public @NotNull MapCodec<InkTearOptions> codec() {
                    return InkTearOptions.CODEC.xmap(
                            o -> InkTearOptions.land(o.r(), o.g(), o.b()),
                            o -> o
                    );
                }

                @Override
                public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, InkTearOptions> streamCodec() {
                    return InkTearOptions.RGB_STREAM.map(
                            o -> InkTearOptions.land(o.r(), o.g(), o.b()),
                            o -> o
                    );
                }
            });

    public static void init(IEventBus bus) {
        PARTICLES.register(bus);
    }
}
