package me.sucrosedev.pixelempires;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import me.sucrosedev.pixelempires.commands.AdminSuite.*;
import me.sucrosedev.pixelempires.commands.Balance;
import me.sucrosedev.pixelempires.commands.Join;
import me.sucrosedev.pixelempires.commands.Pay;
import me.sucrosedev.pixelempires.commands.TeleportToLobby;
import me.sucrosedev.pixelempires.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class PixelEmpires extends JavaPlugin {

    public static Plugin plugin;

    private static String SERVICE_ACCOUNT_PATH = "/root/PixelEmpires/plugins/PixelEmpires/pixelempires-service-account.json";

    private static void initFirebase() {
        try {
            InputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_PATH);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.out.println("Could not get credentials file: " + e.getMessage());
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        initFirebase();

        plugin = this;

        // load primary world
        getServer().createWorld(new WorldCreator("lobby"));
        Bukkit.getWorld("lobby").setDifficulty(Difficulty.PEACEFUL);

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractWithItemEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityCombust(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryOpen(), this);

        getCommand("join").setExecutor(new Join());
        getCommand("lobby").setExecutor(new TeleportToLobby());
        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("smite").setExecutor(new Smite());
        getCommand("silent-tp").setExecutor(new TeleportSilent());
        getCommand("invisible").setExecutor(new ToggleInvisibility());
        getCommand("flyspeed").setExecutor(new FlySpeed());
        getCommand("pay").setExecutor(new Pay());
        getCommand("balance").setExecutor(new Balance());
        getCommand("shrine").setExecutor(new Shrine());
        getCommand("takecoins").setExecutor(new TakeCoins());
        getCommand("givecoins").setExecutor(new GiveCoins());
    }

}
