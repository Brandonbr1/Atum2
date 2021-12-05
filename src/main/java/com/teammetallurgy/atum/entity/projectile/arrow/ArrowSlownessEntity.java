package com.teammetallurgy.atum.entity.projectile.arrow;

import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.init.AtumEntities;
import com.teammetallurgy.atum.init.AtumParticles;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;

public class ArrowSlownessEntity extends CustomArrow {
    private float velocity;

    public ArrowSlownessEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) {
        this(AtumEntities.SLOWNESS_ARROW, world);
    }

    public ArrowSlownessEntity(EntityType<? extends ArrowSlownessEntity> entityType, Level world) {
        super(entityType, world);
    }

    public ArrowSlownessEntity(Level world, LivingEntity shooter, float velocity) {
        super(AtumEntities.SLOWNESS_ARROW, world, shooter);
        this.velocity = velocity;
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult rayTraceResult) {
        Entity entity = rayTraceResult.getEntity();
        if (!level.isClientSide && entity instanceof LivingEntity) {
            LivingEntity livingBase = (LivingEntity) entity;
            float chance = 0.25F;
            if (velocity == 1.0F) {
                chance = 1.0F;
            }
            if (random.nextFloat() <= chance) {
                livingBase.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, true));
                if (level instanceof ServerLevel) {
                    ServerLevel serverWorld = (ServerLevel) level;
                    serverWorld.sendParticles(AtumParticles.GEB, entity.getX(), this.getY(), entity.getZ(), 15, 0.0D, -0.06D, 0.0D, 0.025D);
                }
            }
        }
        super.onHitEntity(rayTraceResult);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Atum.MOD_ID, "textures/arrow/arrow_slowness.png");
    }
}