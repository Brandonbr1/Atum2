package com.teammetallurgy.atum.init;

import com.teammetallurgy.atum.entity.EntityHeartOfRa;
import com.teammetallurgy.atum.entity.animal.*;
import com.teammetallurgy.atum.entity.bandit.*;
import com.teammetallurgy.atum.entity.efreet.EntitySunspeaker;
import com.teammetallurgy.atum.entity.projectile.EntityCamelSpit;
import com.teammetallurgy.atum.entity.projectile.EntitySmallBone;
import com.teammetallurgy.atum.entity.projectile.arrow.*;
import com.teammetallurgy.atum.entity.stone.EntityStoneguard;
import com.teammetallurgy.atum.entity.stone.EntityStonewarden;
import com.teammetallurgy.atum.entity.undead.*;
import com.teammetallurgy.atum.utils.Constants;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

import static com.teammetallurgy.atum.utils.AtumRegistry.*;

@ObjectHolder(value = Constants.MOD_ID)
public class AtumEntities {
    //Mobs
    public static final EntityType<EntityAssassin> ASSASSIN = registerMob(EntityAssassin.class, 0x433731, 0xd99220);
    public static final EntityType BANDIT_WARLORD = registerMob(EntityWarlord.class, 0xa62d1b, 0xe59a22);
    public static final EntityType BARBARIAN = registerMob(EntityBarbarian.class, 0x9c7359, 0x8c8c8c);
    public static final EntityType BONESTORM = registerMob(EntityBonestorm.class, 0x74634e, 0xab9476);
    public static final EntityType BRIGAND = registerMob(EntityBrigand.class, 0xC2C2C2, 0x040F85);
    public static final EntityType CAMEL = registerMob(EntityCamel.class, 0xAD835C, 0x684626);
    public static final EntityType DESERT_WOLF = registerMob(EntityDesertWolf.class, 0xE7DBC8, 0xAD9467);
    public static final EntityType FORSAKEN = registerMob(EntityForsaken.class, 0xB59C7D, 0x6F5C43);
    public static final EntityType MUMMY = registerMob(EntityMummy.class, 0x515838, 0x868F6B);
    public static final EntityType NOMAD = registerMob(EntityNomad.class, 0xC2C2C2, 0x7E0C0C);
    public static final EntityType PHARAOH = registerMob(EntityPharaoh.class, 0xD4BC37, 0x3A4BE0);
    public static final EntityType RABBIT = registerMob(EntityDesertRabbit.class, 0xAE8652, 0x694C29);
    public static final EntityType SCARAB = registerMob(EntityScarab.class, 0x61412C, 0x2F1D10);
    public static final EntityType STONEGUARD = registerMob(EntityStoneguard.class, 0x918354, 0x695D37);
    public static final EntityType STONEWARDEN = registerMob(EntityStonewarden.class, 0x918354, 0x695D37);
    public static final EntityType SUNSPEAKER = registerMob(EntitySunspeaker.class, 0x464646, 0xCC5654);
    public static final EntityType TARANTULA = registerMob(EntityTarantula.class, 0x745c47, 0xd2b193);
    public static final EntityType WRAITH = registerMob(EntityWraith.class, 0x544d34, 0x3e3927);

    //Entities
    public static final EntityType CAMEL_SPIT = registerEntity(EntityCamelSpit.class, 64, 10, false);
    public static final EntityType DOUBLE_SHOT_BLACK = registerArrow(EntityArrowDoubleShotBlack.class);
    public static final EntityType DOUBLE_SHOT_WHITE = registerArrow(EntityArrowDoubleShotWhite.class);
    public static final EntityType EXPLOSIVE_ARROW = registerArrow(EntityArrowExplosive.class);
    public static final EntityType FIRE_ARROW = registerArrow(EntityArrowFire.class);
    public static final EntityType HEART_OF_RA = registerEntity(EntityHeartOfRa.class, 256, Integer.MAX_VALUE, false);
    public static final EntityType POISON_ARROW = registerArrow(EntityArrowPoison.class);
    public static final EntityType QUICKDRAW_ARROW = registerArrow(EntityArrowQuickdraw.class);
    public static final EntityType RAIN_ARROW = registerArrow(EntityArrowRain.class);
    public static final EntityType SLOWNESS_ARROW = registerArrow(EntityArrowSlowness.class);
    public static final EntityType SMALL_BONE = registerEntity(EntitySmallBone.class, 64, 1, true);
    public static final EntityType STRAIGHT_ARROW = registerArrow(EntityArrowStraight.class);
    public static final EntityType TEFNUTS_CALL = registerArrow(EntityTefnutsCall.class);
}