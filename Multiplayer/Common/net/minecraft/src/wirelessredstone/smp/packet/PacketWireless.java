package net.minecraft.src.wirelessredstone.smp.packet;

import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class PacketWireless extends PacketUpdate
{
	public PacketWireless(int packetId)
	{
		super(packetId);
		this.channel = "WIFI";
	}

	@Override
	public String toString() {
		return this.getCommand()+"("+xPosition+","+yPosition+","+zPosition+")["+this.getFreq()+"]";
	}
	
	public String getCommand()
	{
		return this.payload.getStringPayload(0);
	}
	
	public void setCommand(String command)
	{
		this.payload.setStringPayload(0, command);
	}
	
	public String getFreq()
	{
		return this.payload.getStringPayload(1);
	}

	public void setFreq(Object freq) {
		this.payload.setStringPayload(1, freq.toString());
	}
}
