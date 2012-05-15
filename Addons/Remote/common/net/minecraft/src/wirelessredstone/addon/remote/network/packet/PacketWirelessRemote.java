package net.minecraft.src.wirelessredstone.addon.remote.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;

public class PacketWirelessRemote extends PacketUpdate
{
	public PacketWirelessRemote(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-REMOTE";
	}
}
