package net.minecraft.src.wirelessredstone.addon.clocker.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketWirelessClocker extends PacketUpdate {
	PacketWirelessClocker(int packetId) {
		super(packetId);
		this.channel = "WR-CLOCKER";
	}
}
