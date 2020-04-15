package me.sucrosedev.pixelempires.events;

import me.sucrosedev.pixelempires.commands.Dungeon;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent e) {
        // check if death was in lobby world
        Player player = e.getEntity();
        if (player.getWorld().getName().equals("world")) {
            Dungeon.livesLeft -= 1;
            if (Dungeon.livesLeft == 0) {
                Dungeon.clearScoreboard(player, "You have run out of lives!");
                return;
            }
        }
    }
}
