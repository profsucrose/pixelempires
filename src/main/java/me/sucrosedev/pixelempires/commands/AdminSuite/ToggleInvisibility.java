package me.sucrosedev.pixelempires.commands.AdminSuite;

import me.sucrosedev.pixelempires.PixelEmpires;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ToggleInvisibility implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "This command requires operator permissions");
            return false;
        }

        Player player = (Player) sender;

        boolean toggle = Boolean.valueOf(args[0]);

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != null) {
                if (toggle) {
                    pl.hidePlayer(PixelEmpires.plugin, player);
                } else {
                    pl.showPlayer(PixelEmpires.plugin, player);
                }
            }
        }

        player.sendMessage("Toggled invisibility");

        return true;
    }
}
