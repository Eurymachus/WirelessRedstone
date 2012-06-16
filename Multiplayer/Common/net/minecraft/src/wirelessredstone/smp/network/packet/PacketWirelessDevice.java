package net.minecraft.src.wirelessredstone.smp.network.packet;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;

/**
 * Used to send Redstone Device packet data
 * 
 * @author Eurymachus
 * 
 */
public class PacketWirelessDevice extends PacketWireless {
	public PacketWirelessDevice(int packetId) {
		super(packetId);
		
	}

	public PacketWirelessDevice(int packetId, String name) {
		super(packetId, new PacketPayload(1, 0, 2, 1));
		this.setName(name);
	}

	public PacketWirelessDevice(int packetId, WirelessDeviceData data) {
		this(packetId, data.getName());
		this.setID(data.getID());
		this.setFreq(data.getFreq());
		this.setState(data.getState());
	}
	
	public void setID(int id) {
		this.payload.setIntPayload(0, id);
	}
	
	public void setName(String name) {
		this.payload.setStringPayload(0, name);
	}
	
	public int getID() {
		return this.payload.getIntPayload(0);
	}
	
	public String getName() {
		return this.payload.getStringPayload(0);
	}
}
