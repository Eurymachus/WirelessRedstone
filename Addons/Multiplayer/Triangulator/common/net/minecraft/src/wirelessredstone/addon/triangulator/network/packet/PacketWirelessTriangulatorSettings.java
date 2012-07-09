package net.minecraft.src.wirelessredstone.addon.triangulator.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessTriangulatorSettings extends
		PacketWirelessTriangulator {
	public PacketWirelessTriangulatorSettings() {
		super(PacketIds.ADDON);
	}

	public PacketWirelessTriangulatorSettings(String command) {
		this();
		this.payload = new PacketPayload(1, 0, 2, 0);
		this.setCommand(command);
	}

	public int[] getCoords() {
		int[] coords = new int[3];
		coords[0] = this.xPosition;
		coords[1] = this.yPosition;
		coords[2] = this.zPosition;
		return coords;
	}

	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	public void setDeviceID(int id) {
		this.payload.setIntPayload(0, id);
	}
}