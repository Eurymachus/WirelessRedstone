package net.minecraft.src.wirelessredstone.addon.triangulator.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketWireless;

public class PacketWirelessTriangulator extends PacketWireless {
	public PacketWirelessTriangulator(int packetId) {
		super(packetId);
		this.channel = "WR-TRIANG";
	}

	@Override
	public String toString() {
		return "Freq[" + this.getFreq() + "](" + this.xPosition + ","
				+ this.yPosition + "," + this.zPosition + ")";
	}
}
