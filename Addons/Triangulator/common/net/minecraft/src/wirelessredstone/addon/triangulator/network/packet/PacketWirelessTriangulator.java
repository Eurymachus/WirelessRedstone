package net.minecraft.src.wirelessredstone.addon.triangulator.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketWireless;

public class PacketWirelessTriangulator extends PacketWireless
{
	public PacketWirelessTriangulator(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-TRI";
	}
	
	@Override
	public String toString() {
		return "Freq["+this.getFreq()+"]("+this.xPosition+","+this.yPosition+","+this.zPosition+")";
	}
}
