package net.minecraft.src.clocker.network;

import net.minecraft.src.wifi.network.PacketUpdate;

public class PacketWirelessClocker extends PacketUpdate
{
	PacketWirelessClocker(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-CLOCKER";
	}
}
