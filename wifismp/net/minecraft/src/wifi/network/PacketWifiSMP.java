package net.minecraft.src.wifi.network;

public class PacketWifiSMP extends PacketUpdate
{
	public PacketWifiSMP(int packetId)
	{
		super(packetId);
		this.channel = "WIFI";
	}
}
