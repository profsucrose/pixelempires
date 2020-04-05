package me.sucrosedev.pixelempires.util;

import org.bukkit.entity.Player;

import java.io.File;

public class FileUtil {
    public static File getPlayerVisitedWorldFile(Player player) {
        String uuidString = player.getUniqueId().toString();
        File playerWorldFile = new File("/root/PixelEmpires/plugins/PixelEmpires/playersPastLobby/" + uuidString);
        return playerWorldFile;
    }
}
