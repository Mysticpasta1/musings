package com.mystic.musings.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.musings.init.ParticleInit;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public record InkTearOptions(float r, float g, float b, Mode mode) implements ParticleOptions {
    public enum Mode {HANG, FALL, LAND}

    public static InkTearOptions hang(float r, float g, float b) {
        return new InkTearOptions(r, g, b, Mode.HANG);
    }

    public static InkTearOptions fall(float r, float g, float b) {
        return new InkTearOptions(r, g, b, Mode.FALL);
    }

    public static InkTearOptions land(float r, float g, float b) {
        return new InkTearOptions(r, g, b, Mode.LAND);
    }

    private static final Codec<InkTearOptions> RGB_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("r").forGetter(InkTearOptions::r),
                    Codec.FLOAT.fieldOf("g").forGetter(InkTearOptions::g),
                    Codec.FLOAT.fieldOf("b").forGetter(InkTearOptions::b)
            ).apply(instance, (r, g, b) -> new InkTearOptions(r, g, b, Mode.HANG))  // placeholder mode
    );

    public static final Codec<InkTearOptions> HANG_CODEC = RGB_CODEC.xmap(
            o -> InkTearOptions.hang(o.r(), o.g(), o.b()),
            o -> o
    );
    public static final Codec<InkTearOptions> FALL_CODEC = RGB_CODEC.xmap(
            o -> InkTearOptions.fall(o.r(), o.g(), o.b()),
            o -> o
    );
    public static final Codec<InkTearOptions> LAND_CODEC = RGB_CODEC.xmap(
            o -> InkTearOptions.land(o.r(), o.g(), o.b()),
            o -> o
    );

    public static final Deserializer<InkTearOptions> HANG_DESERIALIZER =
            new Deserializer<>() {
                @Override
                public InkTearOptions fromCommand(ParticleType<InkTearOptions> type, StringReader reader)
                        throws CommandSyntaxException {
                    reader.expect(' ');
                    float r = reader.readFloat();
                    reader.expect(' ');
                    float g = reader.readFloat();
                    reader.expect(' ');
                    float b = reader.readFloat();
                    return InkTearOptions.hang(r, g, b);
                }

                @Override
                public InkTearOptions fromNetwork(ParticleType<InkTearOptions> type, FriendlyByteBuf buf) {
                    float r = buf.readFloat();
                    float g = buf.readFloat();
                    float b = buf.readFloat();
                    return InkTearOptions.hang(r, g, b);
                }
            };

    public static final Deserializer<InkTearOptions> FALL_DESERIALIZER =
            new Deserializer<>() {
                @Override
                public InkTearOptions fromCommand(ParticleType<InkTearOptions> type, StringReader reader)
                        throws CommandSyntaxException {
                    reader.expect(' ');
                    float r = reader.readFloat();
                    reader.expect(' ');
                    float g = reader.readFloat();
                    float b = reader.readFloat();
                    return InkTearOptions.fall(r, g, b);
                }

                @Override
                public InkTearOptions fromNetwork(ParticleType<InkTearOptions> type, FriendlyByteBuf buf) {
                    float r = buf.readFloat();
                    float g = buf.readFloat();
                    float b = buf.readFloat();
                    return InkTearOptions.fall(r, g, b);
                }
            };

    public static final Deserializer<InkTearOptions> LAND_DESERIALIZER =
            new Deserializer<>() {
                @Override
                public InkTearOptions fromCommand(ParticleType<InkTearOptions> type, StringReader reader)
                        throws CommandSyntaxException {
                    reader.expect(' ');
                    float r = reader.readFloat();
                    reader.expect(' ');
                    float g = reader.readFloat();
                    float b = reader.readFloat();
                    return InkTearOptions.land(r, g, b);
                }

                @Override
                public InkTearOptions fromNetwork(ParticleType<InkTearOptions> type, FriendlyByteBuf buf) {
                    float r = buf.readFloat();
                    float g = buf.readFloat();
                    float b = buf.readFloat();
                    return InkTearOptions.land(r, g, b);
                }
            };

    @Override
    public ParticleType<?> getType() {
        return ParticleInit.INK_TEAR_HANG.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.r);
        buf.writeFloat(this.g);
        buf.writeFloat(this.b);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%f %f %f", this.r, this.g, this.b);
    }
}
