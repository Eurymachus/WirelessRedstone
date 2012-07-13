package net.minecraft.src.wirelessredstone.addon.powerc.smp.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketPowerConfigSettings extends PacketPowerConfig {
	public PacketPowerConfigSettings() {
		super(PacketIds.ADDON);
	}

	public PacketPowerConfigSettings(String command) {
		this();
		this.payload = new PacketPayload(1, 0, 1, 0);
		this.setCommand(command);
	}

	@Override
	public String toString() {
		return this.payload.getStringPayload(0) + "(" + xPosition + ","
				+ yPosition + "," + zPosition + ")["
				+ this.payload.getIntPayload(0) + "]";
	}

	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	public int getValue() {
		return this.payload.getIntPayload(0);
	}

	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
	}

	public void setValue(int value) {
		this.payload.setIntPayload(0, value);
	}
}
