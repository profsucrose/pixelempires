package me.sucrosedev.pixelempires.commands.AdminSuite;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Shrine implements CommandExecutor {
    private static int[][] shrineLocations = {
            {768, -1054},       // Europe
            {3036, -725},       // Asia
            {3932, 636},        // Australia
            {325, -506},        // Africa
            {-1399, 400},       // South America
            {757, 2424},        // Antarctica
            {-3591, -1089}      // North America
    };

    private static double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Location location = ((Player) sender).getLocation();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        int[] closestShrine = shrineLocations[0];
        double closestShrineDistance = getDistance(x, z, closestShrine[0], closestShrine[1]);
        for (int i = 1; i < shrineLocations.length; i++) {
            double currentShrineDistance = getDistance(x, z, shrineLocations[i][0], shrineLocations[i][1]);
            if (currentShrineDistance < closestShrineDistance) {
                closestShrine = shrineLocations[i];
                closestShrineDistance = currentShrineDistance;
            }
        }

        sender.sendMessage(ChatColor.GOLD + "Closest shrine is at X: " + closestShrine[0] + " and Z: " + closestShrine[1]);
        return true;
    }
}
