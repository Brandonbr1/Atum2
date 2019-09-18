package com.teammetallurgy.atum.items.artifacts.horus;

import com.teammetallurgy.atum.entity.projectile.arrow.EntityArrowStraight;
import com.teammetallurgy.atum.items.tools.BaseBowItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class HorusSoaringItem extends BaseBowItem {

    public HorusSoaringItem() {
        super();
        this.setMaxDamage(650);
        this.setRepairItem(Items.DIAMOND);
    }

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    @Nonnull
    public EnumRarity getRarity(@Nonnull ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    protected EntityArrow setArrow(ItemStack stack, World world, EntityPlayer player, float velocity) {
        return new EntityArrowStraight(world, player, velocity);
    }

    @Override
    protected void onShoot(EntityArrow arrow, EntityPlayer player, float velocity) {
        arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity * 2.0F, 0.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag tooltipType) {
        if (Keyboard.isKeyDown(42)) {
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format(this.getTranslationKey() + ".line1"));
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format(this.getTranslationKey() + ".line2"));
        } else {
            tooltip.add(I18n.format(this.getTranslationKey() + ".line3") + " " + TextFormatting.DARK_GRAY + "[SHIFT]");
        }
    }
}