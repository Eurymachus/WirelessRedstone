package net.minecraft.src.wirelessredstone.addon.sniffer.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessSnifferOpenGui extends PacketWirelessSniffer {

	public PacketWirelessSnifferOpenGui() {
		super(PacketIds.GUI);
	}

	public PacketWirelessSnifferOpenGui(int deviceID) {
		this();
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.setDeviceID(deviceID);
	}

	public void setDeviceID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	@Override
	public String toString() {
		return "Device[" + this.getDeviceID() + "].setState(" + this.getState()
				+ ")";
	}
}
