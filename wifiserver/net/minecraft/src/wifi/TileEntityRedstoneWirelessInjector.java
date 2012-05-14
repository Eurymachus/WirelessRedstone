package net.minecraft.src.wifi;

import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.wifi.network.PacketWirelessTile;

public class TileEntityRedstoneWirelessInjector {
	public static boolean doUpdateEntity(World world) {
		return ( world != null );
	}
	public static Packet getDescriptionPacket(TileEntityRedstoneWireless tileentity) {
		return new PacketWirelessTile("fetchTile", tileentity).getPacket();
	}
}
