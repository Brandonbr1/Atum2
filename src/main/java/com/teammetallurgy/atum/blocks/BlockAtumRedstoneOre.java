package com.teammetallurgy.atum.blocks;

import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.utils.OreDictHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class BlockAtumRedstoneOre extends Block {
    private final boolean isLit;

    public BlockAtumRedstoneOre(boolean isLit) {
        super(Material.ROCK);
        this.isLit = isLit;
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.STONE);

        if (isLit) {
            setLightLevel(0.625F);
            this.setTickRandomly(true);
        }
    }

    @Override
    public int tickRate(World world) {
        return 30;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, PlayerEntity playerIn) {
        this.activate(world, pos);
        super.onBlockClicked(world, pos, playerIn);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
        this.activate(world, pos);
        super.onEntityCollision(world, pos, state, entity);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        this.activate(world, pos);
        return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }

    private void activate(World world, BlockPos pos) {
        this.addParticles(world, pos);

        if (this == AtumBlocks.REDSTONE_ORE) {
            world.setBlockState(pos, AtumBlocks.LIT_REDSTONE_ORE.getDefaultState());
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
        if (this == AtumBlocks.LIT_REDSTONE_ORE) {
            world.setBlockState(pos, AtumBlocks.REDSTONE_ORE.getDefaultState());
        }
    }

    @Override
    @Nonnull
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Items.REDSTONE;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, @Nonnull Random random) {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }

    @Override
    public int quantityDropped(Random random) {
        return 4 + random.nextInt(2);
    }

    @Override
    public int getExpDrop(BlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        if (this.getItemDropped(world.getBlockState(pos), RANDOM, fortune) != Item.getItemFromBlock(this)) {
            return 1 + RANDOM.nextInt(5);
        }
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (this.isLit) {
            this.addParticles(world, pos);
        }
    }

    private void addParticles(World world, BlockPos pos) {
        Random random = world.rand;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i) {
            double x = (double) ((float) pos.getX() + random.nextFloat());
            double y = (double) ((float) pos.getY() + random.nextFloat());
            double z = (double) ((float) pos.getZ() + random.nextFloat());

            if (i == 0 && !world.getBlockState(pos.up()).isOpaqueCube()) {
                y = (double) pos.getY() + d0 + 1.0D;
            }

            if (i == 1 && !world.getBlockState(pos.down()).isOpaqueCube()) {
                y = (double) pos.getY() - d0;
            }

            if (i == 2 && !world.getBlockState(pos.south()).isOpaqueCube()) {
                z = (double) pos.getZ() + d0 + 1.0D;
            }

            if (i == 3 && !world.getBlockState(pos.north()).isOpaqueCube()) {
                z = (double) pos.getZ() - d0;
            }

            if (i == 4 && !world.getBlockState(pos.east()).isOpaqueCube()) {
                x = (double) pos.getX() + d0 + 1.0D;
            }

            if (i == 5 && !world.getBlockState(pos.west()).isOpaqueCube()) {
                x = (double) pos.getX() - d0;
            }

            if (x < (double) pos.getX() || x > (double) (pos.getX() + 1) || y < 0.0D || y > (double) (pos.getY() + 1) || z < (double) pos.getZ() || z > (double) (pos.getZ() + 1)) {
                world.addParticle(ParticleTypes.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    @Nonnull
    protected ItemStack getSilkTouchDrop(@Nonnull BlockState state) {
        return new ItemStack(AtumBlocks.REDSTONE_ORE);
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(@Nonnull BlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, PlayerEntity player) {
        return new ItemStack(AtumBlocks.REDSTONE_ORE, 1, this.damageDropped(state));
    }

    @Override
    public void getOreDictEntries() {
        if (!this.isLit) {
            OreDictHelper.add(this, "ore", Objects.requireNonNull(this.getRegistryName()).getPath().replace("_ore", ""));
        }
    }
}