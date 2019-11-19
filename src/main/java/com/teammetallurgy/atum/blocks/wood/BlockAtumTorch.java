package com.teammetallurgy.atum.blocks.wood;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockAtumTorch extends BlockTorch {

    public BlockAtumTorch() {
        super();
        this.setHardness(0.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(null);
        this.setLightLevel(0.9375F);
    }
}