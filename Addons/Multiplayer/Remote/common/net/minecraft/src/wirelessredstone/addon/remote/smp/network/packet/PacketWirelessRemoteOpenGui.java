package net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessRemoteOpenGui extends PacketWirelessRemote {
	public PacketWirelessRemoteOpenGui() {
		super(PacketIds.GUI);
	}

	public PacketWirelessRemoteOpenGui(int deviceID) {
		this();
		this.payload = new PacketPayload(1, 0, 2, 0);
		this.setRemoteID(deviceID);
	}

	@Override
	public void setFreq(Object freq) {
		this.payload.setStringPayload(0, freq.toString());
	}

	@Override
	public String getFreq() {
		return this.payload.getStringPayload(0);
	}

	public void setRemoteID(int deviceID) {
		this.payload.setIntPayload(0, deviceID);
	}

	public int getRemoteID() {
		return this.payload.getIntPayload(0);
	}

	public void setRemoteName(Object name) {
		this.payload.setStringPayload(1, name.toString());
	}

	public String getRemoteName() {
		return this.payload.getStringPayload(1);
	}

	@Override
	public String toString() {
		return "(" + this.xPosition + "," + this.yPosition + ","
				+ this.zPosition + ")RemoteID[" + this.getRemoteID() + "]";
	}
}
