package com.mystic.musings.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class InkBlock extends Block {
    public InkBlock(Properties props) {
        super(props);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        if (rand.nextInt(10) == 0) {
            double x = pos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.6;
            double y = pos.getY() + rand.nextDouble();
            double z = pos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.6;
            level.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, y, z, 0.0, 0.0, 0.0);
        }
        super.animateTick(state, level, pos, rand);
    }

    @Override
    public boolean isStickyBlock(@NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(@NotNull BlockState self, @NotNull BlockState other) {
        var otherBlock = other.getBlock();
        if (otherBlock == Blocks.SLIME_BLOCK || otherBlock == Blocks.HONEY_BLOCK) return false;
        if (otherBlock instanceof InkBlock) {
            return otherBlock == self.getBlock();
        }
        return true;
    }


    @Override
    public boolean isSlimeBlock(@NotNull BlockState state) {
        return false;
    }
}
