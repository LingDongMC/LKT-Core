package com.github.lockoct;

import com.github.lockoct.entity.BasePlugin;
import com.github.lockoct.utils.ColorLogUtil;
import com.github.lockoct.utils.DatabaseUtil;
import com.github.lockoct.utils.I18nUtil;
import org.nutz.dao.Dao;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main extends BasePlugin {
    public static Main plugin;

    private Dao dao;

    @Override
    public void onEnable() {
        plugin = this;

        // 保存配置文件
        saveDefaultConfig();

        // 初始化语言配置
        I18nUtil.init(this);

        // 检查并初始化默认数据库（Sqlite）
        DatabaseUtil.setDefaultDB();

        // 连接数据库
        try {
            this.dao = DatabaseUtil.initDB();
            if (this.dao != null) {
                ColorLogUtil.logSuccess(this, I18nUtil.getText(this, "pluginMsg.dbConnectSuccess"));
            }
        } catch (Exception e) {
            ColorLogUtil.logError(this, I18nUtil.getText(this, "pluginMsg.dbConnectFailed"));
            e.printStackTrace();
        }

        // 启动定时任务
        try {
            StdSchedulerFactory.getDefaultScheduler().start();
        } catch (SchedulerException e) {
            ColorLogUtil.logError(Main.plugin, I18nUtil.getText(this, "pluginMsg.cronjobStartFailed"));
            e.printStackTrace();
        }

        ColorLogUtil.logSuccess(this, I18nUtil.getText(this, "pluginMsg.enableSuccess"));
    }

    @Override
    public void onDisable() {
        // 关闭定时任务
        try {
            StdSchedulerFactory.getDefaultScheduler().shutdown();
        } catch (SchedulerException e) {
            ColorLogUtil.logError(Main.plugin, I18nUtil.getText(this, "pluginMsg.cronjobShutdownFailed"));
            e.printStackTrace();
        }
    }

    public Dao getDao() {
        return dao;
    }
}
