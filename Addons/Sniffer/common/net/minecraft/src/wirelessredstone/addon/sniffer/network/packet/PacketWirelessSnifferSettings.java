package net.minecraft.src.wirelessredstone.addon.sniffer.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessSnifferSettings extends PacketWirelessSniffer {

	public PacketWirelessSnifferSettings()
	{
		super(PacketIds.WIFI_SNIFFER);
	}

	public PacketWirelessSnifferSettings(String freq)
	{
		this();
		this.payload = new PacketPayload(0,0,1,0);
		this.setFreq(freq);
	}

	public PacketWirelessSnifferSettings(String freq, boolean state)
	{
		this();
		this.payload = new PacketPayload(0,0,1,1);
		this.setFreq(freq);
		this.setState(state);
	}
	
	@Override
	public String toString() {
		return "Freq["+this.getFreq()+"]("+this.xPosition+","+this.yPosition+","+this.zPosition+")";
	}
	
	public void setFreq(String freq)
	{
		this.payload.setStringPayload(0, freq);
	}
	
	@Override
	public String getFreq()
	{
		return this.payload.getStringPayload(0);
	}
}
