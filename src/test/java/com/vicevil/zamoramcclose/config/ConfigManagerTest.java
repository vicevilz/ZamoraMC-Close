package com.vicevil.zamoramcclose.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfigManagerTest {

    @Test
    void reloadRefreshesConfigurationFromPlugin() {
        JavaPlugin plugin = mock(JavaPlugin.class);
        YamlConfiguration initial = configurationWithMessage("Mensaje inicial.");
        YamlConfiguration updated = configurationWithMessage("Mensaje actualizado.");
        when(plugin.getConfig()).thenReturn(initial, updated);

        ConfigManager configManager = new ConfigManager(plugin);
        assertEquals("Mensaje inicial.", configManager.getMessage("reload-success"));

        configManager.reload();

        assertEquals("Mensaje actualizado.", configManager.getMessage("reload-success"));
        verify(plugin).saveDefaultConfig();
        verify(plugin).reloadConfig();
    }

    private static YamlConfiguration configurationWithMessage(String message) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.set("messages.reload-success", message);
        return configuration;
    }
}
