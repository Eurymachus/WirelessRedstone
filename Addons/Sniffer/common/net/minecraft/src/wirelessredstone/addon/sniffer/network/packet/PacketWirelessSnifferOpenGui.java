package net.minecraft.src.wirelessredstone.addon.sniffer.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessSnifferOpenGui extends PacketWirelessSniffer {

	public PacketWirelessSnifferOpenGui()
	{
		super(PacketIds.WIFI_SNIFFERGUI);
	}

	public PacketWirelessSnifferOpenGui(boolean open)
	{
		this();
		this.payload = new PacketPayload(0,0,0,1);
		this.setState(open);
	}
}
