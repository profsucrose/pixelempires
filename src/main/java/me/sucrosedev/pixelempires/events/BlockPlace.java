package me.sucrosedev.pixelempires.events;

import me.sucrosedev.pixelempires.PixelEmpires;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlace implements Listener {
    @EventHandler
    public static void onBlockPlace(BlockPlaceEvent e) {
        Material blockType = e.getBlock().getType();
        Player player = e.getPlayer();

        if (blockType.toString().contains("SIGN")) {
            if (!(e.getBlockAgainst().getState() instanceof Chest)) return;

            Chest chest = (Chest) e.getBlockAgainst().getState();
            chest.setCustomName(e.getPlayer().getDisplayName() + "'s Shop");
            chest.setMetadata("ownerId", new FixedMetadataValue(PixelEmpires.plugin, player.getUniqueId().toString()));
            chest.setMetadata("isChestShop", new FixedMetadataValue(PixelEmpires.plugin, true));
            chest.update(true);
        }
    }
}
