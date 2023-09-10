package com.github.lockoct.command;

import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * 命令根路由
 * 继承该类作为插件所有命令的入口
 * 负责将用户输入的命令路由到对应的命令处理类
 */
abstract public class BaseCommandRouter implements TabExecutor {
    protected final ArrayList<String> helpStrList = new ArrayList<>();

    // 输出单条帮助信息
    public void doHelp(Player player, String helpStr) {
        player.sendMessage(helpStr);
    }

    // 输出多条帮助信息
    // 常用于输出所有命令帮助
    public void doHelp(Player player) {
        helpStrList.forEach(player::sendMessage);
    }
}
