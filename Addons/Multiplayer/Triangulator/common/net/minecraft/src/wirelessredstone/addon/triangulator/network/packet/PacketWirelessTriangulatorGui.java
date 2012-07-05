package net.minecraft.src.wirelessredstone.addon.triangulator.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessTriangulatorGui extends PacketWirelessTriangulator {

	public PacketWirelessTriangulatorGui() {
		super(PacketIds.GUI);
	}

	public PacketWirelessTriangulatorGui(int deviceID) {
		this();
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.setDeviceID(deviceID);
	}

	public void setDeviceID(int deviceID) {
		this.payload.setIntPayload(0, deviceID);
	}

	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	@Override
	public String toString() {
		return "(" + this.xPosition + "," + this.yPosition + ","
				+ this.zPosition + ")RemoteID[" + this.getDeviceID() + "]";
	}
}
