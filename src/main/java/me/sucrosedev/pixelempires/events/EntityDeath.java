package me.sucrosedev.pixelempires.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {
    @EventHandler
    public static void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("world")) {
            PlayerMove.onMobDeath(e.getEntity().getKiller(), e.getEntity().getLocation());
        }
    }
}
