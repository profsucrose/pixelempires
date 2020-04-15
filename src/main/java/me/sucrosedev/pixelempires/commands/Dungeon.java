package me.sucrosedev.pixelempires.commands;

import me.sucrosedev.pixelempires.PixelEmpires;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.Arrays;

public class Dungeon implements CommandExecutor {

    private static int timeLeft;
    public static int livesLeft;
    private static int countdownId;
    public static int[] altarChestCoords = {74, 12, 30};
    public static int[] mazeAltarCoords = {50, 11, 45};

    public static ItemStack altarKey;

    public static int mazeKeyCount = 0;

    public static String mazeKeyName = "" + ChatColor.GREEN + ChatColor.BOLD + "Maze Altar Key";

    private static ScoreboardManager manager = Bukkit.getScoreboardManager();

    private static int[][] altarKeyChestPositions = {
            {73, 11, 44},
            {69, 11, 57},
            {80, 10, 48}
    };

    private static int[] parkourChestCoords = {65, 11, -4};

    static public void clearScoreboard(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
        Bukkit.getServer().getScheduler().cancelTask(countdownId);
        player.setScoreboard(manager.getNewScoreboard());
    }

    static public void updateScoreboard(Player player) {
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("timer", "dummy", "" + ChatColor.RED + ChatColor.BOLD + "DUNGEON");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        String timeString = (timeLeft / 60) + ":"
                + ((timeLeft % 60) < 10 ? "0" : "")
                + (timeLeft % 60);
        Score timeScore = objective.getScore("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + timeString);
        Score livesScore = objective.getScore("" + ChatColor.GREEN + ChatColor.BOLD + livesLeft + " LIVE" + (livesLeft > 1 ? "S" : "") + " LEFT");

        timeScore.setScore(2);
        livesScore.setScore(1);

        player.setScoreboard(scoreboard);
    }

    public static ItemStack createDungeonKey(String keyName, ChatColor keyColor) {
        ItemStack dungeonKey = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta dungeonKeyMeta = dungeonKey.getItemMeta();
        dungeonKeyMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Pixel Dungeon Key"));
        dungeonKeyMeta.setDisplayName("" + keyColor + ChatColor.BOLD + "Key of " + keyName);
        dungeonKey.setItemMeta(dungeonKeyMeta);
        return dungeonKey;
    }

    public static void createAltarKey() {
        altarKey = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta altarKeyMeta = altarKey.getItemMeta();
        altarKeyMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Altar Key"));
        altarKeyMeta.setDisplayName(mazeKeyName);
        altarKey.setItemMeta(altarKeyMeta);
    }

    private static void placeAltarKeys() {
        for (int i = 0; i < altarKeyChestPositions.length; i++) {
            Chest chest = (Chest) Bukkit.getWorld("world")
                    .getBlockAt(altarKeyChestPositions[i][0], altarKeyChestPositions[i][1], altarKeyChestPositions[i][2]).getState();
            System.out.println("Inserted key into chest at " + altarKeyChestPositions[i].toString());
            chest.update(true);
            chest.getBlockInventory().setItem(13, altarKey);
        }
    }

    private static void placeParkourKey() {
        Chest chest = (Chest) Bukkit.getWorld("world")
                .getBlockAt(parkourChestCoords[0], parkourChestCoords[1], parkourChestCoords[2]).getState();
        chest.update(true);
        chest.getBlockInventory().setItem(13, createDungeonKey("Agility", ChatColor.BLUE));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        player.sendMessage(ChatColor.GOLD + "Welcome to the first Pixel Dungeon...");
        player.sendMessage(ChatColor.GOLD + "There are three rooms, each hiding their own key...");
        player.sendMessage(ChatColor.GOLD + "When all keys are inserted, completion should be near...");
        player.sendMessage(ChatColor.GOLD + "Should you succeed, you will be rewarded with the most powerful gear in the realm...");
        player.sendMessage(ChatColor.GOLD + "You are permitted three lives and fifteen minutes, should you run out of either you will need to restart...");
        player.sendMessage(ChatColor.GOLD + "Good luck!");
        timeLeft = 15 * 60; // 15 minutes
        livesLeft = 3;

        updateScoreboard(player);
        placeAltarKeys();
        placeParkourKey();

        countdownId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PixelEmpires.plugin, new Runnable() {
            @Override
            public void run() {
                System.out.println(timeLeft);
                if (timeLeft == 0) {
                    clearScoreboard(player, "You have run out of time!");
                }
                timeLeft -= 1;
                updateScoreboard(player);
            }
        }, 0L, 20L);

        return true;
    }
}
