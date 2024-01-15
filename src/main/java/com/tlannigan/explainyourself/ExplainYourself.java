package com.tlannigan.explainyourself;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod(ExplainYourself.MODID)
public class ExplainYourself {
    public static final String MODID = "explainyourself";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExplainYourself() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        saveCurrentModList();
        printChangedMods();
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
    public static class ServerModEvents {
        @SubscribeEvent
        public static void onServerSetup(FMLDedicatedServerSetupEvent event) {
            LOGGER.info(MODID + ": Dedicated Server - " + event.getClass().getSimpleName());
        }

        @SubscribeEvent
        public static void OnRegisterCommands(RegisterCommandsEvent event) {
            // Register commands here
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info(MODID + ": Client - " + event.getClass().getSimpleName());
        }
    }

    private void saveCurrentModList() {
        if (!Config.isLocked) {
            Config.savedMods = getModFileNames();
        }
    }

    private void printChangedMods() {
        ChangedMods changedMods = getChangedModsFileNames();

        if (!changedMods.getRemovedMods().isEmpty()) {
            LOGGER.info("Removed mods:");
            changedMods.getRemovedMods().forEach(LOGGER::info);
        }

        if (!changedMods.getAddedMods().isEmpty()) {
            LOGGER.info("Added mods:");
            changedMods.getAddedMods().forEach(LOGGER::info);
        }
    }

    private ChangedMods getChangedModsFileNames() {
        List<String> expected = Config.savedMods;
        List<String> current = getModFileNames();

        return new ChangedMods(
            getItemsNotPresentInFirstList(expected, current),
            getItemsNotPresentInFirstList(current, expected)
        );
    }

    private List<String> getModFileNames() {
        return ModList.get().getModFiles()
                .stream()
                .map(file -> file.getFile().getFileName())
                .collect(Collectors.toList());
    }

    private List<String> getItemsNotPresentInFirstList(List<String> firstList, List<String> secondList) {
        List<String> changedItems = new ArrayList<>();
        secondList.forEach(v -> {
            if (!firstList.contains(v)) {
                changedItems.add(v);
            }
        });
        return changedItems;
    }
}
