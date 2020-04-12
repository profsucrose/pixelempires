package me.sucrosedev.pixelempires.events;

import me.sucrosedev.pixelempires.util.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;

import java.util.concurrent.ExecutionException;

public class EntityCombust implements Listener {

    private static boolean isWithin(int x, int y, int range) {
        return Math.abs(x - y) <= range;
    }

    @EventHandler
    public static void onEntityCombust(EntityCombustByBlockEvent e) {
        if (e.getEntity().getType().equals(EntityType.DROPPED_ITEM)) {
            Location location = e.getEntity().getLocation();
            if (isWithin(location.getBlockX(), 1112, 2)
            && isWithin(location.getBlockY(), 70, 2)
            && isWithin(location.getBlockZ(), -112, 2)) {
                Item item = (Item) e.getEntity();
                Player player = PlayerDropItem.lastPlayerToDropItem;
                if (player == null) {
                    System.out.println("No player has burned an item recently");
                }
                if (item.getItemStack().getType().equals(Material.DIAMOND)) {
                    try {
                        int profit = 10 * item.getItemStack().getAmount();
                        Economy.add(player.getUniqueId().toString(), profit);
                        player.sendMessage(ChatColor.GOLD + "You have received " + profit + " coins from the statue of wealth!");
                    } catch (Exception exc) {
                        player.sendMessage(ChatColor.RED + "Conversion failed. If this should not have happened, contact a moderator!");
                        System.out.println(exc.getMessage());
                    }
                } else {
                    player.sendMessage(ChatColor.GOLD + "The statue of wealth does not care for this counterfeit you have fed it...");
                }
            }
        }
    }
}
