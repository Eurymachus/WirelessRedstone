package net.minecraft.src.wirelessredstone.addon.remote.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessRemoteSettings extends PacketWirelessRemote
{
	public PacketWirelessRemoteSettings()
	{
		super(PacketIds.WIFI_REMOTE);
	}
	
	public PacketWirelessRemoteSettings(String command) {
		this();
		this.payload = new PacketPayload(0,0,2,0);
		this.setCommand(command);
	}
	
	@Override
	public String toString() {
		return "Freq["+this.getFreq()+"]("+this.xPosition+","+this.yPosition+","+this.zPosition+")";
	}
}
