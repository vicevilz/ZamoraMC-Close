package com.vicevil.zamoramcclose.config;

import com.vicevil.zamoramcclose.util.MessageUtil;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigManager {

    private final Configuration configuration;

    public ConfigManager(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.configuration = plugin.getConfig();
    }

    public ConfigManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getMessage(String key) {
        String message = configuration.getString("messages." + key);
        if (message == null) {
            return MessageUtil.colorize("&cMissing message: " + key);
        }
        return MessageUtil.colorize(message);
    }
}
