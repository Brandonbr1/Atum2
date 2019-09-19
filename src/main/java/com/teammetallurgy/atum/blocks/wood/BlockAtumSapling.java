package com.teammetallurgy.atum.blocks.wood;

import com.google.common.collect.Maps;
import com.teammetallurgy.atum.blocks.base.IRenderMapper;
import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.utils.AtumRegistry;
import com.teammetallurgy.atum.utils.IOreDictEntry;
import com.teammetallurgy.atum.utils.OreDictHelper;
import com.teammetallurgy.atum.world.gen.feature.WorldGenPalm;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Random;

public class BlockAtumSapling extends BlockBush implements IGrowable, IRenderMapper, IOreDictEntry {
    private static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    private static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.4125D, 0.0D, 0.4125D, 0.6D, 0.5D, 0.6D);
    private static final Map<BlockAtumPlank.WoodType, Block> SAPLINGS = Maps.newEnumMap(BlockAtumPlank.WoodType.class);

    private BlockAtumSapling() {
        super();
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
    }

    public static void registerSaplings() {
        for (BlockAtumPlank.WoodType type : BlockAtumPlank.WoodType.values()) {
            if (type != BlockAtumPlank.WoodType.DEADWOOD) {
                Block sapling = new BlockAtumSapling();
                SAPLINGS.put(type, sapling);
                AtumRegistry.registerBlock(sapling, type.getName() + "_sapling");
            }
        }
    }

    public static Block getSapling(BlockAtumPlank.WoodType type) {
        return SAPLINGS.get(type);
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        return state.getBlock() == AtumBlocks.FERTILE_SOIL || state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT;
    }

    @Override
    public void updateTick(World world, @Nonnull BlockPos pos, @Nonnull BlockState state, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, pos, state, rand);

            if (!world.isAreaLoaded(pos, 1)) return;
            if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(20) == 0) {
                this.grow(world, pos, state, rand);
            }
        }
    }

    private void grow(World world, BlockPos pos, BlockState state, Random random) {
        if (state.getValue(STAGE) == 0) {
            world.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else {
            this.generateTree(world, pos, state, random);
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        return blockDown == AtumBlocks.FERTILE_SOIL || blockDown == Blocks.GRASS || blockDown == Blocks.DIRT;
    }

    private void generateTree(World world, BlockPos pos, BlockState state, Random rand) {
        if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;
        WorldGenerator generator = (rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
        int i = 0;
        int j = 0;
        boolean flag = false;

        switch (BlockAtumPlank.WoodType.byIndex(BlockAtumPlank.WoodType.values().length)) {
            case PALM:
                generator = new WorldGenPalm(true);
                break;
            case DEADWOOD:
                break;
        }

        BlockState stateAir = Blocks.AIR.getDefaultState();

        if (flag) {
            world.setBlockState(pos.add(i, 0, j), stateAir, 4);
            world.setBlockState(pos.add(i + 1, 0, j), stateAir, 4);
            world.setBlockState(pos.add(i, 0, j + 1), stateAir, 4);
            world.setBlockState(pos.add(i + 1, 0, j + 1), stateAir, 4);
        } else {
            world.setBlockState(pos, stateAir, 4);
        }

        if (!generator.generate(world, rand, pos.add(i, 0, j))) {
            if (flag) {
                world.setBlockState(pos.add(i, 0, j), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j), state, 4);
                world.setBlockState(pos.add(i, 0, j + 1), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            } else {
                world.setBlockState(pos, state, 4);
            }
        }
    }

    @Override
    public IProperty[] getNonRenderingProperties() {
        return new IProperty[]{STAGE};
    }

    @Override
    public boolean canGrow(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return (double) world.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        this.grow(world, pos, state, rand);
    }

    @Override
    @Nonnull
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        int i = 0;
        i = i | state.getValue(STAGE) << 3;
        return i;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    public void getOreDictEntries() {
        OreDictHelper.add(this, "treeSapling");
    }
}