package me.sucrosedev.pixelempires.events;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {

    public static Player lastPlayerToDropItem;

    @EventHandler
    public static void onPlayerDropItem(PlayerDropItemEvent e) {
        lastPlayerToDropItem = e.getPlayer();
        System.out.println("New player to be the latest to drop an item: " + lastPlayerToDropItem.getDisplayName());
    }
}
