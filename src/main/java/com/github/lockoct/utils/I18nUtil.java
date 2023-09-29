package com.github.lockoct.utils;

import com.github.lockoct.Main;
import com.github.lockoct.entity.BasePlugin;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class I18nUtil {

    public static void init(BasePlugin plugin) {
        String[] langFileList = PathUtil.getI18nFileNames(plugin);
        boolean findLang = false;

        // 获取配置文件指定的语言
        FileConfiguration config = plugin.getConfig();
        // 设置当前语言为配置文件指定语言
        String lang = config.getString("language");
        // 配置文件语言为空，使用本机语言为当前语言
        if (StringUtils.isBlank(lang)) {
            lang = Locale.getDefault().toString();
        }

        // 释放语言文件
        for (String langFile : langFileList) {
            String langStr = langFile.replace(".yml", "");
            String langFilePath = String.format("i18n/%s", langFile);

            // 检查插件文件夹是否已存在语言文件，有就跳过，没有就从jar包里复制一份
            File file = new File(plugin.getDataFolder() + "/" + langFilePath);
            if (!file.exists()) {
                plugin.saveResource(langFilePath, false);
            }

            // 加载语言配置到配置文件对象中
            HashMap<String, YamlConfiguration> langMap = plugin.getLangMap();
            langMap.put(langStr.toLowerCase(), YamlConfiguration.loadConfiguration(file));

            // 检查插件文件夹中的语言文件是否匹配当前语言
            if (lang.equals(langStr)) {
                findLang = true;
            }
        }

        // 确保控制台一定有一种语言能对应
        // 如果没有匹配的语言文件，改变当前语言为英语
        if (!findLang) {
            plugin.setLang(Locale.US.toString());
        } else {
            plugin.setLang(lang);
        }
    }

    // 国际化输出（发送者为控制台，仅在找不到语言文件，提示警告时有用）
    public static String getText(BasePlugin plugin, @Nonnull String key) {
        return getText(plugin, plugin.getServer().getConsoleSender(), key, (Object) null);
    }

    // 国际化输出带参数
    public static String getText(BasePlugin plugin, @Nonnull String key, Object... params) {
        return getText(plugin, plugin.getServer().getConsoleSender(), key, params);
    }

    // 国际化输出带发送者
    public static String getText(BasePlugin plugin, CommandSender sender, @Nonnull String key) {
        return getText(plugin, sender, key, (Object) null);
    }

    // 国际化输出带发送者和参数
    public static String getText(BasePlugin plugin, CommandSender sender, @Nonnull String key, Object... params) {
        Object[] info = check(plugin, sender);
        String locale = (String) info[0];

        String value = ((YamlConfiguration) info[1]).getString(key);
        // 通常情况下语言文件每一条都不会为空
        // 如果出现，就是服务器管理者自己破坏了内容，好自为之吧
        if (StringUtils.isBlank(value)) {
            ColorLogUtil.logError(plugin, String.format("%s.yml中的 %s 不允许为空 / The \"%s\" field in the %s.yml cannot be empty.", locale, key, key, locale));
            return "";
        }
        return String.format(value, params);
    }

    public static String getCommonText(CommandSender sender, @Nonnull String key) {
        return getText(Main.plugin, sender, key, (Object) null);
    }

    public static List<String> getTextList(BasePlugin plugin, CommandSender sender, @Nonnull String key) {
        Object[] info = check(plugin, sender);
        return ((YamlConfiguration) info[1]).getStringList(key);
    }

    private static Object[] check(BasePlugin plugin, CommandSender sender) {
        // 获取玩家或控制台区域
        String locale = sender instanceof Player player ? player.getLocale() : plugin.getLang();
        // 加载该区域语言配置
        HashMap<String, YamlConfiguration> langMap = plugin.getLangMap();
        YamlConfiguration langConf = langMap.get(locale.toLowerCase());

        // BasePlugin corePlugin = Main.plugin;

        // 根据玩家所在区域展示语言
        if (sender instanceof Player player) {
            // 如果没有对应配置，默认使用英文
            if (langConf == null) {
                langConf = langMap.get(Locale.US.toString().toLowerCase());

                // 获取语言不支持提示信息
                // String notSupportStr = getText(corePlugin, corePlugin.getServer().getConsoleSender(), "pluginMsg.notSupportLangWarning", player.getName(), locale);

                // 告诉管理者插件缺少对该玩家区域语言的支持
                // ColorLogUtil.logWarning(plugin, notSupportStr);
            }
        }

        return new Object[]{locale, langConf};
    }
}
