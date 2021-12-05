package com.teammetallurgy.atum.client.render.entity.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.entity.animal.TarantulaEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class TarantulaRender<T extends TarantulaEntity> extends MobRenderer<T, SpiderModel<T>> {
    private static final ResourceLocation TARANTULA_TEXTURE = new ResourceLocation(Atum.MOD_ID, "textures/entity/tarantula.png");

    public TarantulaRender(EntityRenderDispatcher manager) {
        super(manager, new SpiderModel<>(), 0.6F);
    }

    @Override
    protected float getFlipDegrees(T tarantula) {
        return 180.0F;
    }

    @Override
    protected void scale(T tarantula, PoseStack matrixStack, float partialTickTime) {
        matrixStack.scale(0.6F, 0.6F, 0.6F);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull T tarantula) {
        return TARANTULA_TEXTURE;
    }
}