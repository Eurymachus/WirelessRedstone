package net.minecraft.src.wirelessredstone.smp.network.packet;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;

/**
 * Used to send Redstone Ether packet data
 * 
 * @author Eurymachus
 * 
 */
public class PacketRedstoneDevice extends PacketWireless {
	public PacketRedstoneDevice() {
		super(PacketIds.DEVICE);
	}

	public PacketRedstoneDevice(String command) {
		super(PacketIds.DEVICE, new PacketPayload(1, 0, 2, 1));
		setCommand(command);
	}

	public PacketRedstoneDevice(WirelessDeviceData data, World world) {
		super(PacketIds.DEVICE, new PacketPayload(1, 0, 2, 1));
		this.setID(data.getID());
		this.setFreq(data.getFreq());
		this.setState(data.getState());
	}
	
	public void setID(int id) {
		
	}

	@Override
	public String toString() {
		return this.getCommand() + "(" + xPosition + "," + yPosition + ","
				+ zPosition + ")[" + this.getFreq() + "]:" + this.getState();
	}

	@Override
	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	@Override
	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write(
				"setCommand(" + command + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	@Override
	public String getFreq() {
		return this.payload.getStringPayload(1);
	}

	@Override
	public void setFreq(Object freq) {
		this.payload.setStringPayload(1, freq.toString());
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write(
				"setFreq(" + freq.toString() + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	@Override
	public void setState(boolean state) {
		this.payload.setBoolPayload(0, state);
		LoggerRedstoneWireless.getInstance("PacketRedstoneEther").write(
				"setState(" + state + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG);
	}

	@Override
	public boolean getState() {
		return this.payload.getBoolPayload(0);
	}
}
