package net.minecraft.src.wirelessredstone.addon.clocker.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessClockerGui extends PacketWirelessClocker {

	public PacketWirelessClockerGui() {
		super(PacketIds.GUI);
	}

	public PacketWirelessClockerGui(int clockFreq, int i, int j, int k) {
		this();
		this.setPosition(i, j, k);
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.setClockFreq(clockFreq);
	}

	@Override
	public String toString() {
		return "[" + this.getClockFreq() + "](" + xPosition + "," + yPosition
				+ "," + zPosition + ")";
	}

	public void setClockFreq(int clockFreq) {
		this.payload.setIntPayload(0, clockFreq);
	}

	public int getClockFreq() {
		return this.payload.getIntPayload(0);
	}
}
