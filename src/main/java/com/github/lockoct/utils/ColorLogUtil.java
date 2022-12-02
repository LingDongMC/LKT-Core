package com.github.lockoct.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ColorLogUtil {

    public static void logSuccess(JavaPlugin plugin, String msg) {
        colourLog(plugin, ChatColor.GREEN, msg);
    }

    public static void logError(JavaPlugin plugin, String msg) {
        colourLog(plugin, ChatColor.RED, msg);
    }

    private static void colourLog(JavaPlugin plugin, ChatColor color, String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(color + "[" + plugin.getName() + "] " + msg);
    }

}
