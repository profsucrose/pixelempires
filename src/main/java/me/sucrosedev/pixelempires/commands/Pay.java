package me.sucrosedev.pixelempires.commands;

import me.sucrosedev.pixelempires.util.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class Pay implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String senderUuidString = ((Player) sender).getUniqueId().toString();

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Must specify player to pay and coin amount");
            return false;
        }

        Player receiverPlayer = Bukkit.getPlayer(args[0]);

        if (receiverPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Player specified does not exist or is not currently online");
            return false;
        }

        int coins = Integer.parseInt(args[1]);

        String receiverUuidString = receiverPlayer.getUniqueId().toString();

        Long senderCoinAmount = 0L;
        try {
            senderCoinAmount = Economy.get(senderUuidString);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Something went wrong internally! Contact a server moderator if this shouldn't have happened.");
            return false;
        }

        if (senderCoinAmount < coins) {
            String appendPlural = senderCoinAmount > 1 ? "s" : "";
            sender.sendMessage(ChatColor.RED + "Insufficient coins to make payment (You have " + senderCoinAmount + " coin" + appendPlural + ")");
            return false;
        }

        try {
            Economy.exchange(senderUuidString, receiverUuidString, coins);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Something went wrong internally! Contact a server moderator if this shouldn't have happened.");
            return false;
        }

        sender.sendMessage(ChatColor.GOLD + "Transferred " + coins + " to " + receiverPlayer.getDisplayName() + " successfully");
        return true;
    }
}
