package com.github.lockoct.utils;


import com.github.lockoct.entity.BasePlugin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PathUtil {
    public static String[] getEntityParentPackageNames(BasePlugin plugin) {
        String[] fileList = getResourceFileList(plugin, "com/github/lockoct");
        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = fileList[i].replace("com/github/lockoct", "").replace("entity/", "").replace("/", ".");
        }
        return fileList;
    }

    public static String[] getI18nFileNames(BasePlugin plugin) {
        String[] fileList = getResourceFileList(plugin, "i18n/");
        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = fileList[i].replace("i18n/", "");
        }
        return fileList;
    }

    private static String[] getResourceFileList(BasePlugin plugin, String startPath) {
        ArrayList<String> fileList = new ArrayList<>();

        try {
            JarFile jf = new JarFile(plugin.getJarFile());
            Enumeration<JarEntry> jarEntries = jf.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                String jarEntryName = jarEntry.getName();
                if (jarEntryName.startsWith(startPath) && !jarEntryName.endsWith("/")) {
                    fileList.add(jarEntryName);
                }
            }
        } catch (IOException e) {
            ColorLogUtil.logError(plugin, "无法找到插件jar文件 / Could not find plugin jar file.");
        }

        return fileList.toArray(new String[0]);
    }
}
