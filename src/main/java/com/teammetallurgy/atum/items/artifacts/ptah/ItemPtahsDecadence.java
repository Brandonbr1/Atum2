package com.teammetallurgy.atum.items.artifacts.ptah;

import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.init.AtumItems;
import com.teammetallurgy.atum.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ItemPtahsDecadence extends ItemPickaxe {

    public ItemPtahsDecadence() {
        super(ToolMaterial.DIAMOND);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return true;
    }

    @SubscribeEvent
    public static void harvestEvent(BlockEvent.HarvestDropsEvent event) {
        World world = event.getWorld();
        if (!world.isRemote && event.getHarvester() != null && event.getHarvester().getHeldItemMainhand().getItem() == AtumItems.PTAHS_DECADENCE) {
            Item itemDropped = event.getState().getBlock().getItemDropped(event.getState(), itemRand, 0);
            if (itemDropped == null || itemDropped == Items.AIR) return;
            Block dropBlock = Block.getBlockFromItem(itemDropped);
            if ((dropBlock == AtumBlocks.IRON_ORE || dropBlock == Blocks.IRON_ORE) && world.rand.nextFloat() <= 0.50F) {
                if (dropBlock == AtumBlocks.IRON_ORE) {
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(AtumBlocks.GOLD_ORE));
                    world.playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else {
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Blocks.GOLD_ORE));
                    world.playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
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

    @Override
    @Nonnull
    public EnumRarity getRarity(@Nonnull ItemStack stack) {
        return EnumRarity.RARE;
    }
}