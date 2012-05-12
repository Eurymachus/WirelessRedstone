package net.minecraft.src.powerc.network;

import net.minecraft.src.wifi.network.PacketIds;

public class PacketPowerConfigGui extends PacketPowerConfig
{
	public PacketPowerConfigGui()
	{
		super(PacketIds.WIFI_POWERCGUI);
	}
	
	public PacketPowerConfigGui(int x, int y, int z) {
		this();
		this.setPosition(x, y, z);
	}
}
