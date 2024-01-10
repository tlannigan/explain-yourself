package com.tlannigan.explainyourself;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Commands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(net.minecraft.commands.Commands.literal("explainyourself")
                .then(net.minecraft.commands.Commands.literal("update")));
    }

    private static int updateConfiguration(CommandSourceStack source) {
        Player player = source.getPlayer();
        if (player != null) {
            Component message = Component.literal("Updating configuration for the current set of mods. Changes to the current mod list WILL be logged.");
            player.sendSystemMessage(message);
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}
