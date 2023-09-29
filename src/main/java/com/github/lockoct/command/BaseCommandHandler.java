package com.github.lockoct.command;

import com.github.lockoct.entity.BasePlugin;
import com.github.lockoct.utils.I18nUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 命令处理类
 * 对用户输入的命令进行处理
 * 既可以将命令在当前类中进行处理，也可以充当路由角色，将子命令分发给对应的子命令处理器进行处理
 */
public abstract class BaseCommandHandler {

    // 处理子命令
    public abstract void execute(CommandSender sender, String[] args);

    // 输出单条帮助信息
    public void doHelpSingle(BasePlugin plugin, Player player, String key) {
        player.sendMessage(I18nUtil.getText(plugin, player, key));
    }

    // 输出多条帮助信息
    // 常用于输出所有命令帮助
    public void doHelp(BasePlugin plugin, Player player, String key) {
        I18nUtil.getTextList(plugin, player, key).forEach(player::sendMessage);
    }
}
