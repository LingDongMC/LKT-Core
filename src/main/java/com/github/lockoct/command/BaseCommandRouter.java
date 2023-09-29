package com.github.lockoct.command;

import com.github.lockoct.entity.BasePlugin;
import com.github.lockoct.utils.I18nUtil;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/**
 * 命令根路由
 * 继承该类作为插件所有命令的入口
 * 负责将用户输入的命令路由到对应的命令处理类
 */
abstract public class BaseCommandRouter implements TabExecutor {

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
