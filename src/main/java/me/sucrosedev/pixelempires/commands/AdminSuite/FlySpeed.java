package me.sucrosedev.pixelempires.commands.AdminSuite;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlySpeed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "This command requires operator permissions");
            return false;
        }

        float newFlySpeed = Float.valueOf(args[0]);

        if (newFlySpeed > 1 || newFlySpeed < -1) {
            player.sendMessage(ChatColor.RED + "Specified fly speed must be a number between -1 and 1.");
            return false;
        }

        player.setFlySpeed(newFlySpeed);
        return true;

    }
}
