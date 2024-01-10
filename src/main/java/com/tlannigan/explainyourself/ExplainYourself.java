package com.tlannigan.explainyourself;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExplainYourself.MODID)
public class ExplainYourself {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "explainyourself";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExplainYourself() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info(MODID);
        Config.savedMods.forEach(LOGGER::info);

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
    public static class ServerModEvents {
        @SubscribeEvent
        public static void onClientSetup(ServerStartedEvent event) {
            LOGGER.info(MODID + ": Dedicated Server");
            Config.savedMods.forEach(LOGGER::info);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info(MODID + ": Client");
            Config.savedMods.forEach(LOGGER::info);
        }
    }

    private List<String> getModFileNames() {
        return ModList.get().getModFiles()
                .stream()
                .map(file -> file.getFile().getFileName())
                .collect(Collectors.toList());
    }

    private List<String> getChangedModsFileNames() {
        List<String> currentModFileNames = getModFileNames();

        List<String> removedMods = List.of();
        List<String> 
    }

    private void saveCurrentModList() {
        if (Config.savedMods.isEmpty()) {
            Config.savedMods = getModFileNames();
        }
    }
}
