package net.minecraft.src.wirelessredstone.addon.triangulator.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessTriangulatorSettings extends PacketWirelessTriangulator
{
	public PacketWirelessTriangulatorSettings()
	{
		super(PacketIds.WIFI_TRIANGULATOR);
	}
	
	public PacketWirelessTriangulatorSettings(String command) {
		this();
		this.payload = new PacketPayload(0,0,2,0);
	}
	
	public int[] getCoords()
	{
		int[] coords = new int[3];
		coords[0] = this.xPosition;
		coords[1] = this.yPosition;
		coords[2] = this.zPosition;
		return coords;
	}
}