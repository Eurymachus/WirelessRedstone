package net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessRemotePlayer extends PacketWirelessRemote {
	public PacketWirelessRemotePlayer() {
		super(PacketIds.DEVICE);
	}

	public PacketWirelessRemotePlayer(String command) {
		this();
		this.payload = new PacketPayload(2, 0, 2, 1);
		this.setCommand(command);
	}

	public void setRemoteID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public void setEntityID(int id) {
		this.payload.setIntPayload(1, id);
	}

	public int getRemoteID() {
		return this.payload.getIntPayload(0);
	}

	public int getEntityID() {
		return this.payload.getIntPayload(1);
	}

	@Override
	public String toString() {
		return "Freq[" + this.getFreq() + "](" + this.xPosition + ","
				+ this.yPosition + "," + this.zPosition + ")";
	}
}
