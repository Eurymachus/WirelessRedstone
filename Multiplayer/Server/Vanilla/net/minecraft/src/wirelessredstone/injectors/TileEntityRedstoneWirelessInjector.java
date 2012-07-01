package net.minecraft.src.wirelessredstone.injectors;

import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketWirelessTile;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class TileEntityRedstoneWirelessInjector {
	public static boolean doUpdateEntity(World world) {
		return (world != null);
	}

	public static Packet getDescriptionPacket(
			TileEntityRedstoneWireless tileentity) {
		return new PacketWirelessTile("fetchTile", tileentity).getPacket();
	}
}
