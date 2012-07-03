package net.minecraft.src.wirelessredstone.addon.remote.network.packet;

import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketPayload;


public class PacketWirelessRemoteGui extends PacketWirelessRemote {
	public PacketWirelessRemoteGui() {
		super(PacketIds.GUI);
	}

	public PacketWirelessRemoteGui(int itemDamage, int x, int y, int z) {
		this();
		this.setPosition(x, y, z);
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.setItemDamage(itemDamage);
	}

	public void setItemDamage(int itemDamage) {
		this.payload.setIntPayload(0, itemDamage);
	}

	public int getItemDamage() {
		return this.payload.getIntPayload(0);
	}

	@Override
	public String toString() {
		return "(" + this.xPosition + "," + this.yPosition + ","
				+ this.zPosition + ")RemoteID[" + this.getItemDamage() + "]";
	}
}
