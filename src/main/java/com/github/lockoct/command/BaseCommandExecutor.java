package com.github.lockoct.command;

import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

abstract public class BaseCommandExecutor implements TabExecutor {
    public void doHelp(Player player, String helpStr) {
        player.sendMessage(helpStr);
    }

    public void doHelp(Player player, ArrayList<String> helpStrList) {
        helpStrList.forEach(player::sendMessage);
    }
}
