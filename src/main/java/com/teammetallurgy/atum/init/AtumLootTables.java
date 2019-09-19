package com.teammetallurgy.atum.init;

import com.teammetallurgy.atum.utils.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTables;

public class AtumLootTables {
    //Entities
    public static final ResourceLocation ASSASSIN = register("entities/assassin");
    public static final ResourceLocation BARBARIAN = register("entities/barbarian");
    public static final ResourceLocation BONESTORM = register("entities/bonestorm");
    public static final ResourceLocation BRIGAND = register("entities/brigand");
    public static final ResourceLocation CAMEL = register("entities/camel");
    public static final ResourceLocation DESERT_WOLF = register("entities/desert_wolf");
    public static final ResourceLocation DESERT_WOLF_ALPHA = register("entities/desert_wolf_alpha");
    public static final ResourceLocation FORSAKEN = register("entities/forsaken");
    public static final ResourceLocation MUMMY = register("entities/mummy");
    public static final ResourceLocation NOMAD = register("entities/nomad");
    public static final ResourceLocation SCARAB = register("entities/scarab");
    public static final ResourceLocation SCARAB_GOLDEN = register("entities/scarab_golden");
    public static final ResourceLocation STONEGUARD = register("entities/stoneguard");
    public static final ResourceLocation STONEWARDEN = register("entities/stonewarden");
    public static final ResourceLocation SUNSPEAKER = register("entities/sunspeaker");
    public static final ResourceLocation TARANTULA = register("entities/tarantula");
    public static final ResourceLocation WARLORD = register("entities/warlord");
    public static final ResourceLocation WRAITH = register("entities/wraith");

    //Fishing
    public static final ResourceLocation ATUMS_BOUNTY = register("gameplay/fishing/atums_bounty_fish");
    public static final ResourceLocation FISHING = register("gameplay/fishing");

    //Container Loot
    public static final ResourceLocation CRATE = register("chests/crate");
    public static final ResourceLocation CRATE_BONUS = register("chests/crate_bonus");
    public static final ResourceLocation GIRAFI_TOMB = register("chests/girafi_tomb");
    public static final ResourceLocation LIGHTHOUSE = register("chests/lighthouse");
    public static final ResourceLocation PHARAOH = register("chests/pharaoh");
    public static final ResourceLocation PYRAMID_CHEST = register("chests/pyramid_chest");
    public static final ResourceLocation SARCOPHAGUS_ARTIFACT = register("chests/sarcophagus_artifact");
    public static final ResourceLocation TOMB_CHEST = register("chests/tomb");

    //Blocks
    public static final ResourceLocation RELIC = register("blocks/relic_ore");

    private static ResourceLocation register(String path) {
        return LootTables.register(new ResourceLocation(Constants.MOD_ID, path));
    }
}