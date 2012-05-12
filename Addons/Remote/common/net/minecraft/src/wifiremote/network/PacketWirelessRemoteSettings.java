package net.minecraft.src.wifiremote.network;

import net.minecraft.src.wifi.network.PacketIds;
import net.minecraft.src.wifi.network.PacketPayload;
import net.minecraft.src.wifi.network.PacketUpdate;

public class PacketWirelessRemoteSettings extends PacketWirelessRemote
{
	public PacketWirelessRemoteSettings()
	{
		super(PacketIds.WIFI_REMOTE);
	}
	
	public PacketWirelessRemoteSettings(String freq) {
		this();
		this.payload = new PacketPayload(0,0,1,1);
		this.setFreq(freq);
	}
	
	public String toString() {
		return "Freq["+this.getFreq()+"]("+this.xPosition+","+this.yPosition+","+this.zPosition+")State["+this.getState()+"]";
	}
	
	public void setState(boolean state)
	{
		this.payload.setBoolPayload(0, state);
	}
	
	public void setFreq(String freq)
	{
		this.payload.setStringPayload(0, freq);
	}
	
	public boolean getState()
	{
		return this.payload.getBoolPayload(0);
	}
	
	public String getFreq()
	{
		return this.payload.getStringPayload(0);
	}
}
