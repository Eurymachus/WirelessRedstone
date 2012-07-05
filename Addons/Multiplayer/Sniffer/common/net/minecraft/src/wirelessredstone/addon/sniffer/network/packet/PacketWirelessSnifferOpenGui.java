package net.minecraft.src.wirelessredstone.addon.sniffer.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;

public class PacketWirelessSnifferOpenGui extends PacketWirelessSniffer {

	public PacketWirelessSnifferOpenGui()
	{
		super(PacketIds.GUI);
	}

	public PacketWirelessSnifferOpenGui(boolean open)
	{
		this();
		this.payload = new PacketPayload(0,0,0,1);
		this.setState(open);
	}
}
