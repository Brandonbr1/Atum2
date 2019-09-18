package com.teammetallurgy.atum.client.model.entity;

import com.teammetallurgy.atum.entity.bandit.EntityNomad;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelNomad extends ModelPlayer {

    public ModelNomad() {
        super(0.0F, false);
    }

    @Override
    public void setLivingAnimations(LivingEntity livingBase, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.rightArmPose = ModelBiped.ArmPose.EMPTY;
        this.leftArmPose = ModelBiped.ArmPose.EMPTY;
        ItemStack itemstack = livingBase.getHeldItem(EnumHand.MAIN_HAND);

        if (itemstack.getItem() instanceof ItemBow && ((EntityNomad) livingBase).isSwingingArms()) {
            if (livingBase.getPrimaryHand() == EnumHandSide.RIGHT) {
                this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
        }
        super.setLivingAnimations(livingBase, limbSwing, limbSwingAmount, partialTickTime);
    }
}
