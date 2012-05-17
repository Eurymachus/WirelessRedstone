package net.minecraft.src.wirelessredstone.addon.remote.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketWireless;

public class PacketWirelessRemote extends PacketWireless
{
	public PacketWirelessRemote(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-REMOTE";
	}
}
