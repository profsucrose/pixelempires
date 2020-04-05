package me.sucrosedev.pixelempires.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public static void onPlayerChat(AsyncPlayerChatEvent e) {

        if (!e.getPlayer().isOp()) return;

        e.setCancelled(true);
        Bukkit.broadcastMessage(""
                + ChatColor.GOLD
                + ChatColor.BOLD
                + "[GOD] "
                + e.getPlayer().getDisplayName()
                + ChatColor.RESET
                + ChatColor.GOLD
                + ": "
                + e.getMessage()
        );

    }
}
