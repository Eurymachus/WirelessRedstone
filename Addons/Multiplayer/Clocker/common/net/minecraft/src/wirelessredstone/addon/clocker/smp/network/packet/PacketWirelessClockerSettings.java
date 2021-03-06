package net.minecraft.src.wirelessredstone.addon.clocker.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessClockerSettings extends PacketWirelessClocker {

	public PacketWirelessClockerSettings() {
		super(PacketIds.ADDON);
	}

	public PacketWirelessClockerSettings(Object clockFreq, int i, int j, int k) {
		this();
		this.setPosition(i, j, k);
		this.payload = new PacketPayload(0, 0, 1, 0);
		this.setClockFreq(clockFreq.toString());
	}

	@Override
	public String toString() {
		return "[" + this.getClockFreq() + "](" + xPosition + "," + yPosition
				+ "," + zPosition + ")";
	}

	public void setClockFreq(String clockFreq) {
		this.payload.setStringPayload(0, clockFreq);
	}

	public String getClockFreq() {
		return this.payload.getStringPayload(0);
	}
}
