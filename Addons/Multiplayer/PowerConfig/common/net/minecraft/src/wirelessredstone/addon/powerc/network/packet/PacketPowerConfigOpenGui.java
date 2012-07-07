package net.minecraft.src.wirelessredstone.addon.powerc.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;

public class PacketPowerConfigOpenGui extends PacketPowerConfig {
	public PacketPowerConfigOpenGui() {
		super(PacketIds.GUI);
	}

	public PacketPowerConfigOpenGui(int x, int y, int z) {
		this();
		this.setPosition(x, y, z);
	}
}
