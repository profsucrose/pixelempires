package me.sucrosedev.pixelempires.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractWithItemEvent implements Listener {
    @EventHandler
    public static void onPlayerInteract(PlayerInteractEvent e) {


        if (e.getItem().getType().equals(Material.ENDER_EYE)) {
            Player player = e.getPlayer();
            System.out.println("Spawning Eye of Endeer...");
            e.setCancelled(true);
            EnderSignal enderSignal = (EnderSignal) player.getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_SIGNAL);
            enderSignal.setTargetLocation(new Location(Bukkit.getWorld("world"), 246, 87, 2393));
        }

    }
}
