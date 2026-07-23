package com.vicevil.zamoramcclose.command;

import com.vicevil.zamoramcclose.config.ConfigManager;
import com.vicevil.zamoramcclose.service.PlayerResolver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class CloseCommand implements CommandExecutor, TabCompleter {

    public static final String PERMISSION = "zamoramcclose.use";

    private final PlayerResolver playerResolver;
    private final ConfigManager configManager;

    public CloseCommand(PlayerResolver playerResolver, ConfigManager configManager) {
        this.playerResolver = playerResolver;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission(PERMISSION)) {
            sender.sendMessage(configManager.getMessage("no-permission"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(configManager.getMessage("usage"));
            return true;
        }

        Player target = playerResolver.find(args[0]).orElse(null);
        if (target == null) {
            sender.sendMessage(configManager.getMessage("player-not-found"));
            return true;
        }

        target.closeInventory();
        sender.sendMessage(configManager.getMessage("inventory-closed"));
        target.sendMessage(configManager.getMessage("inventory-closed-target"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) {
            return Collections.emptyList();
        }

        String prefix = args[0].toLowerCase(Locale.ROOT);
        return playerResolver.onlinePlayerNames().stream()
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(prefix))
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }
}
