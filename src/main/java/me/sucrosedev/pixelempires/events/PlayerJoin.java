package me.sucrosedev.pixelempires.events;

import me.sucrosedev.pixelempires.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoin implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        File playerWorldFile = FileUtil.getPlayerVisitedWorldFile(player);

        if (playerWorldFile.exists()) return;
        player.sendMessage(ChatColor.GOLD + "Welcome to Pixel Empires!");
        player.sendMessage(ChatColor.GOLD + "Pixel Empires is a Minecraft server for Stanford OHS where players can roleplay as historical empires or build their own on a Minecraft map modeling the Earth on a 1:4000 scale!");
        player.sendMessage(ChatColor.GOLD + "Go to " + ChatColor.UNDERLINE + "https://pixelempiresmc.net" + ChatColor.RESET + ChatColor.GOLD + " for the website and the link to the map. On the map you can see the server world, where players are and faction territories!");
        player.sendMessage(ChatColor.GOLD + "Look around the map and decide where you want to spawn! To join the world do /join <x> <y>. (Note that you can only do this once!)");
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(Bukkit.getWorld("lobby").getSpawnLocation());

    }
}
