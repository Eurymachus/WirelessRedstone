package net.minecraft.src.clocker.network;

import net.minecraft.src.wifi.network.PacketIds;
import net.minecraft.src.wifi.network.PacketPayload;

public class PacketWirelessClockerGui extends PacketWirelessClocker {

	public PacketWirelessClockerGui() {
		super(PacketIds.WIFI_CLOCKERGUI);
	}

	public PacketWirelessClockerGui(int clockFreq, int i, int j, int k) {
		this();
		this.setPosition(i, j, k);
		this.payload = new PacketPayload(1,0,0,0);
		this.setClockFreq(clockFreq);
	}

	@Override
	public String toString() {
		return "["+this.getClockFreq()+"]("+xPosition+","+yPosition+","+zPosition+")";
	}
	
	public void setClockFreq(int clockFreq) {
		this.payload.setIntPayload(0, clockFreq);
	}

	public int getClockFreq() {
		return this.payload.getIntPayload(0);
	}
}
