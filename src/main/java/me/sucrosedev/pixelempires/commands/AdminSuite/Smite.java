package me.sucrosedev.pixelempires.commands.AdminSuite;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Smite implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "This command requires operator permissions");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Must specify player to smite");
            return false;
        }

        String playerName = args[0];
        Player playerToSmite = Bukkit.getPlayer(playerName);

        if (playerToSmite == null) {
            sender.sendMessage(ChatColor.RED + "Player does not exist");
            return false;
        }

        playerToSmite.getWorld().strikeLightningEffect(playerToSmite.getLocation());
        playerToSmite.setHealth(0);
        Bukkit.broadcastMessage(ChatColor.GOLD + playerToSmite.getDisplayName() + " has been smitten by the gods!");

        return true;
    }
}
