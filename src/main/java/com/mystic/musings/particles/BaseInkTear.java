package com.mystic.musings.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public abstract class BaseInkTear extends TextureSheetParticle {
    protected boolean isGlowing = true;

    protected BaseInkTear(ClientLevel lvl, double x, double y, double z, InkTearOptions c) {
        super(lvl, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.setColor(c.r(), c.g(), c.b());
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float pt) {
        return isGlowing ? 240 : super.getLightColor(pt);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            onExpire();
            return;
        }
        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98;
        this.yd *= 0.98;
        this.zd *= 0.98;
        afterMove();
    }

    protected void onExpire() {
        this.remove();
    }

    protected void afterMove() {
    }


    public static class InkTearHangParticle extends BaseInkTear {
        private final InkTearOptions color;

        public InkTearHangParticle(ClientLevel lvl, double x, double y, double z, InkTearOptions c) {
            super(lvl, x, y, z, c);
            this.color = c;
            this.gravity = 0.01F;
            this.lifetime = 10;
        }

        @Override
        protected void afterMove() {
            this.xd *= 0.02;
            this.yd *= 0.02;
            this.zd *= 0.02;
        }

        @Override
        protected void onExpire() {
            this.remove();
            this.level.addParticle(InkTearOptions.fall(color.r(), color.g(), color.b()), // options
                    this.x, this.y, this.z, 0, 0, 0); // we will route to FALL type in the provider (see below)
        }
    }

    public static class InkTearFallParticle extends BaseInkTear {
        private final InkTearOptions color;

        public InkTearFallParticle(ClientLevel lvl, double x, double y, double z, InkTearOptions c) {
            super(lvl, x, y, z, c);
            this.color = c;
            this.gravity = 0.01F;
            this.lifetime = 40;
        }

        @Override
        protected void afterMove() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(InkTearOptions.land(color.r(), color.g(), color.b()),
                        this.x, this.y, this.z, 0, 0, 0); // LAND will be chosen by provider
            }
        }
    }

    public static class InkTearLandParticle extends BaseInkTear {
        public InkTearLandParticle(ClientLevel lvl, double x, double y, double z, InkTearOptions c) {
            super(lvl, x, y, z, c);
            this.lifetime = (int) (28.0 / (this.random.nextDouble() * 0.8 + 0.2));
        }

        @Override
        protected void afterMove() {
            if (this.onGround) this.remove();
        }
    }
}
