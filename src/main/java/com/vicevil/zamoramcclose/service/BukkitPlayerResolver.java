package com.vicevil.zamoramcclose.service;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public final class BukkitPlayerResolver implements PlayerResolver {

    @Override
    public Optional<Player> find(String name) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getName().equalsIgnoreCase(name))
                .map(player -> (Player) player)
                .findFirst();
    }

    @Override
    public Collection<String> onlinePlayerNames() {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();
    }
}
