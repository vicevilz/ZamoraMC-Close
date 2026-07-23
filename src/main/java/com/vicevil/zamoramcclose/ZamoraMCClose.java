package com.vicevil.zamoramcclose;

import com.vicevil.zamoramcclose.command.CloseCommand;
import com.vicevil.zamoramcclose.config.ConfigManager;
import com.vicevil.zamoramcclose.service.BukkitPlayerResolver;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZamoraMCClose extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager configManager = new ConfigManager(this);
        CloseCommand closeCommand = new CloseCommand(new BukkitPlayerResolver(), configManager);

        PluginCommand command = getCommand("zamoramc-close");
        if (command == null) {
            throw new IllegalStateException("The zamoramc-close command is missing from plugin.yml");
        }

        command.setExecutor(closeCommand);
        command.setTabCompleter(closeCommand);
        getLogger().info("ZamoraMC-Close enabled.");
    }
}
