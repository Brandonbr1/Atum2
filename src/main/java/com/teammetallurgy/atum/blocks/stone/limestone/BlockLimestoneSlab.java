package com.teammetallurgy.atum.blocks.stone.limestone;

import com.google.common.collect.Maps;
import com.teammetallurgy.atum.blocks.base.BlockAtumSlab;
import com.teammetallurgy.atum.items.AtumSlabItem;
import com.teammetallurgy.atum.utils.AtumRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

import java.util.Map;

public class BlockLimestoneSlab extends BlockAtumSlab {
    private static final Map<BlockLimestoneBricks.BrickType, Block> BRICK_SLAB = Maps.newEnumMap(BlockLimestoneBricks.BrickType.class);

    private BlockLimestoneSlab() {
        super(Material.ROCK, MaterialColor.SAND);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.STONE);
        this.useNeighborBrightness = true;
    }

    public static void registerSlabs() {
        for (BlockLimestoneBricks.BrickType type : BlockLimestoneBricks.BrickType.values()) {
            BlockAtumSlab limestoneSlab = new BlockLimestoneSlab();
            BRICK_SLAB.put(type, limestoneSlab);
            AtumRegistry.registerBlock(limestoneSlab, new AtumSlabItem(limestoneSlab, limestoneSlab), "limestone_" + type.getName() + "_slab");
        }
    }

    public static Block getSlab(BlockLimestoneBricks.BrickType type) {
        return BRICK_SLAB.get(type);
    }
}