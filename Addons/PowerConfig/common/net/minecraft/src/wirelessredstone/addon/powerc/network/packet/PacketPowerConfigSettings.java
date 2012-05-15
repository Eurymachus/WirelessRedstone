package net.minecraft.src.wirelessredstone.addon.powerc.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketPowerConfigSettings extends PacketPowerConfig {
	public PacketPowerConfigSettings() {
		super(PacketIds.WIFI_POWERC);
	}
	
	public PacketPowerConfigSettings(String command) {
		this();
		PacketPayload p = new PacketPayload(1,1,1,1);
		p.setStringPayload(0, command);
		this.payload = p;
	}
	
	@Override
	public String toString() {
		return this.payload.getStringPayload(0)+"("+xPosition+","+yPosition+","+zPosition+")["+this.payload.getIntPayload(0)+"]";
	}
	
	public int getValue()
	{
		return this.payload.getIntPayload(0);
	}
	
	public String getCommand()
	{
		return this.payload.getStringPayload(0);
	}

	public void setValue(int value) {
		this.payload.setIntPayload(0, value);
	}	
}
