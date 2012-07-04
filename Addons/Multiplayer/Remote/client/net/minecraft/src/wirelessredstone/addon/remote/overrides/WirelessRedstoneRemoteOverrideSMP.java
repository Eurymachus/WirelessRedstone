package net.minecraft.src.wirelessredstone.addon.remote.overrides;

import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.wirelessredstone.addon.remote.network.PacketHandlerWirelessRemote.PacketHandlerOutput;

public class WirelessRedstoneRemoteOverrideSMP implements WirelessRedstoneRemoteOverride {

	@Override
	public boolean beforeTransmitRemote(String command, World world,
			WirelessRemoteDevice remote) {
		if (world.isRemote) {
			PacketHandlerOutput.sendWirelessRemotePacket(command, remote);
			return true;
		}
		else
			return false;
	}
}
