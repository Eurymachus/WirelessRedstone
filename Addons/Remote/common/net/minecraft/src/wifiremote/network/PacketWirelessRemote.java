package net.minecraft.src.wifiremote.network;

import net.minecraft.src.wifi.network.PacketUpdate;

public class PacketWirelessRemote extends PacketUpdate
{
	public PacketWirelessRemote(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-REMOTE";
	}
}