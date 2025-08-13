package com.mystic.musings.blocks;

import com.mystic.musings.particles.InkTearOptions;
import net.minecraft.core.BlockPos;
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
            var mc = state.getMapColor(level, pos);
            int rgb = mc.col;
            float r = ((rgb >> 16) & 0xFF) / 255f;
            float g = ((rgb >> 8) & 0xFF) / 255f;
            float b = (rgb & 0xFF) / 255f;

            double x = pos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.6;
            double y = pos.getY() + rand.nextDouble();
            double z = pos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.6;

            level.addParticle(InkTearOptions.hang(r, g, b), x, y, z, 0.0, 0.0, 0.0);
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
        return true;
    }
}
