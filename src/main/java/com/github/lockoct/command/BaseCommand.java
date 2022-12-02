package com.github.lockoct.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

abstract public class BaseCommand {
    public abstract void execute(CommandSender sender, String[] args);

    public void doHelp(Player player, String helpStr) {
        player.sendMessage(helpStr);
    }

    public void doHelp(Player player, ArrayList<String> helpStrList) {
        helpStrList.forEach(player::sendMessage);
    }
}
