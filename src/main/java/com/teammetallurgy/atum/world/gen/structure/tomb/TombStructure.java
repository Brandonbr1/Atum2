package com.teammetallurgy.atum.world.gen.structure.tomb;

import com.mojang.serialization.Codec;
import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.world.DimensionHelper;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public class TombStructure extends StructureFeature<NoneFeatureConfiguration> {

    public TombStructure(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    @Override
    protected boolean isFeatureChunk(@Nonnull ChunkGenerator generator, @Nonnull BiomeSource provider, long seed, @Nonnull WorldgenRandom seedRandom, int chunkX, int chunkZ, @Nonnull Biome biome, @Nonnull ChunkPos chunkPos, @Nonnull NoneFeatureConfiguration config) {
        for (Biome b : provider.getBiomesWithin(chunkX * 16 + 9, DimensionHelper.GROUND_LEVEL, chunkZ * 16 + 9, 20)) {
            return b.getGenerationSettings().isValidStart(this);
        }
        return false;
    }

    @Override
    @Nonnull
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return Start::new;
    }

    public static class Start extends StructureStart<NoneFeatureConfiguration> {

        public Start(StructureFeature<NoneFeatureConfiguration> structure, int chunkPosX, int chunkPosZ, BoundingBox box, int references, long seed) {
            super(structure, chunkPosX, chunkPosZ, box, references, seed);
        }

        @Override
        public void generatePieces(@Nonnull RegistryAccess registries, @Nonnull ChunkGenerator generator, @Nonnull StructureManager manager, int chunkX, int chunkZ, @Nonnull Biome biome, @Nonnull NoneFeatureConfiguration config) {
            Rotation rotation = Rotation.getRandom(this.random);
            int y = Mth.nextInt(this.random, 8, 55);
            BlockPos pos = new BlockPos(chunkX * 16 + 8, y, chunkZ * 16 + 8);
            TombPieces.TombTemplate tomb = new TombPieces.TombTemplate(manager, pos, rotation);
            this.pieces.add(tomb);
            this.calculateBoundingBox();
        }

        @Override
        public void placeInChunk(@Nonnull WorldGenLevel seedReader, @Nonnull StructureFeatureManager manager, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BoundingBox box, @Nonnull ChunkPos chunkPos) {
            super.placeInChunk(seedReader, manager, generator, rand, box, chunkPos);
            int y = this.boundingBox.y0;

            for (int x = box.x0; x <= box.x1; ++x) {
                for (int z = box.z0; z <= box.z1; ++z) {
                    BlockPos pos = new BlockPos(x, y, z);

                    if (!seedReader.isEmptyBlock(pos) && this.boundingBox.isInside(pos)) {
                        boolean isVecInside = false;

                        for (StructurePiece piece : this.pieces) {
                            if (piece.getBoundingBox().isInside(pos)) {
                                isVecInside = true;
                                break;
                            }
                        }

                        if (isVecInside) {
                            for (int tombY = y - 1; tombY > 1; --tombY) {
                                BlockPos tombPos = new BlockPos(x, tombY, z);

                                if (!seedReader.isEmptyBlock(tombPos) && !seedReader.getBlockState(tombPos).getMaterial().isLiquid()) {
                                    break;
                                }
                                seedReader.setBlock(tombPos, AtumBlocks.LIMESTONE_BRICK_LARGE.defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
        }
    }
}