package com.teammetallurgy.atum.entity.projectile.arrow;

import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.init.AtumEntities;
import com.teammetallurgy.atum.init.AtumParticles;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class ArrowQuickdrawEntity extends CustomArrow {

    public ArrowQuickdrawEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) {
        this(AtumEntities.QUICKDRAW_ARROW, world);
    }

    public ArrowQuickdrawEntity(EntityType<? extends ArrowQuickdrawEntity> entityType, Level world) {
        super(entityType, world);
    }

    public ArrowQuickdrawEntity(Level world, LivingEntity shooter) {
        super(AtumEntities.QUICKDRAW_ARROW, world, shooter);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Atum.MOD_ID, "textures/arrow/arrow_quickdraw.png");
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isCritArrow()) {
            if (level instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel) level;
                serverWorld.sendParticles(AtumParticles.SHU, this.getX() + (level.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), this.getY() + level.random.nextDouble() * (double) this.getBbHeight(), this.getZ() + (level.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), 1, 0.0D, 0.0D, 0.0D, 0.01D);
            }
        }
    }
}