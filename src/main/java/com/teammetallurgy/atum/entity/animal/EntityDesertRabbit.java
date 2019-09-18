package com.teammetallurgy.atum.entity.animal;

import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.init.AtumItems;
import com.teammetallurgy.atum.world.biome.*;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EntityDesertRabbit extends EntityRabbit {

    public EntityDesertRabbit(World world) {
        super(world);
        this.spawnableBlock = AtumBlocks.SAND;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32D);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, AtumItems.FLAX, false));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityDesertWolf.class, 16.0F, 2.2D, 2.6D));
    }

    @Override
    protected int getRandomRabbitType() {
        Biome biome = this.world.getBiome(new BlockPos(this));
        int i = this.rand.nextInt(100);

        if (biome instanceof BiomeSandPlains) {
            return i <= 80 ? 0 : 1;
        } else if (biome instanceof BiomeSandDunes) {
            return i <= 60 ? 1 : 2;
        } else if (biome instanceof BiomeSandHills) {
            return i <= 30 ? 1 : 2;
        } else if (biome instanceof BiomeLimestoneMountains) {
            return i <= 30 ? 2 : 3;
        } else if (biome instanceof BiomeLimestoneCrags) {
            return i <= 30 ? 3 : 4;
        } else if (biome instanceof BiomeDeadwoodForest) {
            return i <= 50 ? 2 : 3;
        } else if (biome instanceof BiomeOasis) {
            return i <= 50 ? 2 : 3;
        } else if (biome instanceof BiomeDeadOasis) {
            return i <= 33 ? 2 : (i <= 66 ? 3 : 4);
        } else if (biome instanceof BiomeDriedRiver) {
            return i <= 50 ? 1 : 2;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == AtumItems.FLAX || item == Items.CARROT || item == Items.GOLDEN_CARROT || item == Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
    }

    @Override
    public EntityDesertRabbit createChild(EntityAgeable ageable) {
        EntityDesertRabbit rabbit = new EntityDesertRabbit(this.world);
        int type = this.getRandomRabbitType();

        if (this.rand.nextInt(20) != 0) {
            if (ageable instanceof EntityDesertRabbit && this.rand.nextBoolean()) {
                type = ((EntityDesertRabbit) ageable).getRabbitType();
            } else {
                type = this.getRabbitType();
            }
        }
        rabbit.setRabbitType(type);
        return rabbit;
    }
}