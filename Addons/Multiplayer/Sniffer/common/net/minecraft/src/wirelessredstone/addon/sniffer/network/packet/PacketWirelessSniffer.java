package net.minecraft.src.wirelessredstone.addon.sniffer.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketWireless;

public class PacketWirelessSniffer extends PacketWireless {

	public PacketWirelessSniffer(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-SNIFFER";
	}
}
