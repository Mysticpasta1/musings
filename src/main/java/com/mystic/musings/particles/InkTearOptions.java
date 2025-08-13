package com.mystic.musings.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.musings.init.ParticleInit;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record InkTearOptions(Stage stage, float r, float g, float b) implements ParticleOptions {
    public enum Stage { HANG, FALL, LAND }

    public static InkTearOptions hang(float r, float g, float b) { return new InkTearOptions(Stage.HANG, r, g, b); }
    public static InkTearOptions fall(float r, float g, float b) { return new InkTearOptions(Stage.FALL, r, g, b); }
    public static InkTearOptions land(float r, float g, float b) { return new InkTearOptions(Stage.LAND, r, g, b); }

    @Override public @NotNull ParticleType<?> getType() {
        return switch (stage) {
            case HANG -> ParticleInit.INK_TEAR_HANG.get();
            case FALL -> ParticleInit.INK_TEAR_FALL.get();
            case LAND -> ParticleInit.INK_TEAR_LAND.get();
        };
    }

    public static final MapCodec<InkTearOptions> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    Codec.FLOAT.fieldOf("r").forGetter(InkTearOptions::r),
                    Codec.FLOAT.fieldOf("g").forGetter(InkTearOptions::g),
                    Codec.FLOAT.fieldOf("b").forGetter(InkTearOptions::b)
            ).apply(i, InkTearOptions::hang)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, InkTearOptions> RGB_STREAM =
            StreamCodec.of((buf, o) -> { buf.writeFloat(o.r()); buf.writeFloat(o.g()); buf.writeFloat(o.b()); },
                    buf -> hang(buf.readFloat(), buf.readFloat(), buf.readFloat()));
}

