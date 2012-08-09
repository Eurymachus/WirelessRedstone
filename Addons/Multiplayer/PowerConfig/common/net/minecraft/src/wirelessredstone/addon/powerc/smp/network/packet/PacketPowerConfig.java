package net.minecraft.src.wirelessredstone.addon.powerc.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketPowerConfig extends PacketUpdate {
	public PacketPowerConfig(int packetId) {
		super(packetId);
		this.channel = "WR-POWERC";
	}
}
