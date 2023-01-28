package com.github.lockoct;

import com.github.lockoct.utils.ColorLogUtil;
import com.github.lockoct.utils.DatabaseUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.nutz.dao.Dao;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main extends JavaPlugin {
    public static Main plugin;

    private Dao dao;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            this.dao = DatabaseUtil.initDB();
            if (this.dao != null) {
                ColorLogUtil.logSuccess(this, "数据库连接成功");
            }
        } catch (Exception e) {
            ColorLogUtil.logError(this, "数据库连接失败");
            e.printStackTrace();
        }
        // 保存配置文件
        saveDefaultConfig();

        // 启动定时任务
        try {
            StdSchedulerFactory.getDefaultScheduler().start();
        } catch (SchedulerException e) {
            ColorLogUtil.logError(Main.plugin, "定时任务启动失败");
            e.printStackTrace();
        }

        ColorLogUtil.logSuccess(this, "插件启动成功");
    }

    @Override
    public void onDisable() {
        // 关闭定时任务
        try {
            StdSchedulerFactory.getDefaultScheduler().shutdown();
        } catch (SchedulerException e) {
            ColorLogUtil.logError(Main.plugin, "定时任务关闭失败");
            e.printStackTrace();
        }
    }

    public Dao getDao() {
        return dao;
    }
}
