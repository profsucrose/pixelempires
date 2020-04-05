package me.sucrosedev.pixelempires.commands;

import me.sucrosedev.pixelempires.util.FileUtil;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

public class Join implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        File playerWorldFile = FileUtil.getPlayerVisitedWorldFile(player);

        if (playerWorldFile.exists()) {
            player.sendMessage(ChatColor.RED + "You've already joined the world map, you can no longer use this command and must traverse the globe on your own!");
            return false;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Must specify arguments (/join <x> <z>)");
            return true;
        }

        int x = Integer.valueOf(args[0]);
        int z = Integer.valueOf(args[1]);
        int y;
        for (y = 150; y > 0; y--) {
            if (!Bukkit.getWorld("world").getBlockAt(x, y, z).getType().equals(Material.AIR)) {
                break;
            }
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 255));
        player.setGameMode(GameMode.SURVIVAL);

        player.teleport(new Location(Bukkit.getWorld("world"), x, y + 4, z));
        player.sendMessage(ChatColor.GOLD + "Welcome to the world! May you go out and build or join the greatest empire Minecraft has ever seen! You are invincible for 30 seconds!");

        try {
            playerWorldFile.createNewFile();
            System.out.println("Created a new player world file");
        } catch(Exception e) {
            System.out.println("Error in creating player world file: " + e.getMessage());
        }
        return true;

    }
}
