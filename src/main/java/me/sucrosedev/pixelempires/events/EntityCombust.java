package me.sucrosedev.pixelempires.events;

import me.sucrosedev.pixelempires.commands.Dungeon;
import me.sucrosedev.pixelempires.util.Direction;
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

    private static int[] statueOfWealthFireCoords = {1112, 70, -112};

    @EventHandler
    public static void onEntityCombust(EntityCombustByBlockEvent e) {
        if (e.getEntity().getType().equals(EntityType.DROPPED_ITEM)) {
            Location location = e.getEntity().getLocation();

            Item item = (Item) e.getEntity();
            Player player = PlayerDropItem.lastPlayerToDropItem;

            if (Direction.locationIsWithinXYZ(location, Dungeon.mazeAltarCoords, 2)) {
                System.out.println(item.getItemStack().getItemMeta().getDisplayName());
                if (item.getItemStack().getItemMeta().getDisplayName().equals(Dungeon.mazeKeyName)) {
                    System.out.println("Keys equal");
                    Dungeon.mazeKeyCount += item.getItemStack().getAmount();
                    if (Dungeon.mazeKeyCount == 3) {
                        player.sendMessage(ChatColor.GOLD + "Congratulations, you have delivered all altar keys...");
                        player.getInventory().addItem(Dungeon.createDungeonKey("Navigation", ChatColor.GREEN));
                    } else {
                        int supplement = (3 - Dungeon.mazeKeyCount);
                        player.sendMessage(ChatColor.GOLD + "Good, you need deliver only " + supplement + " more key" + (supplement > 1 ? "s" : "") + "...");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "The altar accepts only the correct kind of key...");
                    player.getInventory().addItem(item.getItemStack());
                }
            }

            if (Direction.locationIsWithinXYZ(location, statueOfWealthFireCoords, 2)) {

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
