package net.minecraft.src.wirelessredstone.addon.triangulator.network;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessTriangulatorSettings extends PacketWirelessTriangulator
{
	public PacketWirelessTriangulatorSettings()
	{
		super(PacketIds.WIFI_TRIANGULATOR);
	}
	
	public PacketWirelessTriangulatorSettings(String freq) {
		this();
		this.payload = new PacketPayload(0,0,1,0);
		this.setFreq(freq);
	}
	
	public int[] getCoords()
	{
		int[] coords = new int[3];
		coords[0] = this.xPosition;
		coords[1] = this.yPosition;
		coords[2] = this.zPosition;
		return coords;
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