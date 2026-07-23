package com.vicevil.zamoramcclose.command;

import com.vicevil.zamoramcclose.config.ConfigManager;
import com.vicevil.zamoramcclose.service.PlayerResolver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CloseCommandTest {

    private static final Command COMMAND = mock(Command.class);

    private PlayerResolver playerResolver;
    private ConfigManager configManager;
    private CloseCommand closeCommand;

    @BeforeEach
    void setUp() {
        playerResolver = mock(PlayerResolver.class);
        MemoryConfiguration configuration = new MemoryConfiguration();
        configuration.set("messages.no-permission", "&cNo tienes permiso.");
        configuration.set("messages.player-not-found", "&cEl jugador no está conectado.");
        configuration.set("messages.usage", "&cUso: /zamoramc-close <jugador>");
        configuration.set("messages.inventory-closed", "&aInventario cerrado correctamente.");
        configuration.set("messages.inventory-closed-target", "&eTu inventario ha sido cerrado.");
        configManager = new ConfigManager(configuration);
        closeCommand = new CloseCommand(playerResolver, configManager);
    }

    @Test
    void consoleClosesTargetInventoryAndNotifiesBothSenders() {
        CommandSender console = mock(CommandSender.class);
        Player target = mock(Player.class);
        when(target.getName()).thenReturn("Steve");
        when(playerResolver.find("Steve")).thenReturn(Optional.of(target));

        assertTrue(closeCommand.onCommand(console, COMMAND, "zamoramc-close", new String[]{"Steve"}));

        verify(target).closeInventory();
        verify(console).sendMessage("§aInventario cerrado correctamente.");
        verify(target).sendMessage("§eTu inventario ha sido cerrado.");
    }

    @Test
    void playerWithPermissionCanUseCommand() {
        Player sender = mock(Player.class);
        Player target = mock(Player.class);
        when(sender.hasPermission(CloseCommand.PERMISSION)).thenReturn(true);
        when(playerResolver.find("Steve")).thenReturn(Optional.of(target));

        closeCommand.onCommand(sender, COMMAND, "zamoramc-close", new String[]{"Steve"});

        verify(target).closeInventory();
    }

    @Test
    void playerWithoutPermissionIsRejected() {
        Player sender = mock(Player.class);
        when(sender.hasPermission(CloseCommand.PERMISSION)).thenReturn(false);

        closeCommand.onCommand(sender, COMMAND, "zamoramc-close", new String[]{"Steve"});

        verify(sender).sendMessage("§cNo tienes permiso.");
        verify(playerResolver, never()).find("Steve");
    }

    @Test
    void invalidArgumentsShowUsage() {
        CommandSender sender = mock(CommandSender.class);

        closeCommand.onCommand(sender, COMMAND, "zamoramc-close", new String[0]);

        verify(sender).sendMessage("§cUso: /zamoramc-close <jugador>");
    }

    @Test
    void missingPlayerIsReported() {
        CommandSender sender = mock(CommandSender.class);
        when(playerResolver.find("Steve")).thenReturn(Optional.empty());

        closeCommand.onCommand(sender, COMMAND, "zamoramc-close", new String[]{"Steve"});

        verify(sender).sendMessage("§cEl jugador no está conectado.");
    }

    @Test
    void tabCompletionFiltersPlayerNames() {
        CommandSender sender = mock(CommandSender.class);
        when(playerResolver.onlinePlayerNames()).thenReturn(List.of("Alex", "Steve", "Stella"));

        assertEquals(List.of("Stella", "Steve"),
                closeCommand.onTabComplete(sender, COMMAND, "zamoramc-close", new String[]{"st"}));
    }
}
