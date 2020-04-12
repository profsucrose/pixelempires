package me.sucrosedev.pixelempires.util;

import org.bukkit.block.BlockFace;

public class Direction {
    public static int[] getXZFromCardinal(BlockFace direction) {
        int x = 0;
        int z = 0;

        if (direction.equals(BlockFace.NORTH))
            z = -1;

        if (direction.equals(BlockFace.SOUTH))
            z = 1;

        if (direction.equals(BlockFace.EAST))
            x = 1;

        if (direction.equals(BlockFace.WEST))
            x = -1;

        return new int[] {x, z};
    }
}
