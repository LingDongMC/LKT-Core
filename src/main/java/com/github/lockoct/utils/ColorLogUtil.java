package com.github.lockoct.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ColorLogUtil {

    public static void logSuccess(JavaPlugin plugin, String msg) {
        colorLog(plugin, ChatColor.GREEN, msg);
    }

    public static void logWarning(JavaPlugin plugin, String msg) {
        colorLog(plugin, ChatColor.YELLOW, msg);
    }

    public static void logError(JavaPlugin plugin, String msg) {
        colorLog(plugin, ChatColor.RED, msg);
    }

    private static void colorLog(JavaPlugin plugin, ChatColor color, String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(color + "[" + plugin.getName() + "] " + msg);
    }

}
