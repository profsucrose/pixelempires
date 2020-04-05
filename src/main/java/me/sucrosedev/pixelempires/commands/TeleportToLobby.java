package me.sucrosedev.pixelempires.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportToLobby implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You have insufficient permissions to run this command. Pixel disapproves!");
            return false;
        }

        player.teleport(new Location(Bukkit.getWorld("lobby"), 0, 60, 0));
        return true;

    }
}
