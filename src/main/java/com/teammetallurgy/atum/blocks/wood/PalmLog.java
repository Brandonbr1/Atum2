package com.teammetallurgy.atum.blocks.wood;

import com.teammetallurgy.atum.init.AtumBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;

public class PalmLog extends RotatedPillarBlock {

    public PalmLog() {
        super(BlockBehaviour.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.WOOD : MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD));
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolType toolType) {
        return toolType == ToolType.AXE ? AtumBlocks.STRIPPED_PALM_LOG.defaultBlockState() : super.getToolModifiedState(state, world, pos, player, stack, toolType);
    }
}