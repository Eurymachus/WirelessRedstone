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
	
	public PacketWirelessTriangulatorSettings(String freq, boolean state) {
		this();
		this.payload = new PacketPayload(0,0,1,1);
		this.setFreq(freq);
		this.setState(state);
	}
}
