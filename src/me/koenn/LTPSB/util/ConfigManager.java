package me.koenn.LTPSB.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigManager {

    private static ConfigManager cm;
    private FileConfiguration config;

    public ConfigManager(FileConfiguration c, Plugin pl) {
        cm = this;
        if (!pl.getDataFolder().exists()) {
            pl.getDataFolder().mkdir();
            pl.saveDefaultConfig();
        }
        this.config = c;
    }

    public static ConfigManager getInstance() {
        return cm;
    }

    public List<String> getList(String path) {
        return config.getStringList(path);
    }
}