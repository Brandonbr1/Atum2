package com.teammetallurgy.atum.entity.projectile.arrow;

import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.init.AtumParticles;
import com.teammetallurgy.atum.utils.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityArrowSlowness extends CustomArrow {
    private float velocity;

    public EntityArrowSlowness(World world) {
        super(world);
    }

    public EntityArrowSlowness(World world, LivingEntity shooter, float velocity) {
        super(world, shooter);
        this.velocity = velocity;
    }

    @Override
    protected void onHit(RayTraceResult raytraceResult) {
        Entity entity = raytraceResult.entityHit;
        if (raytraceResult != null && entity instanceof LivingEntity && !world.isRemote && raytraceResult.typeOfHit == RayTraceResult.Type.ENTITY) {
            LivingEntity livingBase = (LivingEntity) entity;
            float chance = 0.25F;
            if (velocity == 1.0F) {
                chance = 1.0F;
            }
            if (rand.nextFloat() <= chance) {
                livingBase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 1, false, true));
                for (int amount = 0; amount < 25; ++amount) {
                    Atum.proxy.spawnParticle(AtumParticles.Types.GEB, entity, entity.posX + (world.rand.nextDouble() - 0.5D) * (double) entity.width, this.posY, entity.posZ + (world.rand.nextDouble() - 0.5D) * (double) entity.width, 0.0D, -0.06D, 0.0D);
                }
            }
        }
        super.onHit(raytraceResult);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Constants.MOD_ID, "textures/arrow/arrow_slowness.png");
    }
}