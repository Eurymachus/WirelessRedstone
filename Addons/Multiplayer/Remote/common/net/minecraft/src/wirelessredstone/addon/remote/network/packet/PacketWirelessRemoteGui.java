package net.minecraft.src.wirelessredstone.addon.remote.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessRemoteGui extends PacketWirelessRemote {
	public PacketWirelessRemoteGui() {
		super(PacketIds.GUI);
	}

	public PacketWirelessRemoteGui(int deviceID) {
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
