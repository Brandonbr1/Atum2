package com.teammetallurgy.atum.entity.projectile.arrow;

import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.init.AtumParticles;
import com.teammetallurgy.atum.utils.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityArrowPoison extends CustomArrow {

    public EntityArrowPoison(World world) {
        super(world);
    }

    public EntityArrowPoison(World world, LivingEntity shooter) {
        super(world, shooter);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (shootingEntity instanceof EntityPlayer && world.getTotalWorldTime() % 4L == 0L) {
            Atum.proxy.spawnParticle(AtumParticles.Types.SETH, this, posX, posY - 0.05D, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHit(RayTraceResult raytraceResult) {
        Entity entity = raytraceResult.entityHit;
        if (raytraceResult != null && entity instanceof LivingEntity && !world.isRemote && raytraceResult.typeOfHit == RayTraceResult.Type.ENTITY) {
            LivingEntity livingBase = (LivingEntity) entity;
            livingBase.addPotionEffect(new PotionEffect(MobEffects.POISON, 80, 0, false, true));
        }
        super.onHit(raytraceResult);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Constants.MOD_ID, "textures/arrow/arrow_poison.png");
    }
}