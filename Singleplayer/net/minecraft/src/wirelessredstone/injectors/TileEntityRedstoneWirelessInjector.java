package net.minecraft.src.wirelessredstone.injectors;

import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class TileEntityRedstoneWirelessInjector {
	public static boolean doUpdateEntity(World world) {
		return (world != null && !world.isRemote);
	}

	public static Packet getDescriptionPacket(
			TileEntityRedstoneWireless tileentity) {
		return null;
	}
}
