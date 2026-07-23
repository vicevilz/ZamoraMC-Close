package com.vicevil.zamoramcclose.util;

import org.bukkit.ChatColor;

public final class MessageUtil {

    private MessageUtil() {
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message == null ? "" : message);
    }
}
