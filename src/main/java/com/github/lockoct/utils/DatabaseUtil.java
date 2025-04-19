package com.github.lockoct.utils;


import com.github.lockoct.Main;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.dao.util.Daos;

import java.io.File;

public class DatabaseUtil {

    public static Dao initDB() throws ClassNotFoundException {
        FileConfiguration config = Main.plugin.getConfig();
        String driver = config.getString("dataSource.driverName");
        String url = config.getString("dataSource.url");
        String username = config.getString("dataSource.username");
        String password = config.getString("dataSource.password");

        // sqlite，不需要校验用户名密码
        if (StringUtils.equals(driver, "org.sqlite.JDBC")) {
            SimpleDataSource dataSource = new SimpleDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setJdbcUrl(url);
            // 下划线转驼峰
            Daos.FORCE_HUMP_COLUMN_NAME = true;
            return new NutDao(dataSource);
        } else if (StringUtils.isNotBlank(driver) && StringUtils.isNotBlank(url) && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            SimpleDataSource dataSource = new SimpleDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setJdbcUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            // 下划线转驼峰
            Daos.FORCE_HUMP_COLUMN_NAME = true;
            return new NutDao(dataSource);
        }
        return null;
    }

    public static Dao getDao() {
        return Main.plugin.getDao();
    }

    public static void setDefaultDB() {
        // 读取配置文件
        FileConfiguration config = Main.plugin.getConfig();
        String driver = config.getString("dataSource.driverName");
        String url = config.getString("dataSource.url");

        // 检查配置文件是否未配置数据库连接信息
        // 当驱动名称和链接都为空时才使用默认数据库
        if (StringUtils.isNotBlank(driver) || StringUtils.isNotBlank(url)) {
            return;
        }

        String dataFolderPath = Main.plugin.getDataFolder().getAbsolutePath();
        dataFolderPath = dataFolderPath.replaceAll("\\\\", "/");

        config.set("dataSource.driverName", "org.sqlite.JDBC");
        config.set("dataSource.url", "jdbc:sqlite:" + dataFolderPath + "/minecraft.db");
        Main.plugin.saveConfig();

        // 检查是否已存在默认数据库文件
        File file = new File(dataFolderPath + "/minecraft.db");
        if (!file.exists()) {
            Main.plugin.saveResource("minecraft.db", false);
        }
    }
}
