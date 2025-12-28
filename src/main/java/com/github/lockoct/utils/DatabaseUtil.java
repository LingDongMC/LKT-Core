package com.github.lockoct.utils;


import com.github.lockoct.Main;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.dao.util.Daos;

public class DatabaseUtil {

    public static Dao initDB() throws ClassNotFoundException {
        FileConfiguration config = Main.plugin.getConfig();
        String driver = config.getString("dataSource.driverName");
        String url = config.getString("dataSource.url");
        String username = config.getString("dataSource.username");
        String password = config.getString("dataSource.password");

        if (StringUtils.isNotBlank(driver) && StringUtils.isNotBlank(url) && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
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
}
