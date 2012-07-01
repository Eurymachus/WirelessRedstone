package net.minecraft.src.wirelessredstone.addon.clocker.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketWirelessClocker extends PacketUpdate {
	PacketWirelessClocker(int packetId) {
		super(packetId);
		this.channel = "WIFI-CLOCKER";
	}
}
