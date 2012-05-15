package net.minecraft.src.wirelessredstone.addon.powerc.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;

public class PacketPowerConfig extends PacketUpdate
{
	public PacketPowerConfig(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-POWERC";
	}
}
