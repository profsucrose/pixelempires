package me.sucrosedev.pixelempires.commands.AdminSuite;

import me.sucrosedev.pixelempires.util.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TakeCoins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "Command requires operator permissions");
            return false;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Must specify player and coin amount");
            return false;
        }

        String receiverName = args[0];
        int coinAmount = Integer.parseInt(args[1]);

        Player receiverPlayer = Bukkit.getPlayer(receiverName);

        if (receiverPlayer == null) {
            player.sendMessage(ChatColor.RED + "Player specified does not exist");
            return false;
        }

        String uuidString = receiverPlayer.getUniqueId().toString();

        try {
            Economy.minus(uuidString, coinAmount);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Give call failed, check logs");
            System.out.println("Give failed: " + e.getMessage());
            return false;
        }

        player.sendMessage(ChatColor.GOLD + "Taken coins successfully");
        receiverPlayer.sendMessage(ChatColor.GOLD + "The gods have seized " + coinAmount + " from you!");
        return true;
    }
}
