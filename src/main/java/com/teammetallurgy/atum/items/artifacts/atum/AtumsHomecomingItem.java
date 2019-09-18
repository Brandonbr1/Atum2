package com.teammetallurgy.atum.items.artifacts.atum;

import com.teammetallurgy.atum.Atum;
import com.teammetallurgy.atum.init.AtumParticles;
import com.teammetallurgy.atum.items.AmuletItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class AtumsHomecomingItem extends AmuletItem {

    public AtumsHomecomingItem() {
        super();
        this.setMaxDamage(20);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        BlockPos pos = player.getBedLocation(player.dimension);
        if (pos == null) {
            BlockPos spawnPointPos = player.world.getSpawnPoint();
            while (spawnPointPos.getY() > 1 && world.isAirBlock(spawnPointPos)) {
                spawnPointPos = spawnPointPos.down();
            }
            while (!world.canSeeSky(spawnPointPos)) {
                spawnPointPos = spawnPointPos.up();
            }
            pos = new BlockPos(spawnPointPos.getX(), spawnPointPos.getY(), spawnPointPos.getZ());
        }
        teleport(world, player, hand, pos.getX(), pos.getY(), pos.getZ());
        if (!player.isCreative()) {
            heldStack.damageItem(1, player);
        }
        onTeleport(world, player);
        return new ActionResult<>(EnumActionResult.PASS, heldStack);
    }

    private static void teleport(World world, Entity entity, EnumHand hand, int x, int y, int z) {
        float yaw = entity.rotationYaw;
        float pitch = entity.rotationPitch;
        if (entity instanceof EntityPlayerMP) {
            Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);
            float f = MathHelper.wrapDegrees(yaw);
            float f1 = MathHelper.wrapDegrees(pitch);

            entity.dismountRidingEntity();
            onTeleport(world, entity);
            ((EntityPlayerMP) entity).connection.setPlayerLocation(x, y, z, f, f1, set);
            entity.setRotationYawHead(f);
        } else {
            float f2 = MathHelper.wrapDegrees(yaw);
            float f3 = MathHelper.wrapDegrees(pitch);
            f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
            entity.setLocationAndAngles(x, y, z, f2, f3);
            entity.setRotationYawHead(f2);
        }

        if (!(entity instanceof LivingEntity) || !((LivingEntity) entity).isElytraFlying()) {
            entity.motionY = 0.0D;
            entity.onGround = true;
        }
    }

    private static void onTeleport(World world, Entity entity) {
        for (int amount = 0; amount < 250; ++amount) {
            float timesRandom = world.rand.nextFloat() * 4.0F;
            float cosRandom = world.rand.nextFloat() * ((float) Math.PI * 2F);
            double x = (double) (MathHelper.cos(cosRandom) * timesRandom);
            double y = 0.01D + world.rand.nextDouble() * 0.5D;
            double z = (double) (MathHelper.sin(cosRandom) * timesRandom);
            if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP) entity;
                Atum.proxy.spawnParticle(AtumParticles.Types.LIGHT_SPARKLE, playerMP, entity.posX + x * 0.1D, entity.posY + 0.3D, entity.posZ + z * 0.1D, x, y, z);
            }
        }
        world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World serverWorld, List<String> tooltip, ITooltipFlag tooltipType) {
        if (Keyboard.isKeyDown(42)) {
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format(this.getTranslationKey() + ".line1"));
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format(this.getTranslationKey() + ".line2"));
        } else {
            tooltip.add(I18n.format(this.getTranslationKey() + ".line3") + " " + TextFormatting.DARK_GRAY + "[SHIFT]");
        }
    }
}