package net.minecraft.src.wirelessredstone.addon.powerc.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;

public class PacketPowerConfigGui extends PacketPowerConfig
{
	public PacketPowerConfigGui()
	{
		super(PacketIds.GUI);
	}
	
	public PacketPowerConfigGui(int x, int y, int z) {
		this();
		this.setPosition(x, y, z);
	}
}
