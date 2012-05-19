package net.minecraft.src.wirelessredstone.addon.remote.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessRemoteSettings extends PacketWirelessRemote
{
	public PacketWirelessRemoteSettings()
	{
		super(PacketIds.WIFI_REMOTE);
	}
	
	public PacketWirelessRemoteSettings(String freq) {
		this();
		this.payload = new PacketPayload(0,0,1,0);
		this.setFreq(freq);
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
