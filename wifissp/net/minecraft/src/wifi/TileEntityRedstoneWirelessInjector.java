package net.minecraft.src.wifi;

import net.minecraft.src.World;

public class TileEntityRedstoneWirelessInjector {
	public static boolean doUpdateEntity(World world) {
		return (world != null && !world.isRemote);
	}
}
