package net.minecraft.src.wirelessredstone.addon.triangulator.network;

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
	
	@Override
	public void setFreq(Object freq)
	{
		this.payload.setStringPayload(0, freq.toString());
	}
	
	@Override
	public String getFreq()
	{
		return this.payload.getStringPayload(0);
	}
}
