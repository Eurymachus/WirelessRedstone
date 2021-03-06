package net.minecraft.src.wirelessredstone.addon.clocker.tileentity;

import net.minecraft.src.Packet;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.packet.PacketWirelessClockerTile;

public class TileEntityRedstoneWirelessClockerInjector {

	public static Packet getDescriptionPacket(
			TileEntityRedstoneWirelessClocker clocker) {
		PacketWirelessClockerTile pWCT = new PacketWirelessClockerTile(clocker);
		return pWCT.getPacket();
	}

}
