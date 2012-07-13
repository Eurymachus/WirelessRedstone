package net.minecraft.src.wirelessredstone.addon.sniffer.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessSnifferSettings extends PacketWirelessSniffer {

	public PacketWirelessSnifferSettings() {
		super(PacketIds.ADDON);
	}

	public PacketWirelessSnifferSettings(String command) {
		this();
		this.payload = new PacketPayload(2, 0, 1, 0);
		this.setCommand(command);
	}

	@Override
	public String toString() {
		return "Freq[" + this.getFreq() + "](" + this.xPosition + ","
				+ this.yPosition + "," + this.zPosition + ")";
	}

	public void setDeviceID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public void setPageNumber(int pageNumber) {
		this.payload.setIntPayload(1, pageNumber);
	}

	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	public int getPageNumber() {
		return this.payload.getIntPayload(1);
	}
}
