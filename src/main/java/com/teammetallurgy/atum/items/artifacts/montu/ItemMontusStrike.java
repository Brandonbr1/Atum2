package com.teammetallurgy.atum.items.artifacts.montu;

import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.init.AtumItems;
import com.teammetallurgy.atum.init.AtumParticles;
import com.teammetallurgy.atum.items.tools.ItemBattleAxe;
import com.teammetallurgy.atum.utils.Constants;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.hash.TObjectFloatHashMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ItemMontusStrike extends ItemBattleAxe {
    private static final TObjectFloatMap<EntityPlayer> cooldown = new TObjectFloatHashMap<>();

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return 7200;
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onAttack(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (player.world.isRemote) return;
        if (event.getTarget() instanceof EntityLivingBase) {
            if (player.getHeldItemMainhand().getItem() == AtumItems.MONTUS_STRIKE) {
                cooldown.put(player, player.getCooledAttackStrength(0.5F));
            }
        }
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, EntityLivingBase target, @Nullable EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer && cooldown.containsKey(attacker)) {
            if (cooldown.get(attacker) == 1.0F) {
                EntityPlayer player = (EntityPlayer) attacker;
                World world = player.world;
                float damage = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();

                for (EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D))) {
                    if (entity != player && entity != target && !player.isOnSameTeam(entity) && player.getDistanceSq(entity) < 12.0D) {
                        entity.knockBack(player, 1.0F + EnchantmentHelper.getKnockbackModifier(player), (double) MathHelper.sin(player.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                        for (int amount = 0; amount < 20; amount++) {
                            double d0 = (double) (-MathHelper.sin(player.rotationYaw * 0.017453292F));
                            double d1 = (double) MathHelper.cos(player.rotationYaw * 0.017453292F);
                            Atum.proxy.spawnParticle(AtumParticles.Types.MONTU, target, target.posX + d0, target.posY + 1.1D, target.posZ + d1, 0.0D, 0.0D, 0.0D);
                            Atum.proxy.spawnParticle(AtumParticles.Types.MONTU, entity, entity.posX + d0, entity.posY + 1.1D, entity.posZ + d1, 0.0D, 0.0D, 0.0D);
                        }
                        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                    }
                }
            }
            cooldown.remove(attacker);
        }
        return super.hitEntity(stack, target, attacker);
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