package me.sucrosedev.pixelempires.events;

import me.sucrosedev.pixelempires.util.Direction;
import me.sucrosedev.pixelempires.util.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {



    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            System.out.println("Clicker is not a player");
            return;
        }

        Player player = Bukkit.getPlayer(e.getWhoClicked().getName());
        Location location = e.getInventory().getLocation();
        ItemStack items = e.getInventory().getItem(e.getSlot());

        if (!(location.getBlock().getState() instanceof Chest)) return;
        Chest chest = (Chest) location.getBlock().getState();
        if (!chest.hasMetadata("isChestShop")
                || chest.getMetadata("ownerId").get(0).asString().equals(player.getUniqueId().toString())) return;
        BlockFace direction = ((Directional) chest.getBlockData()).getFacing();
        int[] xz = Direction.getXZFromCardinal(direction);
        BlockState testForSign = Bukkit.getWorld("world").getBlockAt(location.getBlockX() + xz[0], location.getBlockY(), location.getBlockZ() + xz[1]).getState();
        if (testForSign instanceof Sign) {
            e.setCancelled(true);
            if (items == null) return;
            Sign sign = (Sign) testForSign;

            int price = 0;
            try {
                price = Integer.parseInt(sign.getLine(3));
            } catch(Exception exc) {
                player.sendMessage(ChatColor.RED + "Chest shop sign does not have a valid integer on the last line and is not a valid shop!");
                player.closeInventory();
                return;
            }

            String senderId = player.getUniqueId().toString();

            long senderBalance = 0;
            try {
                senderBalance = Economy.get(senderId);
            } catch (Exception getBalanceExc) {
                System.out.println("Error in getting sender balance " + getBalanceExc);
                player.sendMessage(ChatColor.RED + "Error in retrieving current balance, please report this to a moderator");
                return;
            }

            if (senderBalance < price) {
                player.sendMessage(ChatColor.RED + "You have insufficient Pixel Coins to make this purchase!");
                return;
            }

            try {
                Economy.exchange(senderId, chest.getMetadata("ownerId").get(0).asString(), price);
            } catch (Exception exchangeExc) {
                System.out.println("Error in shop purchase: " + exchangeExc);
                player.sendMessage(ChatColor.RED + "Error in paying for shop purchase, please report this to a moderator");
                return;
            }

            player.getInventory().addItem(chest.getInventory().getItem(e.getSlot()));
            chest.getInventory().clear(e.getSlot());

            System.out.println("Price: " + price);
            System.out.println("Amount: " + items.getAmount());
            System.out.println("Player ID: " + player.getUniqueId().toString());
            System.out.println("Shop owner: " + chest.getMetadata("ownerId").get(0).asString());

            player.sendMessage(ChatColor.GOLD
                    + "Purchased "
                    + items.getAmount()
                    + " "
                    + items.getType()
                    + "(s) for "
                    + price
                    + " coins"
            );

        }
    }
}
