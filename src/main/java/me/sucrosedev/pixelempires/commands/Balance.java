package me.sucrosedev.pixelempires.commands;

import me.sucrosedev.pixelempires.util.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class Balance implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String uuidString = ((Player) sender).getUniqueId().toString();
        Long amount = 0L;
        try {
            amount = Economy.get(uuidString);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Something went wrong internally when fetching your balance, contact a server moderator if this shouldn't have happened!");
            return false;
        }

        sender.sendMessage(ChatColor.GOLD + "You have " + amount + " coins");
        return false;
    }
}
