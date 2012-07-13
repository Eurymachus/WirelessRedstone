package net.minecraft.src.wirelessredstone.addon.sniffer.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessSnifferOpenGui extends PacketWirelessSniffer {

	public PacketWirelessSnifferOpenGui() {
		super(PacketIds.GUI);
	}

	public PacketWirelessSnifferOpenGui(int deviceID) {
		this();
		this.payload = new PacketPayload(2, 0, 0, 0);
		this.setDeviceID(deviceID);
	}

	public void setDeviceID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	public void setPageNumber(int pageNumber) {
		this.payload.setIntPayload(1, pageNumber);
	}

	public int getPageNumber() {
		return this.payload.getIntPayload(1);
	}

	@Override
	public String toString() {
		return "Open Gui for Device[" + this.getDeviceID() + "].setPageNumber("
				+ this.getPageNumber() + ")";
	}
}
