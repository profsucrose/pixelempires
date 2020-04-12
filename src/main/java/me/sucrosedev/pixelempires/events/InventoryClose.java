package me.sucrosedev.pixelempires.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClose implements Listener {
    @EventHandler
    public static void onInventoryClose(InventoryCloseEvent e) {
        Player player = Bukkit.getPlayer(e.getPlayer().getUniqueId().toString());

    }
}
