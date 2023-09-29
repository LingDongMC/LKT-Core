package com.github.lockoct.entity;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class BasePlugin extends JavaPlugin {
    protected File jarFile;
    protected HashMap<String, YamlConfiguration> langMap = new HashMap<>();
    protected String lang;

    public BasePlugin() {
        this.jarFile = getFile();
    }

    public File getJarFile() {
        return jarFile;
    }

    public HashMap<String, YamlConfiguration> getLangMap() {
        return langMap;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
