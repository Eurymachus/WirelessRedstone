package net.minecraft.src.wirelessredstone.smp.packet;

public class PacketWifiSMP extends PacketUpdate
{
	public PacketWifiSMP(int packetId)
	{
		super(packetId);
		this.channel = "WIFI";
	}
}
