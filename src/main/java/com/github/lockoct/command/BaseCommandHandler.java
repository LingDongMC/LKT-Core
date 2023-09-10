package com.github.lockoct.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * 命令处理类
 * 对用户输入的命令进行处理
 * 既可以将命令在当前类中进行处理，也可以充当路由角色，将子命令分发给对应的子命令处理器进行处理
 */
public abstract class BaseCommandHandler {
    protected final ArrayList<String> helpStrList = new ArrayList<>();

    // 处理子命令
    public abstract void execute(CommandSender sender, String[] args);

    // 输出单条帮助信息
    public void doHelp(Player player, String helpStr) {
        player.sendMessage(helpStr);
    }

    // 输出多条帮助信息
    // 常用于输出所有子命令帮助
    public void doHelp(Player player) {
        helpStrList.forEach(player::sendMessage);
    }
}
