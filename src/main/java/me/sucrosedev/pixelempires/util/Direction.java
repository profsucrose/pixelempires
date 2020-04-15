package me.sucrosedev.pixelempires.util;

import org.bukkit.Location;
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

    public static boolean isWithin(int x, int y, int range) {
        return Math.abs(x - y) <= range;
    }

    public static boolean locationIsWithinXYZ(Location location, int x, int y, int z, int range) {
        return isWithin(location.getBlockX(), x, range)
                && isWithin(location.getBlockY(), y, range)
                && isWithin(location.getBlockZ(), z, range);
    }

    public static boolean locationIsWithinXYZ(Location location, int[] xyz, int range) {
        return locationIsWithinXYZ(location, xyz[0], xyz[1], xyz[2], range);
    }

    public static boolean checkIfLocationXYZEqual(Location location, int x, int y, int z) {
        return location.getBlockX() == x && location.getBlockY() == y && location.getBlockZ() == z;
    }

    public static boolean checkIfLocationXYZEqual(Location location, int[] locArr) {
        return checkIfLocationXYZEqual(location, locArr[0], locArr[1], locArr[2]);
    }
}
