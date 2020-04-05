package me.sucrosedev.pixelempires.commands.AdminSuite;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Broadcast implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "This command requires operator permissions");
            return false;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++)
            message.append(args[i] + " ");


        Bukkit.broadcastMessage(message.toString());
        return true;
    }
}
