package com.github.lockoct.utils;


import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PathUtil {
    public static String[] getEntityParentPackageNames(JavaPlugin plugin, File jarFile) {
        String pageStart = "com/github/lockoct";
        ArrayList<String> entityParentPkgList = new ArrayList<>();

        try {
            JarFile jf = new JarFile(jarFile);
            Enumeration<JarEntry> jarEntries = jf.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                String jarEntryName = jarEntry.getName();
                if (jarEntryName.startsWith(pageStart) && jarEntryName.endsWith("entity/")) {
                    entityParentPkgList.add(jarEntryName.replace(pageStart, "").replace("entity/", "").replace("/", "."));
                }
            }
        } catch (IOException e) {
            ColorLogUtil.logError(plugin, "无法找到插件jar文件");
        }

        return entityParentPkgList.toArray(new String[0]);
    }
}
