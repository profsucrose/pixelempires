package me.sucrosedev.pixelempires.commands.AdminSuite;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportSilent implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "This command requires operator permissions");
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Must specify players / coordinates");
            return false;
        }

        Player player = (Player) sender;
        Player playerToTeleport = Bukkit.getPlayer(args[0]);

        if (playerToTeleport == null) {
            sender.sendMessage(ChatColor.RED + "Specified player to teleport does not exist");
        }

        try {

            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);

            playerToTeleport.teleport(new Location(playerToTeleport.getWorld(), x, y, z));
            player.sendMessage(ChatColor.GOLD + "Teleported player to coordinates silently");

        }
        catch (NumberFormatException e) {
            // check for player
            String playerName = args[1];
            Player playerToTeleportTo = Bukkit.getPlayer(playerName);

            if (playerToTeleportTo == null) {
                sender.sendMessage(ChatColor.RED + "Player does not exist");
                return false;
            }

            playerToTeleport.teleport(playerToTeleportTo.getLocation());
            sender.sendMessage(ChatColor.GOLD + "Teleported player to coordinates silently");
        }

        return true;
    }
}
