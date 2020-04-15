package me.sucrosedev.pixelempires.events;

import me.sucrosedev.pixelempires.commands.Dungeon;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerMove implements Listener {

    private static BossBar bar;

    private static int[][] trialRoomEntrance = {
            {58, 31},
            {58, 32}
    };

    private static int[][] trialRoomCenterPlatform = {
            {50, 31},
            {51, 32}
    };

    private static int[][] trialRoomSkeletonPositions = {
            {51, 11, 22},
            {51, 11, 41},
            {41, 11, 31}
    };

    private static boolean hasEnteredTrialRoom = false;
    private static boolean hasStartedTrialRoom = false;

    private static int mobsLeft;

    public static boolean isWithin(int x, int lower, int upper) {
        return x >= lower && x <= upper;
    }

    public static boolean isWithinBlocks(int[][] coords, int x, int z) {
        return isWithin(x, coords[0][0], coords[1][0])
                && isWithin(z, coords[0][1], coords[1][1]);
    }

    public static void spawnMob(World world, int x, int y, int z, EntityType mob) {
        world.spawnEntity(new Location(world, x, y, z), mob);
    }

    public static void spawnMobs(World world, int x, int y, int z, EntityType mob, int count) {
        for (int i = 0; i < count; i++) spawnMob(world, x, y, z, mob);
    }

    public static void spawnMob(World world, int[] coordinates, EntityType mob) {
        spawnMob(world, coordinates[0], coordinates[1], coordinates[2], mob);
    }

    private static void spawnSkeletons(World world) {
        spawnMob(world, trialRoomSkeletonPositions[0], EntityType.SKELETON);
        spawnMob(world, trialRoomSkeletonPositions[1], EntityType.SKELETON);
        spawnMob(world, trialRoomSkeletonPositions[2], EntityType.SKELETON);
    }

    public static void onEntrance(Player player) {
        if (hasEnteredTrialRoom) return;
        player.sendMessage(ChatColor.GOLD + "Welcome to the trial room...");
        player.sendMessage(ChatColor.GOLD + "To begin the challenge, walk onto the center gold platform...");
        hasEnteredTrialRoom = true;
    }

    private static void setBlock(World world, int x, int y, int z, Material block) {
        world.getBlockAt(x, y, z).setType(block);
    }

    public static void onMobDeath(Player player, Location location) {
        mobsLeft -= 1;
        bar.setProgress(((double) mobsLeft) / 10);
        System.out.println("Mobs left: " + mobsLeft + "; Percentage: " + (mobsLeft / 10));

        if (mobsLeft == 0) {
            bar.removePlayer(player);
            player.sendMessage(ChatColor.GOLD + "Congratulations, you have won the Key of Courage!");
            player.getInventory().clear();
            player.setHealth(20);
            player.getInventory().addItem(Dungeon.createDungeonKey("Courage", ChatColor.RED));
            World world = location.getWorld();
            setTrialRoomDoor(world, Material.AIR);
            setBlock(world, 59, 11, 31, Material.STONE_PRESSURE_PLATE);
            setBlock(world, 59, 11, 32, Material.STONE_PRESSURE_PLATE);
        }
    }

    private static void setTrialRoomDoor(World world, Material block) {
        setBlock(world, 59, 11, 31, block);
        setBlock(world, 59, 12, 31, block);
        setBlock(world, 59, 12, 32, block);
        setBlock(world, 59, 11, 32, block);
    }

    public static void onReachedCenter(Player player) {
        if (hasStartedTrialRoom) return;
        player.sendMessage(ChatColor.GOLD + "Good luck!");
        player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
        player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 1));
        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

        World world = player.getWorld();

        setTrialRoomDoor(world, Material.IRON_BARS);

        spawnSkeletons(world);
        spawnMob(world, 46, 10, 35, EntityType.BLAZE);
        spawnMobs(world, 55, 10, 36, EntityType.SPIDER, 2);
        spawnMobs(world, 54, 9, 29, EntityType.STRAY, 1);
        spawnMobs(world, 45, 10, 26, EntityType.ZOMBIE, 3);
        mobsLeft = 10;

        bar = Bukkit.createBossBar("Trial Room", BarColor.RED, BarStyle.SEGMENTED_10);
        bar.addPlayer(player);
        bar.setProgress(1);

        hasStartedTrialRoom = true;
    }

    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        Location location = player.getLocation();
        int playerX = location.getBlockX();
        int playerZ = location.getBlockZ();

        if (isWithinBlocks(trialRoomEntrance, playerX, playerZ)) {
            onEntrance(player);
        }

        if (isWithinBlocks(trialRoomCenterPlatform, playerX, playerZ)) {
            onReachedCenter(player);
        }

    }
}
