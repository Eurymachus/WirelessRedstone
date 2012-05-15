package net.minecraft.src.wirelessredstone.addon.clocker.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;

public class PacketWirelessClocker extends PacketUpdate
{
	PacketWirelessClocker(int packetId)
	{
		super(packetId);
		this.channel = "WIFI-CLOCKER";
	}
}
