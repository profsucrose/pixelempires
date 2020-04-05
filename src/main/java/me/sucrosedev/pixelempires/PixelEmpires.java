package me.sucrosedev.pixelempires;

import me.sucrosedev.pixelempires.commands.AdminSuite.Broadcast;
import me.sucrosedev.pixelempires.commands.AdminSuite.Smite;
import me.sucrosedev.pixelempires.commands.AdminSuite.TeleportSilent;
import me.sucrosedev.pixelempires.commands.AdminSuite.ToggleInvisibility;
import me.sucrosedev.pixelempires.commands.Join;
import me.sucrosedev.pixelempires.commands.TeleportToLobby;
import me.sucrosedev.pixelempires.events.PlayerChatEvent;
import me.sucrosedev.pixelempires.events.PlayerInteractWithItemEvent;
import me.sucrosedev.pixelempires.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PixelEmpires extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // load primary world
        getServer().createWorld(new WorldCreator("lobby"));
        Bukkit.getWorld("lobby").setDifficulty(Difficulty.PEACEFUL);

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractWithItemEvent(), this);

        getCommand("join").setExecutor(new Join());
        getCommand("lobby").setExecutor(new TeleportToLobby());
        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("smite").setExecutor(new Smite());
        getCommand("silent-tp").setExecutor(new TeleportSilent());
        getCommand("invisible").setExecutor(new ToggleInvisibility());

    }

}
