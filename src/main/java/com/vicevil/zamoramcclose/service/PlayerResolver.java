package com.vicevil.zamoramcclose.service;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public interface PlayerResolver {

    Optional<Player> find(String name);

    Collection<String> onlinePlayerNames();
}
