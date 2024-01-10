package com.tlannigan.betterhaveagoodreason;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.List;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ExplainYourself.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue IS_LOCKED = BUILDER
            .comment("Setting for modpack authors: lock the current list of saved mods")
            .define("locked", false);

    private static final ForgeConfigSpec.ConfigValue<List<String>> SAVED_MODS = BUILDER
            .comment("Exact list of mods you want the modpack to have")
            .define("savedMods", List.of());

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isLocked;
    public static List<String> savedMods;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        savedMods = SAVED_MODS.get();
        isLocked = IS_LOCKED.get();
    }
}
