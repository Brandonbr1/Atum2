package com.teammetallurgy.atum.entity.undead;

import com.teammetallurgy.atum.init.AtumLootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityWraith extends EntityUndeadBase {
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityWraith.class, DataSerializers.BYTE);
    private int cycleHeight;
    private int cycleTime;

    public EntityWraith(World world) {
        super(world);
        this.experienceValue = 6;
        this.setCanPickUpLoot(false);

        cycleTime = (int) ((Math.random() * 40) + 80);
        cycleHeight = (int) (Math.random() * cycleTime);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new EntityWraith.AIWraithAttack(this));
    }

    protected void applyEntityAI() {
        super.applyEntityAI();
        this.targetTasks.addTask(1, new EntityWraith.AIWraithTarget<>(this, PlayerEntity.class));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CLIMBING, (byte) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
    }

    @Override
    @Nonnull
    protected PathNavigate createNavigator(@Nonnull World world) {
        return new PathNavigateClimber(this, world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_HUSK_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_HUSK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HUSK_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    @Override
    public void onLivingUpdate() {
        cycleHeight = (cycleHeight + 1) % cycleTime;

        super.onLivingUpdate();
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return AtumLootTables.WRAITH;
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    private boolean isBesideClimbableBlock() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    private void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.dataManager.get(CLIMBING);

        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.dataManager.set(CLIMBING, b0);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (!super.attackEntityAsMob(entity)) {
            return false;
        } else {
            if (rand.nextDouble() <= 0.175D) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingBase = (LivingEntity) entity;
                    if (!livingBase.isPotionActive(Effects.SLOWNESS)) {
                        livingBase.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 80, 1));
                    }
                }
            }
            return true;
        }
    }

    private static class AIWraithAttack extends EntityAIAttackMelee {
        AIWraithAttack(EntityWraith wraith) {
            super(wraith, 1.0D, true);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double) (4.0F + attacktarget.getWidth());
        }
    }

    private static class AIWraithTarget<T extends LivingEntity> extends EntityAINearestAttackableTarget<T> {
        AIWraithTarget(EntityWraith wraith, Class<T> classTarget) {
            super(wraith, classTarget, true);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
