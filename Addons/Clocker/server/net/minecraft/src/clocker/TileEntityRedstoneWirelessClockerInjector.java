package net.minecraft.src.clocker;

import net.minecraft.src.Packet;
import net.minecraft.src.clocker.network.PacketWirelessClockerTile;

public class TileEntityRedstoneWirelessClockerInjector {

	public static Packet getDescriptionPacket(TileEntityRedstoneWirelessClocker clocker) {
		PacketWirelessClockerTile pWCT = new PacketWirelessClockerTile(clocker);
		return pWCT.getPacket();
	}

}
